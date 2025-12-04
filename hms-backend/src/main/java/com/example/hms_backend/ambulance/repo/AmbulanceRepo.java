package com.example.hms_backend.ambulance.repo;

import com.example.hms_backend.ambulance.entity.Ambulance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmbulanceRepo extends JpaRepository<Ambulance, Long> {
}
