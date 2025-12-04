package com.example.hms_backend.patient.dto;

import com.example.hms_backend.enums.Enums;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AdtRecordDTO {

    private Long id; // auto-generated, no validation


    private Long patientId;

    @NotNull(message = "ADT type is required")
    private Enums.AdtType type; // ADMITTED, DISCHARGED, TRANSFERRED, DECEASED

    // Only required when type = TRANSFERRED, so keep nullable but validate in service
    private Long transferFromDepartmentId;

    private Long transferToDepartmentId;

    @PastOrPresent(message = "Admission date cannot be in the future")
    private LocalDate admissionDate;

    @PastOrPresent(message = "Discharged date cannot be in the future")
    private LocalDate dischargedDate;

    @PastOrPresent(message = "Transferred date cannot be in the future")
    private LocalDate transferredDate;

    @PastOrPresent(message = "Deceased date cannot be in the future")
    private LocalDate deceasedDate;

    @Size(max = 500, message = "Reason must be under 500 characters")
    private String reason;

    @Size(max = 1000, message = "Notes must be under 1000 characters")
    private String notes;
}
