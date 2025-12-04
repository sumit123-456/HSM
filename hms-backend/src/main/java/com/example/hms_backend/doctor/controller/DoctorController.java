package com.example.hms_backend.doctor.controller;

import com.example.hms_backend.doctor.dto.DoctorViewDTO;
import com.example.hms_backend.doctor.dto.ViewDoctorDTO;
import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.doctor.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @GetMapping("/{departmentId}")
    public ResponseEntity<List<DoctorViewDTO>> getAllDoctorsById(@PathVariable Long departmentId)
    {
        return ResponseEntity.ok(doctorService.findDoctorByDepartmentId(departmentId));
    }

    @GetMapping("/name-ids")
    public ResponseEntity<List<DoctorViewDTO>> getAllDoctorsNameandIDs()
    {
        return ResponseEntity.ok(doctorService.getAllDoctorNameAndId());
    }

    @GetMapping("all")
    public ResponseEntity<List<ViewDoctorDTO>> getAllDoctors()
    {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    //get full doctor details by  departmentId
    @GetMapping("/doctors/{departmentId}")
    public ResponseEntity<List<ViewDoctorDTO>> getAllDoctorsByDepartmentId(@PathVariable Long departmentId) {
        return ResponseEntity.ok(doctorService.getAllDoctorsByDepartmentId(departmentId));
    }
}
