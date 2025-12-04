package com.example.hms_backend.pharmacy.pharmacyPrescription.service;

import com.example.hms_backend.pharmacy.pharmacyPrescription.dto.PrescriptionDto;

import java.util.List;

public interface PharmacyPrescriptionService {
    PrescriptionDto save(PrescriptionDto dto);
    PrescriptionDto getById(Long id);
    List<PrescriptionDto> listAll();
}

