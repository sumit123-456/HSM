package com.example.hms_backend.RoomAndBedManager.service;

import com.example.hms_backend.RoomAndBedManager.dto.CreateRoomDTO;
import com.example.hms_backend.RoomAndBedManager.entity.Room;
import com.example.hms_backend.RoomAndBedManager.entity.RoomType;
import com.example.hms_backend.RoomAndBedManager.repo.RoomRepo;
import com.example.hms_backend.RoomAndBedManager.repo.RoomTypeRepo;
import com.example.hms_backend.enums.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomRepo roomRepo;
    @Autowired
    RoomTypeRepo roomTypeRepo;


    @Override
    public void createRoom(CreateRoomDTO dto)
    {
        // checking if room type is available or not if not then filling data of another room type
        RoomType roomType = roomTypeRepo.findRoomTypeByroomTypeName(dto.getRoomTypeName());
        if(roomType==null)
        {
            roomType=new RoomType();
            roomType.setRoomTypeName(dto.getRoomTypeName());
            roomType.setDescription(dto.getDescription());
            roomType.setPricePerDay(dto.getPricePerDay());
            roomTypeRepo.save(roomType);

        }

        Room room = new Room();
        room.setRoomNumber(dto.getRoomNo());
        room.setFloorNumber(dto.getFloor());
        room.setStatus(dto.getStatus());
        room.setRoomName(dto.getRoomName().toUpperCase());
        room.setRoomType(roomType);

        roomRepo.save(room);



    }

    @Override
    public Room findRoomById(Long id) {
        return roomRepo.findById(id).get();
    }

    @Override
    public List<String> findAllAvailaibleRoomTypes() {
        List<String> roomType= roomTypeRepo.findAll().stream()
                .map(rt->rt.getRoomTypeName().toUpperCase()).collect(Collectors.toList());

        return roomType;
    }

    @Override
    public boolean checkRoomNumberExists(String roomNumber) {
        return roomRepo.existsByRoomNumber(roomNumber);
    }


}

