package com.example.hms_backend.ambulance.controller;

import com.example.hms_backend.ambulance.dto.DriverDTO;
import com.example.hms_backend.ambulance.service.AmbulanceService;
import com.example.hms_backend.ambulance.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @Autowired
    private AmbulanceService ambulanceService;


    //  Add Driver
    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('DRIVER_ADD')")
    public ResponseEntity<?> addDriver(@Valid @RequestBody DriverDTO dto) {
        try {
            driverService.saveDriver(dto);
            return ResponseEntity.ok("Driver Added Successfully!");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Driver with this License Number already exists!");
        }
    }


}
