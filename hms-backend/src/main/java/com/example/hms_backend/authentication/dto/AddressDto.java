package com.example.hms_backend.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private Long id; // optional for new address, required for update

    @NotBlank(message = "Address Line 1 is required")
    @Size(max = 100, message = "Address Line 1 must be under 150 characters")
    private String addressLine1;

    @Size(max = 100, message = "Address Line 2 must be under 150 characters")
    private String addressLine2;

    @NotBlank(message = "City is required")
    @Size(max = 60, message = "City name must be under 100 characters")
    private String city;

    @NotBlank(message = "District is required")
    @Size(max = 60, message = "District name must be under 100 characters")
    private String district;

    @NotBlank(message = "State is required")
    @Size(max = 50, message = "State name must be under 50 characters")
    private String state;

    @NotBlank(message = "Pincode is required")
    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Pincode must be a valid 6-digit Indian postal code")
    private String pincode;

    @NotBlank(message = "Country is required")
    private String country;


}