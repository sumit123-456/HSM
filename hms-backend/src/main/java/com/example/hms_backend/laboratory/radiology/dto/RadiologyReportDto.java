package com.example.hms_backend.laboratory.radiology.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RadiologyReportDto {

    private Long id;
    private Long patientId;
    private Long doctorId;
    private Long radiologyPerformedById;
    private String hospitalPatientId;
    private String doctorName;
    private String patientName;
    private String patientEmail;
    private String patientContact;
    private Integer patientAge;
    private String patientGender;
    private String radiologyTechnicianName;
    private String finalSummary;
    private Double totalCost;

    private String reportStatus;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reportDate;
    private List<RadiologyScanDto> scanDetails;
}
