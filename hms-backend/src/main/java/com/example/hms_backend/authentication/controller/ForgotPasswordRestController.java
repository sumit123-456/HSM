package com.example.hms_backend.authentication.controller;

import com.example.hms_backend.authentication.dto.ForgotPasswordRequestDto;
import com.example.hms_backend.authentication.entity.OtpToken;
import com.example.hms_backend.authentication.service.ForgotPasswordService;
import com.example.hms_backend.exception.BusinessException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/forgot-password")
public class ForgotPasswordRestController {
    @Autowired
    private ForgotPasswordService forgotPasswordService;

    // Step 1: Initiate password reset (send OTP)
    @PostMapping("/request")
    public ResponseEntity<?> requestOtp(@Valid @RequestBody ForgotPasswordRequestDto dto) {

            System.out.println("inside intial otp sen block");
            forgotPasswordService.initiateReset(dto.getEmail());
            return ResponseEntity.ok().body("OTP sent to your email.");

    }

    // Step 2: Resend OTP
    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@RequestBody ForgotPasswordRequestDto dto) {
        try {
            forgotPasswordService.initiateReset(dto.getEmail());
            return ResponseEntity.ok().body("OTP resent to your email.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Step 3: Validate OTP
    @PostMapping("/validate")
    public ResponseEntity<?> validateOtp(@Valid @RequestBody ForgotPasswordRequestDto dto) {

            forgotPasswordService.validateOtp(dto.getEmail(), dto.getOtp());
            return ResponseEntity.ok().body("OTP validated. Please enter new password.");

    }

    // Step 4: Reset password
    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ForgotPasswordRequestDto dto) {
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new BusinessException("Passwords do not match", "PASSWORD_MISMATCH", HttpStatus.BAD_REQUEST);
        }

        forgotPasswordService.resetPassword(dto.getEmail(), dto.getOtp(), dto.getNewPassword());
        return ResponseEntity.ok(Map.of("message", "Password reset successful. You may now log in."));
    }

}








