package com.example.hms_backend.authentication.repo;

import com.example.hms_backend.authentication.entity.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OtpTokenRepo extends JpaRepository< OtpToken, Long> {
    Optional<OtpToken> findByEmailAndOtpAndUsedFalse(String email, String otp);
    void deleteByEmail(String email); // Optional use for cleanup table
    List<OtpToken> findAllByEmail(String email);
    Optional<OtpToken> findTopByEmailOrderByExpiryDesc(String email);


}
