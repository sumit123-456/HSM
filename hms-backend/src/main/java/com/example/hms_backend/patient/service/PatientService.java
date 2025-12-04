package com.example.hms_backend.patient.service;

import com.example.hms_backend.patient.dto.PatientDto;
import com.example.hms_backend.patient.dto.PatientNameAndIdViewDTO;
import com.example.hms_backend.patient.dto.PatientViewDto;
import com.example.hms_backend.patient.entity.Patient;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PatientService {

     List<PatientViewDto> getAllPatients();

    String getPatientFullNameById(Long patientId);

    public List<PatientNameAndIdViewDTO> getAllPatientsNameAndId();

    public List<PatientDto>  getAllIpdAndErPatients();
}
