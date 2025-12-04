package com.example.hms_backend.DeathCertificate.entity;

import com.example.hms_backend.patient.entity.Patient;
import com.example.hms_backend.patient.entity.PatientIpd;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "death_certificate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeathCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientIpd patient;
}
