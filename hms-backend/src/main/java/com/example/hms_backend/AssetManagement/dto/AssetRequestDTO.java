package com.example.hms_backend.AssetManagement.dto;

import com.example.hms_backend.AssetManagement.entity.Asset;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetRequestDTO {

    @NotBlank(message = "Asset ID is required")
    private String assetId;

    @NotBlank(message = "Serial Number is required")
    private String serialNumber;

    @NotBlank(message = "Model is required")
    private String model;

    @NotBlank(message = "Vendor is required")
    private String vendor;

    @NotNull(message = "Purchase Date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate warrantyDate;

    private String departmentBranch;
    private String amcCmcDetails;
    private String remarksNotes;
    private Asset.AssetStatus status;
}

