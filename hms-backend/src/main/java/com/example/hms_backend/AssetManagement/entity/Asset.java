package com.example.hms_backend.AssetManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "assets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "asset_id", unique = true, nullable = false)
    @NotBlank(message = "Asset ID is required")
    private String assetId;

    @Column(name = "serial_number", unique = true, nullable = false)
    @NotBlank(message = "Serial Number is required")
    private String serialNumber;

    @Column(nullable = false)
    @NotBlank(message = "Model is required")
    private String model;

    @Column(nullable = false)
    @NotBlank(message = "Vendor is required")
    private String vendor;

    @Column(name = "purchase_date", nullable = false)
    @NotNull(message = "Purchase Date is required")
    private LocalDate purchaseDate;

    @Column(name = "warranty_date")
    private LocalDate warrantyDate;

    @Column(name = "department_branch")
    private String departmentBranch;

    @Column(name = "amc_cmc_details", columnDefinition = "TEXT")
    private String amcCmcDetails;

    @Column(name = "remarks_notes", columnDefinition = "TEXT")
    private String remarksNotes;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private AssetStatus status = AssetStatus.ACTIVE;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum AssetStatus {
        ACTIVE, INACTIVE, MAINTENANCE, RETIRED
    }

}
