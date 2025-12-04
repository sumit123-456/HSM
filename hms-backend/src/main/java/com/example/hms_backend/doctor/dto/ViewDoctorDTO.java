package com.example.hms_backend.doctor.dto;

import lombok.Data;

@Data
public class ViewDoctorDTO {

    private Long doctorId;

    private String firstName;
    private String lastName;

    private String specialization;
    private String qualifications;
    private String experience;
    private String licenseNumber;

    private String departmentName;

    private String email;
    private String mobileNumber;

    private String gender;
    private String dob;
    private Integer age;
    private String joiningDate;

    private String addressLine1;
    private String city;
    private String state;
    private String country;

    private String bloodGroup;

    private byte[] profilePic;
    private byte[] idProofPic;
}
