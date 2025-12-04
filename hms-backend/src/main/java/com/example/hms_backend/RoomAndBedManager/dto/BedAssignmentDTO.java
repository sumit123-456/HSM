package com.example.hms_backend.RoomAndBedManager.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BedAssignmentDTO {

    private Long id;

    private Long roomId;

    // IDs instead of full entities
    private Long bedId;

    private String patientHospitalId;

    private Long patientId;

    private Long assignedById;

    private LocalDateTime assignedAt;

    private LocalDateTime releasedAt;



}
