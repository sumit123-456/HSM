package com.example.hms_backend.DeathCertificate.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeathCertificateDTO {

    private Long id;

    private String hospitalName;
    private String certificateNumber;
    private String fullName;
    private String gender;

    private LocalDate dateOfDeath;
    private LocalTime timeOfDeath;
    private LocalDate dateOfBirth;

    private Integer ageAtDeath;
    private String causeOfDeath;
    private String placeOfDeath;
    private String address;
    private String contactNumber;

    private String attendingDoctor;

    private LocalDate issueDate;

    private Long patientId; // For linking with Patient
}