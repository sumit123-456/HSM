package com.example.hms_backend.doctor.dto;

public record DoctorViewDTO(
        Long id,
        String name,
        String specialization,
        String experience,
        String qualifications,
        String departmentName
) {}
