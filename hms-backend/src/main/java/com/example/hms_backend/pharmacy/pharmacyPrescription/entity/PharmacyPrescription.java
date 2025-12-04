package com.example.hms_backend.pharmacy.pharmacyPrescription.entity;

import com.example.hms_backend.enums.Enums;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "pharmacy_prescription")
@Data
public class PharmacyPrescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long prescriptionId;
    private String doctorName;
    private String patientName;
    private Integer patientAge;
    private String diagnosis;
    private String date;
    private String notes;

    @Enumerated(EnumType.STRING)
    private Enums.PharmacyPrescriptionStatus status;

    @OneToMany(mappedBy = "pharmacyPrescription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescriptionItem> items;
}

