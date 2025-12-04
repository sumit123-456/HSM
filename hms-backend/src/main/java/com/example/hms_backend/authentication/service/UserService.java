package com.example.hms_backend.authentication.service;

import com.example.hms_backend.authentication.entity.UserEntity;

import java.util.Optional;

public interface UserService {

    UserEntity createUser(UserEntity userEntity);

    Optional<UserEntity> findUserByUsername(String username);



}