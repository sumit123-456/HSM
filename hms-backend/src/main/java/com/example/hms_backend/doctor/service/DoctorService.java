package com.example.hms_backend.doctor.service;

import com.example.hms_backend.doctor.dto.DoctorViewDTO;
import com.example.hms_backend.doctor.dto.ViewDoctorDTO;
import com.example.hms_backend.doctor.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorService {

    List<DoctorViewDTO> findDoctorByDepartmentId(Long departmentId);

    String getDoctorFullNameById(Long doctorId);

    List<ViewDoctorDTO> getAllDoctors();

    //get full doctor objects by department id
    public List<ViewDoctorDTO> getAllDoctorsByDepartmentId(Long departmentId);

    List<DoctorViewDTO> getAllDoctorNameAndId();

}
