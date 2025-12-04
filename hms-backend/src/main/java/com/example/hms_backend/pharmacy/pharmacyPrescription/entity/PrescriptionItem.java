package com.example.hms_backend.pharmacy.pharmacyPrescription.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "prescription_item")
@Data
public class PrescriptionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String medicineName;
    private String dosage;
    private String duration;
    private String frequency;
    private String instructions;

    @ManyToOne
    @JoinColumn(name = "prescription_id", nullable = false)
    private PharmacyPrescription pharmacyPrescription;
}

