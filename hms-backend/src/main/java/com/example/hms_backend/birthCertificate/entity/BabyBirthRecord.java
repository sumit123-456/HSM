package com.example.hms_backend.birthCertificate.entity;

import com.example.hms_backend.patient.entity.PatientIpd;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "baby_birth_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BabyBirthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // -------- Baby Information --------


    @NotBlank(message = "Gender is required")
    private String gender;

    @NotNull(message = "Date of Birth is required")
    private LocalDate dateOfBirth;

    private LocalTime timeOfBirth;

    private String placeOfBirth;

    private String attendingDoctor;

    private LocalDate timeOfIssue;

    private String certificateNumber;

    @NotNull(message = "Birth Weight is required")
    @DecimalMin(value = "0.1", message = "Birth Weight must be positive")
    private Double birthWeight;

    @Positive(message = "Birth Length must be positive")
    private Double birthLength;



    // -------- Parent / Guardian Information --------
    @NotBlank(message = "Mother's Name is required")
    private String motherName;

    @NotBlank(message = "Father's Name is required")
    private String fatherName;

    @NotBlank(message = "Contact Number is required")
    private String contactNumber;

    private String address;


    // Relation to Patient (Mother must be admitted or registered)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mother_patient_id", nullable = false)
    private PatientIpd motherPatient;

    public void setPatientIpd(PatientIpd mother) {
        this.motherPatient = mother;
    }
}
