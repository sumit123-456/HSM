package com.example.hms_backend.birthCertificate.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BabyBirthRecordDTO {

    private Long id;

    // Baby Information

    private String gender;
    private LocalDate dateOfBirth;
    private LocalTime timeOfBirth;
    private Double birthWeight;
    private Double birthLength;



    private String placeOfBirth;

    private String attendingDoctor;

    private LocalDate timeOfIssue;

    private String certificateNumber;


    // Parent Information
    private String motherName;
    private String fatherName;
    private String contactNumber;
    private String address;

    // Mother Patient ID (Required to establish relationship)
    private Long motherPatientId;
}
