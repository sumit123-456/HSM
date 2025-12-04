package com.example.hms_backend.RoomAndBedManager.repo;

import com.example.hms_backend.RoomAndBedManager.entity.Bed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BedRepo extends JpaRepository<Bed, Long> {

    boolean existsByBedNumber(String bedNumber);

}
