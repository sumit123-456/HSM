package com.example.hms_backend.appointment.dto;

import com.example.hms_backend.appointment.entity.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {

    private Long id;
    private Long patientId;
    private Long doctorId;
    private Long departmentId;

    private String doctorName;
    private String patientName;
    private Integer patientAge;
    private String patientContact;
    private String patientHospitalId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime appointmentTime;


    private AppointmentStatus status;

    private String symptoms;
}
