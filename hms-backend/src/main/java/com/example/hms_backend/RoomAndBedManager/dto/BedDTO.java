package com.example.hms_backend.RoomAndBedManager.dto;

import com.example.hms_backend.enums.Enums;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BedDTO {

    private Long bedId;


    @NotBlank(message = "Bed number is required")
    @Pattern(regexp = "^[0-9]+$", message = "Bed number must contain only digits")

    private String bedNumber;



    private Long roomId;



    private Enums.BedStatus status;


}
