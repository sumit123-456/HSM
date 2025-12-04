package com.example.hms_backend.authentication.service;

import com.example.hms_backend.authentication.entity.CustomUserDetails;
import com.example.hms_backend.authentication.entity.Permission;

import java.util.List;

public interface CurrentUserAndPermissionService {
    CustomUserDetails getCurrentUser();
    List<Permission> getPermissions();
}
