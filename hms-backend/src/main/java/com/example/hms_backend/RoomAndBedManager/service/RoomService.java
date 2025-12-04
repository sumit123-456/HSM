package com.example.hms_backend.RoomAndBedManager.service;

import com.example.hms_backend.RoomAndBedManager.dto.CreateRoomDTO;
import com.example.hms_backend.RoomAndBedManager.entity.Room;

import java.util.List;

public interface RoomService {

    void createRoom(CreateRoomDTO dto);

    Room findRoomById(Long id);

    List<String> findAllAvailaibleRoomTypes();

    boolean checkRoomNumberExists(String roomNumber);


}

