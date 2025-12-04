package com.example.hms_backend.prescription.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PrescriptionDTO {
    private Long id;
    private Long patientId;
    private String patientName;
    private Long doctorId;
    private String doctorName;
    private Long departmentId;
    private String departmentName;
    private String diagnosis;
    private String symptoms;
    private String additionalNotes;
    private LocalDate prescriptionDate;
    private String status;
    private List<PrescriptionMedicineDTO> medicines;

}
