package com.example.hms_backend.DoctorsSchedule.dto;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DayScheduleDTO {
    private String day;

    private LocalTime startTime;

    private LocalTime endTime;
}