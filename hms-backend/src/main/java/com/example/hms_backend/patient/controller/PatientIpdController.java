package com.example.hms_backend.patient.controller;

import com.example.hms_backend.patient.dto.PatientIpdDTO;
import com.example.hms_backend.patient.dto.PatientIpdFormDto;
import com.example.hms_backend.patient.service.PatientIpdService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient/ipd")
@RequiredArgsConstructor
public class PatientIpdController {
    private final PatientIpdService patientIpdService;

    @PostMapping("/add")
    public ResponseEntity<Long> admit(@Valid @RequestBody PatientIpdFormDto formDto) {
        Long ipdId = patientIpdService.createIpdAdmission(formDto);
        return ResponseEntity.ok(ipdId);
    }

    @PutMapping("/{id}/discharge")
    public ResponseEntity<PatientIpdDTO> discharge(@PathVariable Long id) {
        return ResponseEntity.ok(patientIpdService.dischargeIpd(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PatientIpdDTO>> history(@PathVariable Long patientId) {
        return ResponseEntity.ok(patientIpdService.getIpdHistory(patientId));
    }

}
