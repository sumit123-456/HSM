package com.example.hms_backend.registration.service;

import com.example.hms_backend.registration.dto.RegistrationDto;
import jakarta.validation.Valid;

import java.io.IOException;

public interface RegistrationService {
    void registerUser(@Valid RegistrationDto dto) throws IOException;

}