package com.example.hms_backend.bloodManagement.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BloodDonationRequestDTO {

    private String donorId;

    @NotNull(message = "Donation date is required")
    @PastOrPresent(message = "Donation date cannot be in the future")
    private LocalDate donationDate;

    @NotNull(message = "Units collected is required")
    @Min(value = 1, message = "At least 1 unit must be collected")
    @Max(value = 2, message = "Cannot collect more than 2 units")
    private Integer unitsCollected;

    private String notes;
}
