package com.example.hms_backend.DeathCertificate.Service;

import com.example.hms_backend.DeathCertificate.entity.DeathCertificate;
import com.example.hms_backend.DeathCertificate.dto.DeathCertificateDTO;
import com.example.hms_backend.DeathCertificate.mapper.DeathCertificateMapper;
import com.example.hms_backend.DeathCertificate.repo.DeathCertificateRepo;
import com.example.hms_backend.enums.Enums;
import com.example.hms_backend.patient.dto.PatientDto;
import com.example.hms_backend.patient.dto.PatientIpdViewDTO;
import com.example.hms_backend.patient.dto.PatientViewDto;
import com.example.hms_backend.patient.entity.Patient;
import com.example.hms_backend.patient.entity.PatientIpd;
import com.example.hms_backend.patient.mapper.PatientMapper;
import com.example.hms_backend.patient.repo.PatientIpdRepo;
import com.example.hms_backend.patient.repo.PatientRepo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DeathCertificateServiceImpl implements DeathCertificateService {

    private final DeathCertificateRepo repo;
    private final DeathCertificateMapper mapper;
    private final PatientIpdRepo patientIpdRepo;


    public DeathCertificateServiceImpl(DeathCertificateRepo repo, PatientRepo patientRepo, DeathCertificateMapper mapper, PatientMapper patientMapper, PatientIpdRepo patientIpdRepo) {
        this.repo = repo;
        this.mapper = mapper;
        this.patientIpdRepo = patientIpdRepo;
    }

    @Override
    public DeathCertificateDTO create(DeathCertificateDTO dto) {
        PatientIpd patient = patientIpdRepo.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient Not Found"));

        DeathCertificate certificate = mapper.toEntity(dto, patient);
        certificate = repo.save(certificate);

        certificate.setCertificateNumber("DC000"+certificate.getId());

        return mapper.toDTO(repo.save(certificate));
    }

    @Override
    public List<DeathCertificateDTO> getAll() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public DeathCertificateDTO getById(Long id) {
        return repo.findById(id).map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Record Not Found"));
    }

//    public List<PatientIpdViewDTO> getAllIpdPatients() {
//        return patientIpdRepo.findAll()
//                .stream()
//
//                .map(patientIpd -> new PatientIpdViewDTO(
//                        patientIpd.getId(),
//                        patientIpd.getPatient().getFirstName() + " " + patientIpd.getPatient().getLastName(),
//                        patientIpd.getPatient().getPatientHospitalId(),
//
//                        patientIpd.getPatient().getContactInfo(),
//
//                        patientIpd.getPatient().getAddress().getAddressLine1()+", "+
//                                patientIpd.getPatient().getAddress().getAddressLine2()+", "+
//                                patientIpd.getPatient().getAddress().getCity()+" ,"
//                                +patientIpd.getPatient().getAddress().getDistrict()+" ,"+
//                                patientIpd.getPatient().getAddress().getPincode()+" ,"+
//                                patientIpd.getPatient().getAddress().getState()+" ,"+
//                                patientIpd.getPatient().getAddress().getCountry(),
//                        patientIpd.getDoctor().getUserEntity().getUserInfo().getFirstName()+" "
//                                +patientIpd.getDoctor().getUserEntity().getUserInfo().getLastName(),
//                        patientIpd.getPatient().getGender().toString()
//
//                ))
//                .collect(Collectors.toList());
//    }

//    public List<PatientIpdViewDTO> getAllAliveIpdPatients() {
//
//        // Step 1: Get dead patient IDs
//        List<Long> deadPatientIds = repo.findAllDeadPatientIds();
//
//        // Step 2: Filter IPD patients whose ID is NOT in deadPatientIds
//        return patientIpdRepo.findAll().stream()
//                .filter(ipd -> !deadPatientIds.contains(ipd.getId())) // alive only
//                .map(patientIpd -> new PatientIpdViewDTO(
//                        patientIpd.getId(),
//                        patientIpd.getPatient().getFirstName() + " " + patientIpd.getPatient().getLastName(),
//                        patientIpd.getPatient().getPatientHospitalId(),
//
//                        patientIpd.getPatient().getContactInfo(),
//
//                        patientIpd.getPatient().getAddress().getAddressLine1()+", "+
//                                patientIpd.getPatient().getAddress().getAddressLine2()+", "+
//                                patientIpd.getPatient().getAddress().getCity()+" ,"
//                                +patientIpd.getPatient().getAddress().getDistrict()+" ,"+
//                                patientIpd.getPatient().getAddress().getPincode()+" ,"+
//                                patientIpd.getPatient().getAddress().getState()+" ,"+
//                                patientIpd.getPatient().getAddress().getCountry(),
//                        patientIpd.getDoctor().getUserEntity().getUserInfo().getFirstName()+" "
//                                +patientIpd.getDoctor().getUserEntity().getUserInfo().getLastName(),
//                        patientIpd.getPatient().getGender().toString(),
//                        patientIpd.getPatient().getDob()
//                ))
//                .collect(Collectors.toList());
//    }

    @Override
    public DeathCertificateDTO updateDeathCertificate(DeathCertificateDTO dto,Long id) {

        DeathCertificate dc = repo.findById(id).orElseThrow(()-> new RuntimeException("Death Record Not Found"));
        dc.setFullName(dto.getFullName());
        dc.setCauseOfDeath(dto.getCauseOfDeath());
        dc.setGender(dto.getGender());
        dc.setHospitalName(dto.getHospitalName());
        dc.setDateOfDeath(dto.getDateOfDeath());
        dc.setTimeOfDeath(dto.getTimeOfDeath());
        dc.setAgeAtDeath(dto.getAgeAtDeath());
        dc.setCauseOfDeath(dto.getCauseOfDeath());
        dc.setPlaceOfDeath(dto.getPlaceOfDeath());
        dc.setAddress(dto.getAddress());
        dc.setAttendingDoctor(dto.getAttendingDoctor());
        dc.setIssueDate(dto.getIssueDate());

        PatientIpd pid = patientIpdRepo.findById(dto.getPatientId()).orElseThrow(()->new RuntimeException("Ipd Patient Not Found"));
        dc.setPatient(pid);

        return mapper.toDTO(repo.save(dc));
    }


}
