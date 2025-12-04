package com.example.hms_backend.authentication.service;

import com.example.hms_backend.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;


    public void sendOtpEmail(String toEmail, String otp) {
       try
       {
           SimpleMailMessage message = new SimpleMailMessage();
           message.setTo(toEmail);
           message.setSubject("Password Reset OTP");
           message.setText("Your One-Time Password (OTP) for resetting your password for HarishChandra Medicity is: " + otp +
                   "\nThis OTP is valid for 10 minutes. Please do not share this code with anyone for security reasons");

           mailSender.send(message);
       }
       catch(MailException e)
       {
           // This includes internet issues, SMTP failures, etc.
           throw new BusinessException(
                   "Unable to send OTP email. Please check your internet connection or try again later.",
                   "EMAIL_SEND_FAILED",
                   HttpStatus.SERVICE_UNAVAILABLE
           );

       }
    }


}


