package com.example.hms_backend.patient.controller;

import com.example.hms_backend.patient.dto.PatientIpdDTO;
import com.example.hms_backend.patient.dto.PatientOpdFormDto;
import com.example.hms_backend.patient.entity.PatientIpd;
import com.example.hms_backend.patient.service.PatientOpdService;
import com.example.hms_backend.patient.service.PatientIpdServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patient/opd")
public class PatientOpdController {
    @Autowired
    private PatientOpdService patientOpdService;

    @PostMapping("/add")
    public ResponseEntity<?> createOpdVisit(@Valid @RequestBody PatientOpdFormDto formDto) {
        try {
            Long visitId = patientOpdService.createOpdVisit(formDto);
            return ResponseEntity.ok("OPD visit created with ID: " + visitId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating OPD visit: " + e.getMessage());
        }
    }



}
