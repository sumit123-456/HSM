package com.example.hms_backend.pharmacy.pharmacyPrescription.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionItemDto {
    private Long id;
    private String medicineName;
    private String dosage;
    private String duration;
    private String frequency;
    private String instructions;
}

