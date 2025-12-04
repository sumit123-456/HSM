package com.example.hms_backend.hr.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponseDTO {

    // Common User fields
    private Long userId;
    private String username;
    private String email;

    // Roles
    private Object roles; // List<String>

    // UserInfo common fields
    private String firstName;
    private String lastName;
    private String bloodGroup;
    private String joiningDate;
    private String gender;
    private String mobileNo;
    private Integer age;
    private String dob;

    private String address1;
    private String address2;
    private String district;
    private String city;
    private String country;
    private String status;

    // Base64 encoded images
    private String profilePic;
    private String idProofPic;

    // ===== Role-Specific fields =====
    private String specialization;     // doctor
    private String licenseNo;          // doctor
    private String experience;         // all roles
    private String qualification;      // all roles
    private String category;           // lab technician
}
