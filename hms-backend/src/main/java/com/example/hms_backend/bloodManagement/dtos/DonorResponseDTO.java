package com.example.hms_backend.bloodManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonorResponseDTO {
    private Long id;
    private String donorId;
    private String donorName;
    private Integer age;
    private String gender;
    private String phone;
    private String email;
    private LocalDate lastDonationDate;
    private String bloodGroup;
    private String address;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Boolean isActive;
}
