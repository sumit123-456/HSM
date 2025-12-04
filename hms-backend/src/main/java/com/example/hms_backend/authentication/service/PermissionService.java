package com.example.hms_backend.authentication.service;

import com.example.hms_backend.authentication.entity.Permission;

import java.util.List;

public interface PermissionService {

    List<Permission> getUserPermissionsByUserId(Long UserId);
}