package com.example.hms_backend.ambulance.controller;

import com.example.hms_backend.ambulance.service.AmbulanceAssignmentService;
import com.example.hms_backend.ambulance.service.AmbulanceService;
import com.example.hms_backend.ambulance.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ambulance")
public class AmbulanceViewController {

    @Autowired
    private AmbulanceService ambulanceService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private AmbulanceAssignmentService ambulanceAssignmentService;

    // ---------------------------
    // 1. GET ALL Ambulance View Data
    // ---------------------------
    @GetMapping("/view")
    public ResponseEntity<Map<String, Object>> getAmbulanceView() {

        Map<String, Object> response = new HashMap<>();
        response.put("ambulances", ambulanceService.findAllAmbulanceFullObject());
        response.put("drivers", driverService.findAllDriverFullObject());
        response.put("assignments", ambulanceAssignmentService.findAllInProgressAmbulanceAssignments());
        response.put("completedAssignments", ambulanceAssignmentService.findAllCompletedAmbulanceAssignments());

        return ResponseEntity.ok(response);
    }

    // ---------------------------
    // 2. GET Ambulance Table
    // ---------------------------
    @GetMapping("/list")
    public ResponseEntity<List<?>> getAmbulanceList() {
        return ResponseEntity.ok(ambulanceService.findAllAmbulanceFullObject());
    }

    // ---------------------------
    // 3. GET Driver Table
    // ---------------------------
    @GetMapping("/drivers")
    public ResponseEntity<List<?>> getDriverList() {
        return ResponseEntity.ok(driverService.findAllDriverFullObject());
    }

    // ---------------------------
    // 4. GET In-Progress Assignments
    // ---------------------------
    @GetMapping("/assignments")
    public ResponseEntity<List<?>> getAssignments() {
        return ResponseEntity.ok(ambulanceAssignmentService.findAllInProgressAmbulanceAssignments());
    }

    // ---------------------------
    // 5. GET Completed Assignment History
    // ---------------------------
    @GetMapping("/assignments/history")
    public ResponseEntity<List<?>> getAssignmentHistory() {
        return ResponseEntity.ok(ambulanceAssignmentService.findAllCompletedAmbulanceAssignments());
    }
}

