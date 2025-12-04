package com.example.hms_backend.authentication.repo;

import com.example.hms_backend.authentication.entity.UserInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByUserEntity_UserId(Long userId);
}
