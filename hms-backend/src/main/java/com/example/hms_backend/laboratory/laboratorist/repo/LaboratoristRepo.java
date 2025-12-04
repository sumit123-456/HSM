package com.example.hms_backend.laboratory.laboratorist.repo;

import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.laboratory.laboratorist.entity.Laboratorist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaboratoristRepo extends JpaRepository<Laboratorist,Long> {

    Laboratorist findByUserEntity(UserEntity userEntity);
}
