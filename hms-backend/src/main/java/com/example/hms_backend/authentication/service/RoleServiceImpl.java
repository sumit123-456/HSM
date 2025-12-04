package com.example.hms_backend.authentication.service;

import com.example.hms_backend.authentication.entity.Role;
import com.example.hms_backend.authentication.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepo roleRepo;

    @Override
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }

    @Override
    public Role getRoleById(Long id) {
        return  roleRepo.findById(id).orElseThrow(()-> new RuntimeException("Role Not Found with Id:- "+id));
    }
}