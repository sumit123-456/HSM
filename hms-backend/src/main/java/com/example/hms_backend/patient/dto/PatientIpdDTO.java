package com.example.hms_backend.patient.dto;

import com.example.hms_backend.enums.ChronicConditionsEnum;
import com.example.hms_backend.enums.Enums;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PatientIpdDTO {

    private Long patientId;

    private Long id; // optional for update

    @NotNull(message = "Department is required")
    private Long departmentId;

    @NotNull(message = "Doctor is required")
    private Long doctorId;

    private Long parentVisitId; // optional for first admission

    private boolean readmissionFlag;

    @Size(max = 500, message = "Allergies must be under 500 characters")
    private String allergies;

    private Set<ChronicConditionsEnum> chronicConditions; // multiple allowed

    @NotNull(message = "IPD status is required")
    private Enums.IpdStatus status;

}
