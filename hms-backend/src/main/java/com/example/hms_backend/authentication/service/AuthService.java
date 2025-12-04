package com.example.hms_backend.authentication.service;

import com.example.hms_backend.authentication.dto.LoginRequestDto;
import com.example.hms_backend.authentication.dto.LoginResponce;

public interface AuthService {
    public LoginResponce login(LoginRequestDto loginRequest) throws Exception;
}
