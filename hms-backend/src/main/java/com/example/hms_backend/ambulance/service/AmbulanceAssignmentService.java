package com.example.hms_backend.ambulance.service;

import com.example.hms_backend.ambulance.dto.AmbulanceAssignmentDTO;

import java.util.List;

public interface AmbulanceAssignmentService {

    void saveAmbulanceAssignment(AmbulanceAssignmentDTO ambulanceAssignmentDTO);

    List<AmbulanceAssignmentDTO> findAllCompletedAmbulanceAssignments();

    void updateAssignmentStatus(Long id, String status);

    List<AmbulanceAssignmentDTO> findAllInProgressAmbulanceAssignments();
}
