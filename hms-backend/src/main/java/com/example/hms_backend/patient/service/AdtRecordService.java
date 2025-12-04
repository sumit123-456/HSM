package com.example.hms_backend.patient.service;

import com.example.hms_backend.patient.dto.AdtRecordDTO;
import com.example.hms_backend.patient.entity.AdtRecord;
import com.example.hms_backend.patient.entity.Patient;

public interface AdtRecordService {
    public AdtRecord createAdtRecord(AdtRecordDTO dto, Patient patient);
}
