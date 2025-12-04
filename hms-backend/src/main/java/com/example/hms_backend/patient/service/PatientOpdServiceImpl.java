package com.example.hms_backend.patient.service;

import com.example.hms_backend.authentication.entity.Address;
import com.example.hms_backend.authentication.repo.AddressRepo;
import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.department.repo.DepartmentRepo;
import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.doctor.repo.DoctorRepo;
import com.example.hms_backend.enums.Enums;
import com.example.hms_backend.patient.dto.PatientDto;
import com.example.hms_backend.patient.dto.PatientOpdDto;
import com.example.hms_backend.patient.dto.PatientOpdFormDto;
import com.example.hms_backend.patient.entity.Patient;
import com.example.hms_backend.patient.entity.PatientOpd;
import com.example.hms_backend.patient.mapper.PatientMapper;
import com.example.hms_backend.patient.mapper.PatientOpdMapper;
import com.example.hms_backend.patient.repo.PatientOpdRepo;
import com.example.hms_backend.patient.repo.PatientRepo;
import com.example.hms_backend.registration.mapper.RegistrationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientOpdServiceImpl implements PatientOpdService{

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private PatientOpdRepo patientOpdRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private PatientOpdMapper patientOpdMapper;

    @Autowired
    private RegistrationMapper registrationMapper;



    @Override
    @Transactional
    public Long createOpdVisit(PatientOpdFormDto formDto) {
        PatientDto patientDto = formDto.getPatient();
        PatientOpdDto opdDto = formDto.getOpd();

        // 🔹 Step 1: Find existing patient (by hospital ID or mobile number)
        Patient patient = null;
        if (patientDto.getPatientHospitalId() != null) {
            patient = patientRepo.findByPatientHospitalId(patientDto.getPatientHospitalId())
                    .orElse(null);
        }


        // 🔹 Step 2: If patient not found, create new
        if (patient == null) {
            Address address = registrationMapper.toAddress(patientDto.getAddress());
            address = addressRepo.save(address);

            patient = patientMapper.toEntity(patientDto, address);
            patient.setPatientHospitalId(null); // will assign later
            patient = patientRepo.save(patient);

            patientMapper.assignHospitalId(patient);
            patient = patientRepo.save(patient);
        }

        // 🔹 Step 3: Resolve department and doctor
        Department dept = departmentRepo.findById(opdDto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        Doctor doctor = doctorRepo.findById(opdDto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // 🔹 Step 4: Resolve parent visit if present
        PatientOpd parentVisit = null;
        if (opdDto.getParentVisitId() != null) {
            parentVisit = patientOpdRepo.findById(opdDto.getParentVisitId())
                    .orElseThrow(() -> new RuntimeException("Parent visit not found"));
        }

        // 🔹 Step 5: Resolve referral info if present
        Department referredDept = null;
        if (opdDto.getReferredToDepartmentId() != null) {
            referredDept = departmentRepo.findById(opdDto.getReferredToDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Referred department not found"));
        }

        Doctor referredDoc = null;
        if (opdDto.getReferredToDoctorId() != null) {
            referredDoc = doctorRepo.findById(opdDto.getReferredToDoctorId())
                    .orElseThrow(() -> new RuntimeException("Referred doctor not found"));
        }

        // 🔹 Step 6: Map and save OPD visit
        PatientOpd opd = patientOpdMapper.toEntity(opdDto, patient, dept, doctor, parentVisit, referredDept, referredDoc);
        opd = patientOpdRepo.save(opd);

        return opd.getId();
    }
}
