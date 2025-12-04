package com.example.hms_backend.AssetManagement.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetResponseDTO {

    private Long id;
    private String assetId;
    private String serialNumber;
    private String model;
    private String vendor;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate warrantyDate;

    private String departmentBranch;
    private String amcCmcDetails;
    private String remarksNotes;
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

}

