package com.example.hms_backend.birthCertificate.service;

import com.example.hms_backend.birthCertificate.dto.BabyBirthRecordDTO;
import com.example.hms_backend.birthCertificate.entity.BabyBirthRecord;
import com.example.hms_backend.birthCertificate.mapper.BabyBirthRecordMapper;
import com.example.hms_backend.birthCertificate.repo.BabyBirthRecordRepository;
import com.example.hms_backend.enums.Enums;
import com.example.hms_backend.patient.dto.PatientIpdViewDTO;
import com.example.hms_backend.patient.entity.PatientIpd;
import com.example.hms_backend.patient.repo.PatientIpdRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BabyBirthRecordServiceImpl implements BabyBirthRecordService {

    private final BabyBirthRecordRepository birthRecordRepository;
    private final PatientIpdRepo patientIpdRepo;
    private final BabyBirthRecordMapper birthRecordMapper;

    @Override
    public BabyBirthRecordDTO createBabyBirthRecord(BabyBirthRecordDTO dto) {

        PatientIpd mother = patientIpdRepo.findById(dto.getMotherPatientId())
                .orElseThrow(() -> new RuntimeException("Mother patient not found"));

        BabyBirthRecord record = BabyBirthRecordMapper.toEntity(dto, mother);

        BabyBirthRecord saved = birthRecordRepository.save(record);

        saved.setCertificateNumber("BC000"+saved.getId().toString());

        saved = birthRecordRepository.save(saved);

        return BabyBirthRecordMapper.toDTO(saved);
    }

    @Override
    public List<BabyBirthRecordDTO> getAllBirthRecords() {
        return birthRecordRepository.findAll()
                .stream()
                .map(BabyBirthRecordMapper::toDTO)
                .collect(Collectors.toList());
    }

  //get all ipd patient For mother filter by Gender
    public List<PatientIpdViewDTO> getAllIpdPatients() {
        return patientIpdRepo.findAll()
                .stream()
                .filter(ipd -> ipd.getPatient().getGender() == Enums.Gender.FEMALE) //  Filter females
                .map(patientIpd -> new PatientIpdViewDTO(
                        patientIpd.getId(),
                        patientIpd.getPatient().getFirstName() + " " + patientIpd.getPatient().getLastName(),
                        patientIpd.getPatient().getPatientHospitalId(),

                        patientIpd.getPatient().getContactInfo(),

                        patientIpd.getPatient().getAddress().getAddressLine1()+", "+
                                patientIpd.getPatient().getAddress().getAddressLine2()+", "+
                                patientIpd.getPatient().getAddress().getCity()+" ,"
                                +patientIpd.getPatient().getAddress().getDistrict()+" ,"+
                                patientIpd.getPatient().getAddress().getPincode()+" ,"+
                                patientIpd.getPatient().getAddress().getState()+" ,"+
                                patientIpd.getPatient().getAddress().getCountry(),
                        patientIpd.getDoctor().getUserEntity().getUserInfo().getFirstName()+" "
                                +patientIpd.getDoctor().getUserEntity().getUserInfo().getLastName(),
                        patientIpd.getPatient().getGender().toString(),
                        patientIpd.getPatient().getDob()

                ))
                .collect(Collectors.toList());
    }

    @Override
    public BabyBirthRecordDTO updateBabyBirthRecord(BabyBirthRecordDTO dto,Long id) {

        // ✅ Find existing record
        BabyBirthRecord br = birthRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Baby birth record not found"));

        // ✅ Find mother patient (if changed)
        PatientIpd mother = patientIpdRepo.findById(dto.getMotherPatientId())
                .orElseThrow(() -> new RuntimeException("Mother patient not found"));

        // ✅ Update fields (DO NOT REPLACE ENTITY)
        br.setGender(dto.getGender());
        br.setDateOfBirth(dto.getDateOfBirth());
        br.setTimeOfBirth(dto.getTimeOfBirth());
        br.setBirthWeight(dto.getBirthWeight());
        br.setBirthLength(dto.getBirthLength());
        br.setPlaceOfBirth(dto.getPlaceOfBirth());
        br.setAttendingDoctor(dto.getAttendingDoctor());
        br.setTimeOfIssue(dto.getTimeOfIssue());
        br.setMotherName(dto.getMotherName());
        br.setFatherName(dto.getFatherName());
        br.setContactNumber(dto.getContactNumber());


        // ✅ Update mother link
        br.setPatientIpd(mother);

        // ✅ Save changes (only once)
        BabyBirthRecord saved = birthRecordRepository.save(br);

        // ✅ Ensure certificate number is set only if missing
        if (saved.getCertificateNumber() == null || saved.getCertificateNumber().isBlank()) {
            saved.setCertificateNumber("BC000" + saved.getId());
            saved = birthRecordRepository.save(saved);
        }

        // ✅ Convert and return DTO
        return birthRecordMapper.toDTO(saved);
    }
}
