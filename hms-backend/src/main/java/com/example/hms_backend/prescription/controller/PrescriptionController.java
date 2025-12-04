package com.example.hms_backend.prescription.controller;

import com.example.hms_backend.prescription.dto.PrescriptionDTO;
import com.example.hms_backend.prescription.service.PrescriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService service;

    @PostMapping
    public ResponseEntity<PrescriptionDTO> create(@RequestBody @Valid PrescriptionDTO dto) {
        return ResponseEntity.ok(service.createPrescription(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PrescriptionDTO>> getAll() {
        return ResponseEntity.ok(service.getAllPrescription());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPrescription(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PrescriptionDTO>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(service.getByPatient(patientId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deletePrescription(id);
        return ResponseEntity.ok("Prescription deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrescriptionDTO> update(@PathVariable Long id,
                                                  @RequestBody @Valid PrescriptionDTO dto)
    {
        return ResponseEntity.ok(service.updatePrescription(id, dto));
    }

    @PutMapping("/update-status/{prescriptionId}/{status}")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long prescriptionId,
            @PathVariable String status) {
        return ResponseEntity.ok(service.updateStatus(prescriptionId, status));
    }
}
