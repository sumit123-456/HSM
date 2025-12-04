package com.example.hms_backend.RoomAndBedManager.service;

import com.example.hms_backend.RoomAndBedManager.dto.BedAllotedDTO;
import com.example.hms_backend.RoomAndBedManager.dto.BedAssignmentDTO;
import com.example.hms_backend.RoomAndBedManager.dto.RoomDTO;
import com.example.hms_backend.RoomAndBedManager.entity.BedAssignment;

import java.util.List;
import java.util.Map;

public interface BedAssignmentService
{
    BedAssignment findBedAssignmentById(long id);

    BedAssignment saveBedAssignment(BedAssignmentDTO bedAssignmentDTO);

    List<RoomDTO> findAllTotalVaccantBedsByRoom();

    Map<Long,Map<String, String>> findAllPatientId();

    Map<Long, String> showAllAvailableBedNumbers(Long roomId);

    RoomDTO showRoomDTOById(long roomId);

    List<BedAllotedDTO>  showAllotedBed();

    String releaseBed(Long bedId);

    boolean checkingVacantBedInRoom(Long  roomId);





}

