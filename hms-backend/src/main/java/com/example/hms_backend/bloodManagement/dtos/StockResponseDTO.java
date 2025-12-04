package com.example.hms_backend.bloodManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockResponseDTO {
    private Long id;
    private String stockId;
    private String bloodGroup;
    private Integer unitsAvailable;
    private LocalDate expiryDate;
}
