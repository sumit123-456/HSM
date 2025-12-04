package com.example.hms_backend.patient.dto;

import com.example.hms_backend.enums.Enums;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientOpdDto {
    private Long id; // optional for update

    private Long patientId;

    @NotNull(message = "Department ID is required")
    private Long departmentId;

    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    // Optional: only required for follow-up or referred visits
    private Long parentVisitId;

    @NotNull(message = "Visit date is required")
    private LocalDate visitDate;

    @NotNull(message = "Visit type is required")
    private Enums.VisitSequenceType visitSequenceType;

    @NotNull(message = "Visit status is required")
    private Enums.VisitStatus status;

    // Optional: only required if referred
    private Long referredToDepartmentId;

    private Long referredToDoctorId;

    @Size(max = 500, message = "Reason must be under 500 characters")
    private String reason;

    @Size(max = 1000, message = "Symptoms must be under 1000 characters")
    private String symptoms;

}
