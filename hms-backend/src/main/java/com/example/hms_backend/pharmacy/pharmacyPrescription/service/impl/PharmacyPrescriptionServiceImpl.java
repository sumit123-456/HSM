package com.example.hms_backend.pharmacy.pharmacyPrescription.service.impl;

import com.example.hms_backend.enums.Enums;
import com.example.hms_backend.pharmacy.pharmacyPrescription.dto.PrescriptionDto;
import com.example.hms_backend.pharmacy.pharmacyPrescription.entity.PharmacyPrescription;
import com.example.hms_backend.pharmacy.pharmacyPrescription.mapper.PharmacyPrescriptionMapper;
import com.example.hms_backend.pharmacy.pharmacyPrescription.repository.PharmacyPrescriptionRepo;
import com.example.hms_backend.pharmacy.pharmacyPrescription.service.PharmacyPrescriptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PharmacyPrescriptionServiceImpl implements PharmacyPrescriptionService {

    private final PharmacyPrescriptionRepo prescriptionRepository;
    private final PharmacyPrescriptionMapper prescriptionMapper;

    public PharmacyPrescriptionServiceImpl(PharmacyPrescriptionRepo prescriptionRepository, PharmacyPrescriptionMapper prescriptionMapper) {
        this.prescriptionRepository = prescriptionRepository;
        this.prescriptionMapper = prescriptionMapper;
    }

    @Override
    public PrescriptionDto save(PrescriptionDto dto) {
        PharmacyPrescription saved = prescriptionMapper.toEntity(dto);
        saved.setStatus(Enums.PharmacyPrescriptionStatus.PENDING);
        saved = prescriptionRepository.save(saved);
        return prescriptionMapper.toDto(saved);
    }

    @Override
    public PrescriptionDto getById(Long id) {
        return prescriptionRepository.findById(id).map(prescriptionMapper::toDto).orElse(null);
    }

    @Override
    public List<PrescriptionDto> listAll() {
        return prescriptionRepository.findAll().stream().map(prescriptionMapper::toDto).collect(Collectors.toList());
    }
}

