package com.example.hms_backend.DeathCertificate.mapper;

import com.example.hms_backend.DeathCertificate.entity.DeathCertificate;
import com.example.hms_backend.DeathCertificate.dto.DeathCertificateDTO;
import com.example.hms_backend.patient.entity.Patient;
import com.example.hms_backend.patient.entity.PatientIpd;
import org.springframework.stereotype.Component;

@Component
public class DeathCertificateMapper {

    public DeathCertificate toEntity(DeathCertificateDTO dto, PatientIpd patient) {
        return DeathCertificate.builder()
                .id(dto.getId())
                .hospitalName(dto.getHospitalName())
                .certificateNumber(dto.getCertificateNumber())
                .fullName(dto.getFullName())
                .gender(dto.getGender())
                .dateOfDeath(dto.getDateOfDeath())
                .timeOfDeath(dto.getTimeOfDeath())
                .ageAtDeath(dto.getAgeAtDeath())
                .causeOfDeath(dto.getCauseOfDeath())
                .placeOfDeath(dto.getPlaceOfDeath())
                .address(dto.getAddress())
                .dateOfBirth(dto.getDateOfBirth())
                .contactNumber(dto.getContactNumber())
                .attendingDoctor(dto.getAttendingDoctor())

                .issueDate(dto.getIssueDate())
                .patient(patient)
                .build();
    }

    public DeathCertificateDTO toDTO(DeathCertificate certificate) {
        return DeathCertificateDTO.builder()
                .id(certificate.getId())
                .hospitalName(certificate.getHospitalName())
                .certificateNumber(certificate.getCertificateNumber())
                .fullName(certificate.getFullName())
                .gender(certificate.getGender())
                .dateOfDeath(certificate.getDateOfDeath())
                .timeOfDeath(certificate.getTimeOfDeath())
                .ageAtDeath(certificate.getAgeAtDeath())
                .causeOfDeath(certificate.getCauseOfDeath())
                .placeOfDeath(certificate.getPlaceOfDeath())
                .address(certificate.getAddress())
                .attendingDoctor(certificate.getAttendingDoctor())
                .dateOfBirth(certificate.getDateOfBirth())
                .contactNumber(certificate.getContactNumber())
                .issueDate(certificate.getIssueDate())
                .patientId(certificate.getPatient().getId())
                .build();
    }
}

