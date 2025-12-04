package com.example.hms_backend.ambulance.dto;

import com.example.hms_backend.ambulance.entity.Ambulance;
import com.example.hms_backend.ambulance.entity.Driver;
import com.example.hms_backend.enums.AmbulanceEnums;
import com.example.hms_backend.patient.entity.Patient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AmbulanceAssignmentDTO {


    private Long id; // for update/edit cases

    @NotNull(message = "Ambulance ID is required")
    private Long ambulanceId;

    @NotNull(message = "Driver ID is required")
    private Long driverId;

    private Long patientId; // optional, so no @NotNull

    @NotBlank(message = "From location is required")
    private String fromLocation;

    @NotBlank(message = "To location is required")
    private String toLocation;

    @NotNull(message = "Status is required")
    private AmbulanceEnums.AssignmentStatus status; // Scheduled, InProgress, Completed

    private String driverName;

    private String ambulanceVehicleNumber;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Driver driver;                // if we want to access full object

    private Ambulance ambulance;

    private Patient patient;
}

