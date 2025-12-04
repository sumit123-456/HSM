package com.example.hms_backend.authentication.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;

}
