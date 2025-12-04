package com.example.hms_backend.laboratory.radiology.dto;

import lombok.Data;

@Data
public class RadiologyScanDto {

    private Long id;
    private Long reportId;    // important for linking
    private String findings;
    private String impression;
    private Double cost;
    private String scanName;
    private byte[] scanFile;
}
