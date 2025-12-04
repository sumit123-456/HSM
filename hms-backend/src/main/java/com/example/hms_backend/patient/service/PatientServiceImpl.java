package com.example.hms_backend.patient.service;

import com.example.hms_backend.authentication.dto.AddressDto;
import com.example.hms_backend.enums.Enums;
import com.example.hms_backend.patient.dto.PatientDto;
import com.example.hms_backend.patient.dto.PatientNameAndIdViewDTO;
import com.example.hms_backend.patient.dto.PatientViewDto;
import com.example.hms_backend.patient.entity.Patient;
import com.example.hms_backend.patient.entity.PatientEmergency;
import com.example.hms_backend.patient.entity.PatientIpd;
import com.example.hms_backend.patient.mapper.PatientMapper;
import com.example.hms_backend.patient.repo.PatientEmergencyRepo;
import com.example.hms_backend.patient.repo.PatientIpdRepo;
import com.example.hms_backend.patient.repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientRepo patientRepo;

   @Autowired
    PatientMapper patientMapper;

   @Autowired
    PatientIpdRepo  patientIpdRepo;

   @Autowired
    PatientEmergencyRepo patientEmergencyRepo;

    @Override
    public List<PatientViewDto> getAllPatients() {
        return patientRepo.findAll()
                .stream()
                .map(patient -> patientMapper.toDTO(patient))
                .collect(Collectors.toList());
    }

    //method for returning patient full name by id
    @Override
    public String getPatientFullNameById(Long patientId)
    {
        Patient patient = patientRepo.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));
        return patient.getFirstName() + " " + patient.getLastName();
    }

    @Override
    public List<PatientNameAndIdViewDTO> getAllPatientsNameAndId() {
        return patientRepo.findAll()
                .stream()
                .map(patient -> new PatientNameAndIdViewDTO(patient.getId(), patient.getFirstName()+" "+patient.getLastName(), patient.getPatientHospitalId(),patient.getAge(),patient.getGender().toString()))
                .collect(Collectors.toList());
    }

    //return all IPD and EMERGENCY Patient
    @Override
    public List<PatientDto> getAllIpdAndErPatients() {

        List<PatientIpd> patientIpds = patientIpdRepo.findAll();

        List<PatientEmergency> patientEmergencies = patientEmergencyRepo.findAll();

        List<PatientDto> patients = new ArrayList<>();

        for (PatientIpd ipd : patientIpds) {
            Patient p = ipd.getPatient();
            PatientDto pp = new PatientDto();
            pp.setId(p.getId());
            pp.setFirstName(p.getFirstName());
            pp.setLastName(p.getLastName());
            pp.setPatientHospitalId(p.getPatientHospitalId());
            pp.setAge(p.getAge());
            pp.setGender(p.getGender());
            pp.setContactInfo(p.getContactInfo());
            AddressDto ad = new AddressDto();
            ad.setAddressLine1(p.getAddress().getAddressLine1());
            ad.setAddressLine2(p.getAddress().getAddressLine2());
            ad.setCity(p.getAddress().getCity());
            ad.setState(p.getAddress().getState());
            pp.setAddress(ad);
            pp.setDob(p.getDob());
            pp.setEmail(p.getEmail());
            patients.add(pp);
        }

        for (PatientEmergency er : patientEmergencies) {
            Patient p = er.getPatient();
            PatientDto pp = new PatientDto();
            pp.setId(p.getId());
            pp.setFirstName(p.getFirstName());
            pp.setLastName(p.getLastName());
            pp.setPatientHospitalId(p.getPatientHospitalId());
            pp.setAge(p.getAge());
            pp.setGender(p.getGender());
            pp.setContactInfo(p.getContactInfo());
            AddressDto ad = new AddressDto();
            ad.setAddressLine1(p.getAddress().getAddressLine1());
            ad.setAddressLine2(p.getAddress().getAddressLine2());
            ad.setCity(p.getAddress().getCity());
            ad.setState(p.getAddress().getState());
            pp.setAddress(ad);
            pp.setDob(p.getDob());
            pp.setEmail(p.getEmail());
            patients.add(pp);
        }

        return patients;


//        return patientRepo.findAll().stream().filter
//                (p->p.getVisitType()== Enums.VisitType.EMERGENCY||p.getVisitType()== Enums.VisitType.IPD)
//                .map(p->
//                {PatientDto pp=new PatientDto();
//                pp.setId(p.getId());
//                pp.setFirstName(p.getFirstName());
//                pp.setLastName(p.getLastName());
//                pp.setPatientHospitalId(p.getPatientHospitalId());
//                pp.setAge(p.getAge());
//                pp.setGender(p.getGender());
//                pp.setContactInfo(p.getContactInfo());
//                    AddressDto ad = new  AddressDto();
//                    ad.setAddressLine1(p.getAddress().getAddressLine1());
//                    ad.setAddressLine2(p.getAddress().getAddressLine2());
//                    ad.setCity(p.getAddress().getCity());
//                    ad.setState(p.getAddress().getState());
//                pp.setAddress(ad);
//                pp.setDob(p.getDob());
//                pp.setEmail(p.getEmail());
//                return pp;
//                }).collect(Collectors.toList());
    }
}
