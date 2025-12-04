package com.example.hms_backend.exception;

public class JwtTokenExpiredException extends RuntimeException {
    public JwtTokenExpiredException(String message)
    {

        super(message);
    }

}
