package com.example.hms_backend.doctor.dto;

import com.example.hms_backend.enums.Enums;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {

    private Long id;

    @NotNull(message = "Department name is required")
    private Long departmentId;


    private String departmentName; // Optional, for display

    @NotBlank(message = "Specialization must not be blank")
    @Size(max = 100, message = "Specialization must be under 100 characters")
    private String specialization;

    @NotNull(message = "Experience is required")
    private Enums.ExperienceLevel experience;

    @NotBlank(message = "Qualifications must not be blank")
    @Size(max = 150, message = "Qualifications must be under 150 characters")
    private String qualifications;

    @NotBlank(message = "License number is required")
    @Size(max = 50, message = "License number must be under 50 characters")
    private String licenseNumber;





}

