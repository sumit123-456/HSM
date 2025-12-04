package com.example.hms_backend.doctor.mapper;

import com.example.hms_backend.authentication.entity.Address;
import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.authentication.entity.UserInfo;
import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.department.repo.DepartmentRepo;
import com.example.hms_backend.doctor.dto.DoctorDto;
import com.example.hms_backend.doctor.dto.ViewDoctorDTO;
import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.enums.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

//This class is for registration form
@Component
@RequiredArgsConstructor
public class DoctorMapper {

//    private final DepartmentRepo departmentRepo;


    public Doctor toEntity(DoctorDto dto, UserEntity user,Department department) {
        Doctor doctor = new Doctor();


        // Set department from ID
//        Department department = departmentRepo.findById(dto.getDepartmentId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));
        doctor.setDepartment(department);

        // Map remaining fields
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setExperience(dto.getExperience().getLabel()); // Enum to string
        doctor.setQualifications(dto.getQualifications());
        doctor.setLicenseNumber(dto.getLicenseNumber());



        // Link user
        doctor.setUserEntity(user);

        return doctor;
    }


    public ViewDoctorDTO toDTO(Doctor doctor) {

        var user = doctor.getUserEntity();
        var info = user.getUserInfo();
        var address = info.getAddress();
        var dept = doctor.getDepartment();

        ViewDoctorDTO dto = new ViewDoctorDTO();

        dto.setDoctorId(doctor.getId());
        dto.setFirstName(info.getFirstName());
        dto.setLastName(info.getLastName());

        dto.setSpecialization(doctor.getSpecialization());
        dto.setQualifications(doctor.getQualifications());
        dto.setExperience(doctor.getExperience());
        dto.setLicenseNumber(doctor.getLicenseNumber());

        dto.setDepartmentName(dept != null ? dept.getDepartment_name() : null);

        dto.setEmail(user.getEmail());
        dto.setMobileNumber(info.getMobileNumber());

        dto.setGender(info.getGender().name());
        dto.setDob(info.getDob() != null ? info.getDob().toString() : null);
        dto.setAge(info.getAge());
        dto.setJoiningDate(info.getJoiningDate() != null ? info.getJoiningDate().toString() : null);

        if (address != null) {
            dto.setAddressLine1(address.getAddressLine1());
            dto.setCity(address.getCity());
            dto.setState(address.getState());
            dto.setCountry(address.getCountry());
        }

        dto.setBloodGroup(info.getBloodGroup().name());

        dto.setProfilePic(info.getProfilePic());
        dto.setIdProofPic(info.getIdProofPic());

        return dto;
    }

}