package com.example.hms_backend.patient.mapper;

import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.patient.dto.PatientOpdDto;
import com.example.hms_backend.patient.entity.Patient;
import com.example.hms_backend.patient.entity.PatientOpd;
import org.springframework.stereotype.Component;

@Component
public class PatientOpdMapper {

    public PatientOpd toEntity(PatientOpdDto dto, Patient patient, Department dept, Doctor doctor,
                               PatientOpd parentVisit, Department referredDept, Doctor referredDoc) {
        PatientOpd opd = new PatientOpd();
        opd.setPatient(patient);
        opd.setDepartment(dept);
        opd.setDoctor(doctor);
        opd.setParentVisit(parentVisit);
        opd.setVisitDate(dto.getVisitDate());
        opd.setVisitSequenceType(dto.getVisitSequenceType());
        opd.setStatus(dto.getStatus());
        opd.setReferredToDepartment(referredDept);
        opd.setReferredToDoctor(referredDoc);
        opd.setReason(dto.getReason());
        opd.setSymptoms(dto.getSymptoms());
        return opd;

    }
}