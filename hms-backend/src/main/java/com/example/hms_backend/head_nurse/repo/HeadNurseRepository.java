package com.example.hms_backend.head_nurse.repo;

import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.head_nurse.entity.HeadNurse;
import com.example.hms_backend.hr.entity.HumanResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeadNurseRepository extends JpaRepository<HeadNurse,Long> {

    HeadNurse findByUserEntity(UserEntity userEntity);
}
