package com.example.hms_backend.authentication.service;

import com.example.hms_backend.authentication.entity.UserInfo;
import com.example.hms_backend.authentication.repo.UserInfoRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepo userInfoRepo;

    @Override
    public Optional<UserInfo> findByUserId(Long userId) {
        return userInfoRepo.findByUserEntity_UserId(userId);
    }
}
