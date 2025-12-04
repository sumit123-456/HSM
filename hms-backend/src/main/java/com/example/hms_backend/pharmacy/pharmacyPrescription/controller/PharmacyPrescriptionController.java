package com.example.hms_backend.pharmacy.pharmacyPrescription.controller;

import com.example.hms_backend.pharmacy.pharmacyPrescription.dto.PrescriptionDto;
import com.example.hms_backend.pharmacy.pharmacyPrescription.service.PharmacyPrescriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacy/prescriptions")
public class PharmacyPrescriptionController {

    private final PharmacyPrescriptionService prescriptionService;

    public PharmacyPrescriptionController(PharmacyPrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping
    public ResponseEntity<PrescriptionDto> create(@RequestBody PrescriptionDto dto) {
        return ResponseEntity.ok(prescriptionService.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<PrescriptionDto>> list() {
        return ResponseEntity.ok(prescriptionService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionDto> get(@PathVariable Long id) {
        PrescriptionDto d = prescriptionService.getById(id);
        if (d == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(d);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PrescriptionDto> update(@PathVariable Long id, @RequestBody PrescriptionDto dto) {
        PrescriptionDto existing = prescriptionService.getById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        dto.setId(id); // ensure the ID is set
        PrescriptionDto updated = prescriptionService.save(dto);
        return ResponseEntity.ok(updated);
    }

}

