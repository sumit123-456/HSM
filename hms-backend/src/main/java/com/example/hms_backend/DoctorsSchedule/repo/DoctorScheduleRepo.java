package com.example.hms_backend.DoctorsSchedule.repo;

import com.example.hms_backend.DoctorsSchedule.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorScheduleRepo extends JpaRepository<DoctorSchedule, Long> {

    boolean existsByDoctor_Id(Long doctorId);

//    List<DoctorSchedule> findByDoctorNameContainingIgnoreCase(String doctorName);

    List<DoctorSchedule> findByDepartmentId(Long departmentId);
}