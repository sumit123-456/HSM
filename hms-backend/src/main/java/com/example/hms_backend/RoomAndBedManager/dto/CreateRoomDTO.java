package com.example.hms_backend.RoomAndBedManager.dto;

import com.example.hms_backend.enums.Enums;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateRoomDTO {

    // Room fields

    @NotBlank(message = "Bed number is required")
    @Pattern(regexp = "^[0-9]+$", message = "Bed number must contain only digits")
    private String roomNo;

    @NotNull
    @Min(value = 1, message = "Floor number must be greater than 0")

    private Integer floor;

    @NotNull
    private Enums.RoomStatus status;// AVAILABLE, UNAVAILABLE, UNDER_MAINTENANCE

    @NotNull
    private String roomName;

    // RoomType fields
    @NotNull
    private String roomTypeName; // ICU, General Ward, etc.

    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid amount with up to 2 decimals")
    private BigDecimal pricePerDay;
}
