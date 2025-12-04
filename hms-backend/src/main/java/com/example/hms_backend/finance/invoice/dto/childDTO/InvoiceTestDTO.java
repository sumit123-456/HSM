package com.example.hms_backend.finance.invoice.dto.childDTO;

import lombok.Data;

@Data
public class InvoiceTestDTO {

    private Long testId;
    private String testName;
    private Double price;
    private Integer quantity;
    private Double totalPrice;

}
