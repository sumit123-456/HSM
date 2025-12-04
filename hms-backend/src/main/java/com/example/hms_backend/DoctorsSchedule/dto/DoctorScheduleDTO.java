package com.example.hms_backend.DoctorsSchedule.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorScheduleDTO {

    private Long id;
    private Long departmentId;
    private String departmentName;

    private String doctorName;

    private Long doctorId;

    private Double appointmentFees;

    private List<DayScheduleDTO> weeklySchedule;

    private String status;
}

