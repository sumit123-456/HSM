package com.example.hms_backend.hr.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class EmployeeUpdateDTO {



    private String firstName;
    private String lastName;
    private String gender;
    private String bloodGroup;
    private String joiningDate;
    private String dob;
    private Integer age;
    private String mobileNo;


//    private MultipartFile idProofPic;  // base64 string

    private String address1;
    private String address2;
    private String district;
    private String city;
    private String country;
    private String status;

    // Role-specific
    private String specialization;
    private String licenseNo;
    private String experience;
    private String qualification;
    private String category;
}
