package com.example.hms_backend.healthPackage.dto;

import lombok.Data;

@Data
public class HealthPackageResponseDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Double price;
    private String image; // ✅ Base64 or URL string
}
