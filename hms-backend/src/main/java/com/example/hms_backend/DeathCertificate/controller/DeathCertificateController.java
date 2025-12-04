package com.example.hms_backend.DeathCertificate.controller;

import com.example.hms_backend.DeathCertificate.Service.DeathCertificateService;
import com.example.hms_backend.DeathCertificate.dto.DeathCertificateDTO;
import com.example.hms_backend.customResponse.ApiResponse;
import com.example.hms_backend.patient.dto.PatientIpdViewDTO;
import com.example.hms_backend.patient.service.PatientIpdService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/death-certificate")
public class DeathCertificateController {

    @Autowired
    DeathCertificateService  deathCertificateService;

    @Autowired
    PatientIpdService patientIpdService;

    @GetMapping()
    public ResponseEntity<List<DeathCertificateDTO>> getDeathCertificate()
    {
        List<DeathCertificateDTO> deathCertificateDTOList = deathCertificateService.getAll();
        return new ResponseEntity<>(deathCertificateDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DeathCertificateDTO> createDeathCertificate(@RequestBody DeathCertificateDTO deathCertificateDTO)
    {
       DeathCertificateDTO saved = deathCertificateService.create(deathCertificateDTO);
       return  ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DeathCertificateDTO>> updateDeathCertificate(@PathVariable Long id, @RequestBody DeathCertificateDTO deathCertificateDTO)
    {
        DeathCertificateDTO updated = deathCertificateService.updateDeathCertificate(deathCertificateDTO, id);
       ApiResponse<DeathCertificateDTO> response = new ApiResponse<>("Death Record Udated Successfully!",updated);
       return ResponseEntity.ok(response);
    }

    @GetMapping("/ipd/patient")
    public ResponseEntity<List<PatientIpdViewDTO>> getAllPatientInIpd() {
        return ResponseEntity.ok(patientIpdService.getAllAliveIpdAndErPatients());
    }
}
