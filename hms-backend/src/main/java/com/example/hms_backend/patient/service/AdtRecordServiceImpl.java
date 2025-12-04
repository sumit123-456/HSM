package com.example.hms_backend.patient.service;

import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.department.repo.DepartmentRepo;
import com.example.hms_backend.enums.Enums;
import com.example.hms_backend.patient.dto.AdtRecordDTO;
import com.example.hms_backend.patient.entity.AdtRecord;
import com.example.hms_backend.patient.entity.Patient;
import com.example.hms_backend.patient.mapper.AdtRecordMapper;
import com.example.hms_backend.patient.repo.AdtRecordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AdtRecordServiceImpl implements AdtRecordService {
    @Autowired
    private AdtRecordRepo adtRecordRepo;
    @Autowired private DepartmentRepo departmentRepo;
    @Autowired private AdtRecordMapper adtRecordMapper;

    @Override
    public AdtRecord createAdtRecord(AdtRecordDTO dto, Patient patient) {
        Department fromDept = null;
        Department toDept = null;

        if (dto.getTransferFromDepartmentId() != null) {
            fromDept = departmentRepo.findById(dto.getTransferFromDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Transfer-from department not found"));
        }
        if (dto.getTransferToDepartmentId() != null) {
            toDept = departmentRepo.findById(dto.getTransferToDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Transfer-to department not found"));
        }

        AdtRecord adt = adtRecordMapper.toEntity(dto, patient, fromDept, toDept);

        adt.setType(dto.getType() != null ? dto.getType() : Enums.AdtType.ADMITTED);
        adt.setReason(dto.getReason());
        adt.setNotes(dto.getNotes());

        switch (adt.getType()) {
            case ADMITTED:
                adt.setAdmissionDate(dto.getAdmissionDate() != null ? dto.getAdmissionDate() : LocalDate.now());
                break;
            case DISCHARGED:
                adt.setDischargedDate(dto.getDischargedDate() != null ? dto.getDischargedDate() : LocalDate.now());
                break;
            case TRANSFERRED:
                if (fromDept == null || toDept == null) {
                    throw new RuntimeException("Transfer requires both from and to departments");
                }
                adt.setTransferredDate(dto.getTransferredDate() != null ? dto.getTransferredDate() : LocalDate.now());
                break;
            case DECEASED:
                adt.setDeceasedDate(dto.getDeceasedDate() != null ? dto.getDeceasedDate() : LocalDate.now());
                break;
        }

        return adtRecordRepo.save(adt);
    }
}
