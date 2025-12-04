package com.example.hms_backend.authentication.service;

import com.example.hms_backend.authentication.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    Role getRoleById(Long id);
}
