package com.example.hms_backend.patient.mapper;

import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.patient.dto.PatientIpdDTO;
import com.example.hms_backend.patient.entity.Patient;
import com.example.hms_backend.patient.entity.PatientIpd;
import org.springframework.stereotype.Component;

@Component
public class PatientIpdMapper {

    public PatientIpd toEntity(PatientIpdDTO dto,
                               Patient patient,
                               Department department,
                               Doctor doctor,
                               PatientIpd parentVisit) {

        PatientIpd ipd = new PatientIpd();
        ipd.setPatient(patient);
        ipd.setDepartment(department);
        ipd.setDoctor(doctor);
        ipd.setParentVisit(parentVisit);

        ipd.setReadmissionFlag(dto.isReadmissionFlag());
        ipd.setAllergies(dto.getAllergies());
        ipd.setChronicConditions(dto.getChronicConditions());
        ipd.setStatus(dto.getStatus());

        return ipd;
    }
    public PatientIpdDTO toDto(PatientIpd entity) {
        PatientIpdDTO dto = new PatientIpdDTO();
        dto.setId(entity.getId());
        dto.setDepartmentId(entity.getDepartment().getId());
        dto.setDoctorId(entity.getDoctor().getId());
        dto.setParentVisitId(entity.getParentVisit() != null ? entity.getParentVisit().getId() : null);

        dto.setReadmissionFlag(entity.isReadmissionFlag());
        dto.setAllergies(entity.getAllergies());
        dto.setChronicConditions(entity.getChronicConditions());
        dto.setStatus(entity.getStatus());

        return dto;
    }


}
