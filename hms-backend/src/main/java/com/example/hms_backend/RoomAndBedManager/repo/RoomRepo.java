package com.example.hms_backend.RoomAndBedManager.repo;

import com.example.hms_backend.RoomAndBedManager.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {

    boolean existsByRoomNumber(String roomNumber);
}
