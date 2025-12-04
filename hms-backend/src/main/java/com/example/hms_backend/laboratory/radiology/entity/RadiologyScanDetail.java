package com.example.hms_backend.laboratory.radiology.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "radiology_scan_details")
public class RadiologyScanDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String scanName;
    private String findings;
    private String impression;
    private Double cost;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] scanFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "radiology_report_id")
    @JsonBackReference // Prevents infinite recursion during serialization
    private RadiologyReport radiologyReport;
}
