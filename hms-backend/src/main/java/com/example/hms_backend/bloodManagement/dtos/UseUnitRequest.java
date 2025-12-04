package com.example.hms_backend.bloodManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UseUnitRequest {
    private int unitsUsed;
    private String patientName;
    private int patientAge;
    private String patientGender;
    private String patientContact;
}
