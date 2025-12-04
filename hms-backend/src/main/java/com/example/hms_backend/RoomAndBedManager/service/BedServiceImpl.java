package com.example.hms_backend.RoomAndBedManager.service;

import com.example.hms_backend.RoomAndBedManager.dto.BedDTO;
import com.example.hms_backend.RoomAndBedManager.entity.Bed;
import com.example.hms_backend.RoomAndBedManager.entity.Room;
import com.example.hms_backend.RoomAndBedManager.repo.BedRepo;
import com.example.hms_backend.RoomAndBedManager.repo.RoomRepo;
import com.example.hms_backend.enums.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BedServiceImpl implements BedService{

    @Autowired
    RoomRepo roomRepo;

    @Autowired
    BedRepo bedRepo;

    // used to return all available rooms in hospital
    @Override
    public List<Room> getAllRoomsName() {
        List<Room> rooms = new ArrayList<>();
        rooms = roomRepo.findAll();
        return rooms;
    }

    @Override
    public String saveBed(BedDTO bedDTO)
    {
        Bed bed = new Bed();
        bed.setBedNumber(bedDTO.getBedNumber());
        bed.setRoom(roomRepo.findById(bedDTO.getRoomId()).orElseThrow(() -> new RuntimeException("Room not found")));
        bed.setStatus(Enums.BedStatus.VACCANT);
        System.out.println("bed Service");
        bedRepo.save(bed);
        return "Bed saved successfully";
    }

    @Override
    public boolean existsByBedNumber(String bedNumber) {
        return bedRepo.existsByBedNumber(bedNumber);
    }
}
