package com.example.hms_backend.laboratory.laboratorist.controller;

import com.example.hms_backend.laboratory.laboratorist.dto.LaboratoristResponseDTO;
import com.example.hms_backend.laboratory.laboratorist.service.LaboratoristService;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/laboratorists")
public class Laboratorist {

    @Autowired
    LaboratoristService laboratoristService;

    @GetMapping()
    public ResponseEntity<List<LaboratoristResponseDTO>> getAllLaboratorists() {
        return ResponseEntity.ok(laboratoristService.getAllLaboratorists());
    }

    @GetMapping("/pathlab-technicians")
    public ResponseEntity<List<LaboratoristResponseDTO>> getAllPathlabTechnician() {
        return ResponseEntity.ok(laboratoristService.getAllPathalogyTechnician());
    }

    @GetMapping("/radiology-technicians")
    public ResponseEntity<List<LaboratoristResponseDTO>> getAllRadiologyTechnician() {
        return ResponseEntity.ok(laboratoristService.getAllRadiologyTechnician());
    }

}
