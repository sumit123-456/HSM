package com.example.hms_backend.authentication.service;

public interface EmailService {
    public void sendOtpEmail(String toEmail, String otp);
}

