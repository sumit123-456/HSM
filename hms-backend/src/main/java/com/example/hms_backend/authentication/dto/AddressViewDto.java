package com.example.hms_backend.authentication.dto;

public record AddressViewDto(
        Long id,
        String state,
        String city,
        String addressLine,
        String pincode
) {}
