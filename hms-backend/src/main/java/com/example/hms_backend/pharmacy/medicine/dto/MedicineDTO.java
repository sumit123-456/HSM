package com.example.hms_backend.pharmacy.medicine.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicineDTO {

    private Long medicineId;
    private String medicineName;
    private String manufacturer;
    private String batchNumber;
    private String expiryDate;
    private Double price;
}
