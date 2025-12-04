package com.example.hms_backend.finance.invoice.dto.childDTO;

import lombok.Data;

@Data
public class InvoiceDoctorDTO {

    private Long doctorId;
    private  String doctorName;
    private Double fee;
}
