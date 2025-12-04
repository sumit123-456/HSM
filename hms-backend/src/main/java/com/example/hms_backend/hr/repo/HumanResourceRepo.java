package com.example.hms_backend.hr.repo;

import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.hr.entity.HumanResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HumanResourceRepo extends JpaRepository<HumanResource,Long> {

    HumanResource findByUserEntity(UserEntity userEntity);
}
