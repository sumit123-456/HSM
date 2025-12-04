package com.example.hms_backend.ambulance.service;

import com.example.hms_backend.ambulance.dto.AmbulanceAssignmentDTO;
import com.example.hms_backend.ambulance.entity.AmbulanceAssignment;
import com.example.hms_backend.ambulance.mapper.AmbulanceAssignmentMapper;
import com.example.hms_backend.ambulance.repo.AmbulanceAssignmentRepo;
import com.example.hms_backend.enums.AmbulanceEnums;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AmbulanceAssignmentServiceImpl implements AmbulanceAssignmentService {

    @Autowired
    AmbulanceAssignmentRepo ambulanceAssignmentRepo;

    @Autowired
    AmbulanceAssignmentMapper ambulanceAssignmentMapper;



    @Override
    public void saveAmbulanceAssignment(AmbulanceAssignmentDTO ambulanceAssignmentDTO) {

        AmbulanceAssignment ambulanceAssignment = ambulanceAssignmentMapper.dtoToEntity(ambulanceAssignmentDTO);
        ambulanceAssignmentRepo.save(ambulanceAssignment);
        System.out.println("Ambulance Assignment Saved Successfully!");
    }

    // show assignment list which is completed
    @Override
    public List<AmbulanceAssignmentDTO> findAllCompletedAmbulanceAssignments() {

        return ambulanceAssignmentRepo.findAll().stream().filter(as->as.getStatus() == AmbulanceEnums.AssignmentStatus.COMPLETED)
                .map(as -> ambulanceAssignmentMapper.EntityToDTO(as)).collect(Collectors.toList());
    }

    //show ambulance assignment which is in progress or schedule
    @Override
    public List<AmbulanceAssignmentDTO> findAllInProgressAmbulanceAssignments() {
        return ambulanceAssignmentRepo.findAll().stream()
                .filter(as -> as.getStatus() == AmbulanceEnums.AssignmentStatus.SCHEDULED
                        || as.getStatus() == AmbulanceEnums.AssignmentStatus.IN_PROGRESS)
                .map(as -> ambulanceAssignmentMapper.EntityToDTO(as))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateAssignmentStatus(Long id, String status)
    {
        AmbulanceAssignment  ambulanceAssignment = ambulanceAssignmentRepo.findById(id).orElseThrow(()-> new RuntimeException("Ambulance Assignment Not Found!"));
        ambulanceAssignment.setStatus(AmbulanceEnums.AssignmentStatus.valueOf(status));

        if (status.equals(AmbulanceEnums.AssignmentStatus.IN_PROGRESS.toString())||status.equals(AmbulanceEnums.AssignmentStatus.SCHEDULED.toString()))
        {
            ambulanceAssignment.getAmbulance().setAmbulanceStatus(AmbulanceEnums.AmbulanceStatus.ON_DUTY);
        }
        else if (status.equals(AmbulanceEnums.AssignmentStatus.COMPLETED.toString()))
        {
            ambulanceAssignment.getAmbulance().setAmbulanceStatus(AmbulanceEnums.AmbulanceStatus.AVAILABLE);
        }
    }
}

