package com.example.hms_backend.ambulance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;


    @Data
    public class AmbulanceDTO {

        private Long ambulanceId;

        @NotBlank(message = "Vehicle number is required")
        @Pattern(regexp = "^[A-Z]{2}[0-9]{2}[A-Z]{1,2}[0-9]{4}$",
                message = "Invalid vehicle number format")
        private  String vehicleNumber;

        @NotBlank(message = "Ambulance Type is required")
        private  String ambulanceType;

        @NotBlank(message = "Ambulance status is required")
        private  String ambulanceStatus;

        private LocalDate lastMaintenanceDate;

    }


