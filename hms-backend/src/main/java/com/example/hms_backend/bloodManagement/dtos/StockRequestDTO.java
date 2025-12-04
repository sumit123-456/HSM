package com.example.hms_backend.bloodManagement.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockRequestDTO {

    @NotBlank(message = "Blood group is mandatory")
    @Pattern(regexp = "^(A\\+|A-|B\\+|B-|AB\\+|AB-|O\\+|O-)$",
            message = "Blood group must be one of: A+, A-, B+, B-, AB+, AB-, O+, O-")
    private String bloodGroup;

    @NotNull(message = "Units available is mandatory")
    @Min(value = 0, message = "Units available cannot be negative")
    @Max(value = 1000, message = "Units available cannot exceed 1000")
    private Integer unitsAvailable;

    @NotNull(message = "Expiry date is mandatory")
    @Future(message = "Expiry date must be in the future")
    private LocalDate expiryDate;
}
