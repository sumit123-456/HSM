package com.example.hms_backend.insurance.repo;

import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.hr.entity.HumanResource;
import com.example.hms_backend.insurance.entity.Insurer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsurerRepo extends JpaRepository<Insurer, Long> {
    Insurer findByUserEntity(UserEntity userEntity);
}
