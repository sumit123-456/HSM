package com.example.hms_backend.authentication.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponce {
    private UserRequest user;
    private String token;
}
