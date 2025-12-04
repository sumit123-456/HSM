package com.example.hms_backend.authentication.service;


import com.example.hms_backend.authentication.entity.OtpToken;
import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.authentication.repo.OtpTokenRepo;
import com.example.hms_backend.authentication.repo.UserRepo;
import com.example.hms_backend.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService{
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OtpTokenRepo otpTokenRepo;

    @Autowired
    private EmailService  emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

// for initiating password reset functionality

    @Transactional
    @Override
    public void initiateReset(String email)
    {
        System.out.println("initiating reset");
        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Email not registered", "EMAIL_NOT_FOUND", HttpStatus.NOT_FOUND));

        // Clean old tokens
        otpTokenRepo.findTopByEmailOrderByExpiryDesc(email)
                .ifPresent(otpTokenRepo::delete);


        // Generating random OTP
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(10);
        System.out.println("Reset otp: " + otp);



        OtpToken token = new OtpToken();
        token.setEmail(email);
        token.setOtp(otp);
        token.setExpiry(expiry);
        otpTokenRepo.save(token);

        emailService.sendOtpEmail(email, otp);

    }

    @Transactional
    @Override
    public void validateOtp(String email, String otp)
    {
        // We are accessing otp from table which are having used field as false
        OtpToken token = otpTokenRepo.findByEmailAndOtpAndUsedFalse(email, otp)
                .orElseThrow(() -> new BusinessException("Invalid OTP", "INVALID_OTP", HttpStatus.BAD_REQUEST));


        if (token.getExpiry().isBefore(LocalDateTime.now())) {
            throw new BusinessException("OTP expired", "OTP_EXPIRED", HttpStatus.BAD_REQUEST);
        }

        // setting used field as true
        token.setUsed(true);
        otpTokenRepo.save(token); //optimistic locking ensures safe update


    }


    @Transactional
    @Override
    public void resetPassword(String email, String otp, String newPassword) {
        OtpToken token = otpTokenRepo.findTopByEmailOrderByExpiryDesc(email)
                .orElseThrow(() -> new BusinessException("OTP not found", "OTP_NOT_FOUND", HttpStatus.NOT_FOUND));

        if (!token.getOtp().equals(otp)) {
            throw new BusinessException("Invalid OTP", "INVALID_OTP", HttpStatus.UNAUTHORIZED);
        }

        if (!token.isUsed()) {
            throw new BusinessException("OTP not validated", "OTP_NOT_VALIDATED", HttpStatus.UNAUTHORIZED);
        }


        if (token.getExpiry().isBefore(LocalDateTime.now())) {
            throw new BusinessException("OTP has expired", "OTP_EXPIRED", HttpStatus.UNAUTHORIZED);
        }

        // Encrypt and update password
        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User not found", "USER_NOT_FOUND", HttpStatus.NOT_FOUND));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        // Optionally delete OTP token
        // ✅ Safe deletion with optimistic locking
        // ✅ Delete all OTP tokens safely
        List<OtpToken> tokens = otpTokenRepo.findAllByEmail(email);
        tokens.forEach(otpTokenRepo::delete);


    }


}

