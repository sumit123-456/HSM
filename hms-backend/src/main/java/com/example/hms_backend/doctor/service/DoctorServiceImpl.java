package com.example.hms_backend.doctor.service;

import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.department.repo.DepartmentRepo;
import com.example.hms_backend.doctor.dto.DoctorViewDTO;
import com.example.hms_backend.doctor.dto.ViewDoctorDTO;
import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.doctor.mapper.DoctorMapper;
import com.example.hms_backend.doctor.repo.DoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    DoctorRepo doctorRepo;

    @Autowired
    DoctorMapper doctorMapper;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Override
    public List<DoctorViewDTO> findDoctorByDepartmentId(Long departmentId) {
        return doctorRepo.findDoctorByDepartmentId(departmentId).stream()
                .map(d -> new DoctorViewDTO(
                        d.getId(),
                        d.getUserEntity().getUserInfo().getFirstName() + " " + d.getUserEntity().getUserInfo().getLastName(),
                        d.getSpecialization(),
                        d.getExperience(),
                        d.getQualifications(),
                        d.getDepartment().getDepartment_name()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public String getDoctorFullNameById(Long doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(()-> new RuntimeException("doctor not found"));
        return doctor.getUserEntity().getUserInfo().getFirstName() + " " + doctor.getUserEntity().getUserInfo().getLastName();
    }

    @Override
    public List<ViewDoctorDTO> getAllDoctors() {
        return doctorRepo.findAll()
                .stream()
                .map(doctorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ViewDoctorDTO> getAllDoctorsByDepartmentId(Long departmentId) {
        return doctorRepo.findDoctorByDepartmentId(departmentId).stream()
                .map(doctorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorViewDTO> getAllDoctorNameAndId() {
        return doctorRepo.findAll().stream()
                .map(d -> new DoctorViewDTO(
                        d.getId(),
                        d.getUserEntity().getUserInfo().getFirstName() + " " + d.getUserEntity().getUserInfo().getLastName(),
                        d.getSpecialization(),
                        d.getExperience(),
                        d.getQualifications(),
                        d.getDepartment().getDepartment_name()
                ))
                .collect(Collectors.toList());
    }


}
