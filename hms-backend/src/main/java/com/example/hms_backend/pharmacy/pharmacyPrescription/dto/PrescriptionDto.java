package com.example.hms_backend.pharmacy.pharmacyPrescription.dto;

import lombok.Data;

import java.util.List;

@Data
public class PrescriptionDto {
    private Long id;
    private Long prescriptionId;
    private String doctorName;
    private String patientName;
    private Integer patientAge;
    private String diagnosis;
    private String date;
    private String notes;
    private String status;
    private List<PrescriptionItemDto> items;
}


