package com.example.hms_backend.prescription.dto;

import lombok.Data;

@Data
public class PrescriptionMedicineDTO {
    private Long id;
    private String medicineName;
    private Long medicineId;
    private String frequency;
    private String duration;
}
