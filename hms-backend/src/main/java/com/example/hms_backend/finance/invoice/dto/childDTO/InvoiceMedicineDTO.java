package com.example.hms_backend.finance.invoice.dto.childDTO;

import lombok.Data;

@Data
public class InvoiceMedicineDTO {

    private Long medicineId;
    private String medicineName;
    private int qty;
    private Double pricePerUnit;
    private Double totalPrice;
}
