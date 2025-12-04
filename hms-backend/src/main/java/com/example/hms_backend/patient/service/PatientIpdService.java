package com.example.hms_backend.patient.service;

import com.example.hms_backend.patient.dto.PatientIpdDTO;
import com.example.hms_backend.patient.dto.PatientIpdFormDto;
import com.example.hms_backend.patient.dto.PatientIpdViewDTO;

import java.util.List;

public interface PatientIpdService {
    public Long createIpdAdmission(PatientIpdFormDto formDto);
    public PatientIpdDTO dischargeIpd(Long ipdId);
    public List<PatientIpdDTO> getIpdHistory(Long patientId);

    // New method to get all alive IPD patients
    List<PatientIpdViewDTO> getAllAliveIpdAndErPatients();
}
