package com.example.hms_backend.patient.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientIpdFormDto {

    @Valid
    @NotNull(message = "Patient details are required")
    private PatientDto patient;

    @Valid
    @NotNull(message = "IPD details are required")
    private PatientIpdDTO ipd;

    @Valid
    @NotNull(message = "ADT details are required")
    private AdtRecordDTO adt;

}
