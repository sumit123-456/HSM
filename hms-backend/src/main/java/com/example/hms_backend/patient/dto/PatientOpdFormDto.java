package com.example.hms_backend.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientOpdFormDto {

    private PatientDto patient;
    private PatientOpdDto opd;

}
