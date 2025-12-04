package com.example.hms_backend.patient.mapper;

import com.example.hms_backend.authentication.dto.AddressViewDto;
import com.example.hms_backend.authentication.entity.Address;
import com.example.hms_backend.patient.dto.PatientDto;
import com.example.hms_backend.patient.dto.PatientViewDto;
import com.example.hms_backend.patient.entity.Patient;
import com.example.hms_backend.registration.mapper.RegistrationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {
    @Autowired
    private RegistrationMapper registrationMapper;

    public Patient toEntity(PatientDto dto, Address savedAddress) {
        Patient patient = new Patient();
        patient.setId(dto.getId());
        patient.setAddress(savedAddress); // use persisted address

        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setEmail(dto.getEmail());
        patient.setDob(dto.getDob());
        patient.setAge(dto.getAge());
        patient.setGender(dto.getGender());
        patient.setOccupation(dto.getOccupation());
        patient.setBloodGroup(dto.getBloodGroup());
        patient.setContactInfo(dto.getContactInfo());
        patient.setEmergencyContact(dto.getEmergencyContact());
        patient.setIdProofType(dto.getIdProofType());
        patient.setIdProofFile(dto.getIdProofFile());
        patient.setMaritalStatus(dto.getMaritalStatus());
//        patient.setVisitType(dto.getVisitType());
        patient.setNote(dto.getNote());
        return patient;
    }


    public void assignHospitalId(Patient patient) {
        String hospitalId = "HM" + patient.getId();
        patient.setPatientHospitalId(hospitalId);
    }


    public PatientViewDto toDTO(Patient patient) {
        Address address = patient.getAddress();

        AddressViewDto addressDto = (address != null) ? new AddressViewDto(
                address.getId(),
                address.getState(),
                address.getCity(),
                address.getAddressLine1(),
                address.getPincode()
        ) : null;

        return new PatientViewDto(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getEmail(),
                patient.getDob().toString(),
                patient.getAge(),
                patient.getGender().toString(),
                patient.getOccupation(),
                patient.getBloodGroup().toString(),
                patient.getContactInfo(),
                patient.getEmergencyContact(),
                patient.getMaritalStatus().toString(),
//                patient.getVisitType().toString(),
                patient.getNote(),
                patient.getPatientHospitalId(),
                addressDto
        );


    }
}
