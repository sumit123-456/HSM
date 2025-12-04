package com.example.hms_backend.patient.service;

import com.example.hms_backend.DeathCertificate.repo.DeathCertificateRepo;
import com.example.hms_backend.authentication.entity.Address;
import com.example.hms_backend.authentication.repo.AddressRepo;
import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.department.repo.DepartmentRepo;
import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.doctor.repo.DoctorRepo;
import com.example.hms_backend.enums.Enums;
import com.example.hms_backend.patient.dto.*;
import com.example.hms_backend.patient.entity.Patient;
import com.example.hms_backend.patient.entity.PatientIpd;
import com.example.hms_backend.patient.mapper.PatientIpdMapper;
import com.example.hms_backend.patient.mapper.PatientMapper;
import com.example.hms_backend.patient.mapper.PatientOpdMapper;
import com.example.hms_backend.patient.repo.PatientEmergencyRepo;
import com.example.hms_backend.patient.repo.PatientIpdRepo;
import com.example.hms_backend.patient.repo.PatientRepo;
import com.example.hms_backend.registration.mapper.RegistrationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PatientIpdServiceImpl implements PatientIpdService {

    @Autowired
    PatientRepo  patientRepo;

    @Autowired
    DepartmentRepo departmentRepo;

    @Autowired
    DoctorRepo doctorRepo;

    @Autowired
    PatientIpdRepo  patientIpdRepo;

    @Autowired
    PatientEmergencyRepo patientEmergencyRepo;

    @Autowired
    private RegistrationMapper registrationMapper;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private PatientIpdMapper patientIpdMapper;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private AdtRecordService adtRecordService;

    @Autowired
    DeathCertificateRepo deathCertificateRepo;


    @Override
    public Long createIpdAdmission(PatientIpdFormDto formDto) {

        PatientDto patientDto = formDto.getPatient();
        PatientIpdDTO ipdDto = formDto.getIpd();

        // 🔹 Step 1: Find existing patient
        Patient patient = patientRepo.findByPatientHospitalId(patientDto.getPatientHospitalId())
                .orElseGet(() -> {
                    Address address = registrationMapper.toAddress(patientDto.getAddress());
                    address = addressRepo.save(address);

                    Patient newPatient = patientMapper.toEntity(patientDto, address);

                    newPatient.setPatientHospitalId(null);
                    newPatient = patientRepo.save(newPatient);

                    patientMapper.assignHospitalId(newPatient);
                    return patientRepo.save(newPatient);
                });

        // 🔹 Step 2: Check for active IPD admission
        boolean hasActiveAdmission = patientIpdRepo.existsByPatientIdAndStatus(patient.getId(), Enums.IpdStatus.ADMITTED);
        if (hasActiveAdmission) {
            throw new RuntimeException("Patient already has an active IPD admission. Discharge before new admission.");
        }

        // 🔹 Step 3: Resolve department and doctor
        Department dept = departmentRepo.findById(ipdDto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        Doctor doctor = doctorRepo.findById(ipdDto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // 🔹 Step 4: Parent IPD admission if present
        PatientIpd parentVisit = null;
        if (ipdDto.getParentVisitId() != null) {
            parentVisit = patientIpdRepo.findById(ipdDto.getParentVisitId())
                    .orElseThrow(() -> new RuntimeException("Parent IPD visit not found"));
        }

        // 🔹 Step 5: Create IPD admission
        PatientIpd ipd = patientIpdMapper.toEntity(ipdDto, patient, dept, doctor, parentVisit);
        ipd.setStatus(Enums.IpdStatus.ADMITTED); // ensure status is set
        ipd = patientIpdRepo.save(ipd);

        // 🔹 Step 6: Log ADT record
        AdtRecordDTO adtDto = formDto.getAdt();
        adtDto.setPatientId(patient.getId());
        adtRecordService.createAdtRecord(adtDto, patient);

        return ipd.getId();
    }

    @Override
    public PatientIpdDTO dischargeIpd(Long ipdId) {
        PatientIpd ipd = patientIpdRepo.findById(ipdId)
                .orElseThrow(() -> new RuntimeException("IPD record not found"));
        ipd.setStatus(Enums.IpdStatus.DISCHARGED);
        return patientIpdMapper.toDto(patientIpdRepo.save(ipd));

    }

    @Override
    public List<PatientIpdDTO> getIpdHistory(Long patientId) {
        List<PatientIpd> admissions = patientIpdRepo.findTopByPatientId(patientId);
        return admissions.stream()
                .map(patientIpdMapper::toDto)
                .collect(Collectors.toList());


    }

    //get all patient ipd who are alive
    public List<PatientIpdViewDTO> getAllAliveIpdAndErPatients() {

        // Step 1: Get dead patient IDs
        List<Long> deadPatientIds = deathCertificateRepo.findAllDeadPatientIds();

        List<PatientIpdViewDTO> pid = new ArrayList<>();

        // Step 2: Filter IPD patients whose ID is NOT in deadPatientIds
        pid = patientIpdRepo.findAll().stream()
                .filter(ipd -> !deadPatientIds.contains(ipd.getId())) // alive only
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


        pid = patientEmergencyRepo.findAll().stream()
                .filter(er -> !deadPatientIds.contains(er.getId())) // alive only
                .map(patientEr -> new PatientIpdViewDTO(
                        patientEr.getId(),
                        patientEr.getPatient().getFirstName() + " " + patientEr.getPatient().getLastName(),
                        patientEr.getPatient().getPatientHospitalId(),

                        patientEr.getPatient().getContactInfo(),

                        patientEr.getPatient().getAddress().getAddressLine1()+", "+
                                patientEr.getPatient().getAddress().getAddressLine2()+", "+
                                patientEr.getPatient().getAddress().getCity()+" ,"
                                +patientEr.getPatient().getAddress().getDistrict()+" ,"+
                                patientEr.getPatient().getAddress().getPincode()+" ,"+
                                patientEr.getPatient().getAddress().getState()+" ,"+
                                patientEr.getPatient().getAddress().getCountry(),
                        patientEr.getDoctor().getUserEntity().getUserInfo().getFirstName()+" "
                                +patientEr.getDoctor().getUserEntity().getUserInfo().getLastName(),
                        patientEr.getPatient().getGender().toString(),
                        patientEr.getPatient().getDob()
                ))
                .collect(Collectors.toList());

        return pid;
    }

}


