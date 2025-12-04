package com.example.hms_backend.bloodManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blood_patient_Usage")
public class PatientUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stockId;
    private String bloodGroup;
    private int unitsUsed;
    private String patientName;
    private int patientAge;
    private String patientGender;
    private String patientContact;
    private LocalDateTime usedAt;
}
