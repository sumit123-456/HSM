package com.example.hms_backend.patient.service;

import com.example.hms_backend.patient.dto.PatientOpdFormDto;

public interface PatientOpdService {
    public Long createOpdVisit(PatientOpdFormDto formDto);


}
