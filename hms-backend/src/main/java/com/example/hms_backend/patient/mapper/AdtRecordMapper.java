package com.example.hms_backend.patient.mapper;

import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.patient.dto.AdtRecordDTO;
import com.example.hms_backend.patient.entity.AdtRecord;
import com.example.hms_backend.patient.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class AdtRecordMapper {

    public AdtRecord toEntity(AdtRecordDTO dto, Patient patient, Department fromDept, Department toDept) {
        AdtRecord record = new AdtRecord();
        record.setPatient(patient);
        record.setType(dto.getType());
        record.setTransferFromDepartment(fromDept);
        record.setTransferToDepartment(toDept);
        record.setAdmissionDate(dto.getAdmissionDate());
        record.setDischargedDate(dto.getDischargedDate());
        record.setTransferredDate(dto.getTransferredDate());
        record.setDeceasedDate(dto.getDeceasedDate());
        record.setReason(dto.getReason());
        record.setNotes(dto.getNotes());
        return record;
    }

    public AdtRecordDTO toDto(AdtRecord entity) {
        AdtRecordDTO dto = new AdtRecordDTO();
        dto.setId(entity.getId());
        dto.setPatientId(entity.getPatient().getId());
        dto.setType(entity.getType());
        dto.setTransferFromDepartmentId(entity.getTransferFromDepartment() != null ? entity.getTransferFromDepartment().getId() : null);
        dto.setTransferToDepartmentId(entity.getTransferToDepartment() != null ? entity.getTransferToDepartment().getId() : null);
        dto.setAdmissionDate(entity.getAdmissionDate());
        dto.setDischargedDate(entity.getDischargedDate());
        dto.setTransferredDate(entity.getTransferredDate());
        dto.setDeceasedDate(entity.getDeceasedDate());
        dto.setReason(entity.getReason());
        dto.setNotes(entity.getNotes());
        return dto;
    }

}
