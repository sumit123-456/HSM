package com.example.hms_backend.DoctorsSchedule.entity;


import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalTime;

//this is embedded class for DoctorsSchedule
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DaySchedule {

    private String day;        // e.g. "Mon", "Tue", ...
    private LocalTime startTime;
    private LocalTime endTime;
}
