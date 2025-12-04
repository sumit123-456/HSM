package com.example.hms_backend.ambulance.mapper;

import com.example.hms_backend.ambulance.dto.AmbulanceAssignmentDTO;
import com.example.hms_backend.ambulance.entity.AmbulanceAssignment;
import com.example.hms_backend.ambulance.service.AmbulanceService;
import com.example.hms_backend.ambulance.service.DriverService;
import com.example.hms_backend.patient.repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class AmbulanceAssignmentMapper {

    @Autowired
    private AmbulanceService ambulanceService;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    DriverService driverService;


    public AmbulanceAssignment dtoToEntity(AmbulanceAssignmentDTO dto)
    {
        AmbulanceAssignment ambulanceAssignment = new AmbulanceAssignment();
        ambulanceAssignment.setId(dto.getId());
        ambulanceAssignment.setStatus(dto.getStatus());
        ambulanceAssignment.setStartTime(dto.getStartTime());
        ambulanceAssignment.setEndTime(dto.getEndTime());
        ambulanceAssignment.setFromLocation(dto.getFromLocation());
        ambulanceAssignment.setToLocation(dto.getToLocation());

        ambulanceAssignment.setAmbulance(ambulanceService.findAmbulanceById(dto.getAmbulanceId()));

        if(dto.getPatientId()!=null) {
            ambulanceAssignment.setPatient(patientRepo.findById(dto.getPatientId()).
                    orElseThrow(() -> new RuntimeException("patient not found")));
        }
        ambulanceAssignment.setDriver(driverService.findDriverById(dto.getDriverId()));

        return ambulanceAssignment;
    }

    public AmbulanceAssignmentDTO EntityToDTO(AmbulanceAssignment ambulanceAssignment)
    {
        AmbulanceAssignmentDTO dto = new AmbulanceAssignmentDTO();
        dto.setId(ambulanceAssignment.getId());
        dto.setStatus(ambulanceAssignment.getStatus());

        // Define your desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        dto.setStartTime(ambulanceAssignment.getStartTime());
        dto.setEndTime(ambulanceAssignment.getEndTime());
        dto.setFromLocation(ambulanceAssignment.getFromLocation());
        dto.setToLocation(ambulanceAssignment.getToLocation());
        dto.setDriverId(ambulanceAssignment.getDriver().getId());

        if (ambulanceAssignment.getPatient() != null) {
            dto.setPatientId(ambulanceAssignment.getPatient().getId());
        }

        dto.setAmbulanceId(ambulanceAssignment.getAmbulance().getId());
        dto.setAmbulanceVehicleNumber(ambulanceAssignment.getAmbulance().getVehicleNumber());
        dto.setDriverName(ambulanceAssignment.getDriver().getDriverName());



        return dto;
    }

}

