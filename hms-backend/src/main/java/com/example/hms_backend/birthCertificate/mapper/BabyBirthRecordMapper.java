package com.example.hms_backend.birthCertificate.mapper;

import com.example.hms_backend.birthCertificate.dto.BabyBirthRecordDTO;
import com.example.hms_backend.birthCertificate.entity.BabyBirthRecord;
import com.example.hms_backend.patient.entity.PatientIpd;
import org.springframework.stereotype.Component;

@Component
public class BabyBirthRecordMapper {

    public static BabyBirthRecord toEntity(BabyBirthRecordDTO dto, PatientIpd motherPatient) {


        return BabyBirthRecord.builder()
                .id(dto.getId())

                .gender(dto.getGender())
                .dateOfBirth(dto.getDateOfBirth())
                .timeOfBirth(dto.getTimeOfBirth())
                .birthWeight(dto.getBirthWeight())
                .placeOfBirth(dto.getPlaceOfBirth())
                .timeOfIssue(dto.getTimeOfIssue())
                .attendingDoctor(dto.getAttendingDoctor())
                .birthLength(dto.getBirthLength())

                .motherName(dto.getMotherName())
                .fatherName(dto.getFatherName())
                .contactNumber(dto.getContactNumber())
                .address(dto.getAddress())

                .motherPatient(motherPatient)
                .build();
    }

    public static BabyBirthRecordDTO toDTO(BabyBirthRecord entity) {
        BabyBirthRecordDTO dto = new BabyBirthRecordDTO();
        dto.setId(entity.getId());

        dto.setGender(entity.getGender());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setTimeOfBirth(entity.getTimeOfBirth());
        dto.setBirthWeight(entity.getBirthWeight());
        dto.setBirthLength(entity.getBirthLength());
        dto.setAddress(entity.getAddress());
        dto.setMotherName(entity.getMotherName());
        dto.setFatherName(entity.getFatherName());
        dto.setContactNumber(entity.getContactNumber());
        dto.setPlaceOfBirth(entity.getPlaceOfBirth());
        dto.setTimeOfIssue(entity.getTimeOfIssue());
        dto.setAttendingDoctor(entity.getAttendingDoctor());
        dto.setCertificateNumber(entity.getCertificateNumber());

        dto.setMotherPatientId(entity.getMotherPatient().getId());

        return dto;
    }
}
