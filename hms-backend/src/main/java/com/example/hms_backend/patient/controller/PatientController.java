package com.example.hms_backend.patient.controller;

import com.example.hms_backend.patient.dto.PatientDto;
import com.example.hms_backend.patient.dto.PatientNameAndIdViewDTO;
import com.example.hms_backend.patient.dto.PatientViewDto;
import com.example.hms_backend.patient.entity.Patient;
import com.example.hms_backend.patient.repo.PatientRepo;
import com.example.hms_backend.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    PatientService patientService;

    @GetMapping("/all")
    public ResponseEntity<List<PatientViewDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/names-and-ids")
    public ResponseEntity<List<PatientNameAndIdViewDTO>> getAllNameAndIdPatients() {
        return ResponseEntity.ok(patientService.getAllPatientsNameAndId());
    }

    //return all ipd and opd PAtients
    @GetMapping("/ipd-er")
    public ResponseEntity<List<PatientDto>> getAllIpdAndErPatients()
    {
        return ResponseEntity.ok(patientService.getAllIpdAndErPatients());
    }
}
