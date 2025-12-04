package com.example.hms_backend.patient.dto;

public record PatientNameAndIdViewDTO(
        Long id,
        String fullName,
        String hospitalPatientId,
        Integer age,
        String gender

) {
}
