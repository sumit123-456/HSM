package com.example.hms_backend.finance.invoice.dto;

import com.example.hms_backend.finance.invoice.dto.childDTO.InvoiceDoctorDTO;
import com.example.hms_backend.finance.invoice.dto.childDTO.InvoiceMedicineDTO;
import com.example.hms_backend.finance.invoice.dto.childDTO.InvoiceRoomDTO;
import com.example.hms_backend.finance.invoice.dto.childDTO.InvoiceTestDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class InvoiceDTO {

    private Long id;

    private Long patientId;
    private String hospitalPatientId;
    private String patientName;
    private Integer patientAge;
    private String patientContact;


    private LocalDate admissionDate;
    private LocalDate dischargeDate;

    private Double totalAmount;
    private String paymentMethod;
    private String paymentStatus;

    private List<InvoiceDoctorDTO> doctors = new ArrayList<>();
    private List<InvoiceMedicineDTO> medicines = new ArrayList<>();
    private List<InvoiceRoomDTO> rooms = new ArrayList<>();
    private List<InvoiceTestDTO> tests = new ArrayList<>();
}
