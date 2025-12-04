package com.example.hms_backend.ambulance.repo;

import com.example.hms_backend.ambulance.entity.AmbulanceAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmbulanceAssignmentRepo extends JpaRepository<AmbulanceAssignment, Long> {
}