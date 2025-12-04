package com.example.hms_backend.patient.dto;

import java.time.LocalDate;

//this record is foer view ipd mother patient in birth certificate
public record PatientIpdViewDTO(
        Long id,
        String name,
        String hospital_patient_id,

        String contactNumber,

        String address,
        String attendingDoctor,

        String gender,

        LocalDate dob

) {

}
