package com.example.hms_backend.accountant.repository;

import com.example.hms_backend.accountant.entity.Accountant;
import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.hr.entity.HumanResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountantRepo extends JpaRepository<Accountant,Long> {

    Accountant findByUserEntity(UserEntity userEntity);
}
