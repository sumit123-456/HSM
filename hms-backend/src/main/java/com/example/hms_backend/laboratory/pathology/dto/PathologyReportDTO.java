package com.example.hms_backend.laboratory.pathology.dto;

import com.example.hms_backend.enums.Enums;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PathologyReportDTO {

    private Long id;
    private Long patientId;
    private Long doctorId;
    private String HospitalPatientId;
    private String doctorName;
    private String patientName;
    private String patientEmail;
    private String patientContact;
    private Integer patientAge;
    private String patientGender;
    private String reportStatus;
    private Long labTechnicianId;
    private String labTechnicianName;
    private String sampleType;
    private LocalDate collectedOn;
    private String collectionTime;
    private String remarks;
    private Double totalCost;
    private List<TestResultDTO> testResults;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TestResultDTO {
        private String testName;
        private String resultValue;
        private String units;
        private String referenceRange;
        private Double cost;
    }
}
