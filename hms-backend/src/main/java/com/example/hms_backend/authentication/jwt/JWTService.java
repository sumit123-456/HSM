package com.example.hms_backend.authentication.jwt;

import com.example.hms_backend.authentication.entity.Permission;
import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.authentication.entity.UserInfo;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface JWTService {
    public String generateToken(UserEntity user, UserInfo userInfo, List<Permission> permissions);
    public String extractUsername(String token);
    public boolean validateToken(String token, UserDetails user);
    public Claims extractAllclaims(String token);
}
