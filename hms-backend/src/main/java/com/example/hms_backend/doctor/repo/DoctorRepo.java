package com.example.hms_backend.doctor.repo;

import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.doctor.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepo extends JpaRepository<Doctor, Long> {

    Doctor findByUserEntity(UserEntity userEntity);// user_id is FK in doctor table

    List<Doctor> findDoctorByDepartmentId(Long departmentId);
}
