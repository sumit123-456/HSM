package com.example.hms_backend.bloodManagement.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BloodDonationResponceDTO {
    private Long id;
    private String donationId;
    private String donorId;
    private String donorName;
    private String donorPhone;
    private String bloodGroup;
    private LocalDate donationDate;
    private LocalDate expiryDate;
    private Integer unitsCollected;
    private String status;
    private String notes;
    private LocalDateTime createdDate;
    private Boolean isActive;

    // Stock information
    private String stockId;
    private Integer unitsAvailable;
}
