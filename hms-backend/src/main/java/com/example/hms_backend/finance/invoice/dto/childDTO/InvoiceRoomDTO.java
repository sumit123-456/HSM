package com.example.hms_backend.finance.invoice.dto.childDTO;

import lombok.Data;

@Data
public class InvoiceRoomDTO {

    private Long roomId;
    private String roomName;
    private Long bedId;
    private int days;
    private Double pricePerDay;
    private Double totalPrice;
}
