package com.example.hms_backend.RoomAndBedManager.repo;

import com.example.hms_backend.RoomAndBedManager.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeRepo extends JpaRepository<RoomType,Long> {

    RoomType findRoomTypeByroomTypeName(String roomTypeName);
}
