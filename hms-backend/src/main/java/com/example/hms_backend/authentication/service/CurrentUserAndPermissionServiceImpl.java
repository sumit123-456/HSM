package com.example.hms_backend.authentication.service;

import com.example.hms_backend.authentication.entity.CustomUserDetails;
import com.example.hms_backend.authentication.entity.Permission;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrentUserAndPermissionServiceImpl implements CurrentUserAndPermissionService {



    @Override
    public CustomUserDetails getCurrentUser() {

        // 1. Get logged-in user details
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails;
        }
        return null;
    }

    @Override
    public List<Permission> getPermissions() {
        CustomUserDetails user = getCurrentUser();

        return (user != null) ? user.getPermissions() : List.of();


    }
}
