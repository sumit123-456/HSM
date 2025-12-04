package com.example.hms_backend.RoomAndBedManager.dto;

import com.example.hms_backend.RoomAndBedManager.entity.Bed;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

// this dto is only for showing room data
@Data

public class RoomDTO {

    private Long roomId;


    private String roomName;


    private String roomNumber;

    private String roomType;

    private Long totalBeds;

    private Long vacantBeds;

    private List<Bed> beds;

}
