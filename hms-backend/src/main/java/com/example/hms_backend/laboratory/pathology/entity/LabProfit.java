package com.example.hms_backend.laboratory.pathology.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class LabProfit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long reportId;
    private double totalAmount;
    private LocalDate profitDate;
}
