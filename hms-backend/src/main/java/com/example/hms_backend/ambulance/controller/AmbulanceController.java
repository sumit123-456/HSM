package com.example.hms_backend.ambulance.controller;

import com.example.hms_backend.ambulance.dto.AmbulanceDTO;
import com.example.hms_backend.ambulance.service.AmbulanceService;
import com.example.hms_backend.enums.AmbulanceEnums;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ambulance")

public class AmbulanceController {

    @Autowired
    private AmbulanceService ambulanceService;

    // ✅ Get dropdown values (Status & Type)
    @GetMapping("/form-data")
    public ResponseEntity<?> getAmbulanceFormData() {
        return ResponseEntity.ok(
                new Object() {
                    public AmbulanceEnums.AmbulanceStatus[] status = AmbulanceEnums.AmbulanceStatus.values();
                    public AmbulanceEnums.AmbulanceType[] type = AmbulanceEnums.AmbulanceType.values();
                }
        );
    }

    // ✅ Add Ambulance
    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('AMBULANCE_ADD')")
    public ResponseEntity<?> addAmbulance(@Valid @RequestBody AmbulanceDTO ambulanceDTO) {
        try {
            ambulanceService.saveAmbulance(ambulanceDTO);
            return ResponseEntity.ok("Ambulance added successfully!");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ambulance with same Vehicle Number Already Exists");
        }
    }
}
