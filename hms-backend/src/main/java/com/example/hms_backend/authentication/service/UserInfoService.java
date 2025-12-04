package com.example.hms_backend.authentication.service;

import com.example.hms_backend.authentication.entity.UserInfo;

import java.util.Optional;

public interface UserInfoService {
    Optional<UserInfo> findByUserId(Long userId);
}
