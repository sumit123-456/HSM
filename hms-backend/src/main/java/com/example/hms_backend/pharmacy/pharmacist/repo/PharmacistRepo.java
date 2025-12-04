package com.example.hms_backend.pharmacy.pharmacist.repo;

import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.pharmacy.pharmacist.entity.Pharmacist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacistRepo extends JpaRepository<Pharmacist, Long> {
    Pharmacist findByUserEntity(UserEntity userEntity);
}
