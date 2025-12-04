package com.example.hms_backend.patient.dto;


import com.example.hms_backend.authentication.dto.AddressViewDto;

public record PatientViewDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String dob,
        Integer age,
        String gender,
        String occupation,
        String bloodGroup,
        String contactInfo,
        String emergencyContact,
        String maritalStatus,

        String note,
        String patient_hospital_id,
        AddressViewDto address
) {}
