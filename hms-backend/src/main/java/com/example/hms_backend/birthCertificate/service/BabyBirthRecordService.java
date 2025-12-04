package com.example.hms_backend.birthCertificate.service;

import com.example.hms_backend.birthCertificate.dto.BabyBirthRecordDTO;
import com.example.hms_backend.patient.dto.PatientIpdViewDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BabyBirthRecordService {
    BabyBirthRecordDTO createBabyBirthRecord(BabyBirthRecordDTO dto);
    List<BabyBirthRecordDTO> getAllBirthRecords();
    List<PatientIpdViewDTO> getAllIpdPatients();
    BabyBirthRecordDTO updateBabyBirthRecord(BabyBirthRecordDTO dto,Long id);

}

