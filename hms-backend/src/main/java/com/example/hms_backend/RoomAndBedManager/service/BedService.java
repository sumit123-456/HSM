package com.example.hms_backend.RoomAndBedManager.service;

import com.example.hms_backend.RoomAndBedManager.dto.BedDTO;
import com.example.hms_backend.RoomAndBedManager.entity.Room;

import java.util.List;

public interface BedService {


    List<Room> getAllRoomsName();


    String saveBed(BedDTO bedDTO);

    boolean existsByBedNumber(String bedNumber);
}