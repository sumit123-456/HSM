package com.example.hms_backend.RoomAndBedManager.dto;

import lombok.Data;

@Data
public class BedAllotedDTO {

    private Long bedAssignmentId;

    private Long bedId;

    private String bedNumber;

    private String roomName;

    private String roomType;

    private String patientName;

    private Long patientId;

    private Long roomId;

}