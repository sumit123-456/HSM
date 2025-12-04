package com.example.hms_backend.receptionist.repo;

import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.hr.entity.HumanResource;
import com.example.hms_backend.receptionist.entity.Receptionist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceptionistRepo extends JpaRepository<Receptionist,Long> {
    Receptionist findByUserEntity(UserEntity userEntity);
}
