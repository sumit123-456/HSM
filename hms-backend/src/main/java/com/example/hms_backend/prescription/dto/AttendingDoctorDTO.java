package com.example.hms_backend.prescription.dto;

import lombok.Data;

@Data
public class AttendingDoctorDTO {

    private Long doctorId;
    private String doctorName;
    private Long DepartmentId;
    private String departmentName;
}
