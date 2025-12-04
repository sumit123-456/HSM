package com.example.hms_backend.bloodManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "blood_stock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloodStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "stock_id", unique = true, nullable = false, updatable = false, length = 20)
    private String stockId; // Format: STK-1001

    @NotBlank(message = "Blood group is mandatory")
    @Pattern(regexp = "^(A\\+|A-|B\\+|B-|AB\\+|AB-|O\\+|O-)$",
            message = "Blood group must be one of: A+, A-, B+, B-, AB+, AB-, O+, O-")
    @Column(name = "blood_group", nullable = false, length = 5)
    private String bloodGroup;

    @NotNull(message = "Units available is mandatory")
    @Min(value = 0, message = "Units available cannot be negative")
    @Max(value = 1000, message = "Units available cannot exceed 1000")
    @Column(name = "units_available", nullable = false)
    private Integer unitsAvailable;

    @NotNull(message = "Expiry date is mandatory")
    @Future(message = "Expiry date must be in the future")
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Builder.Default
    private Boolean isActive = true;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donation_id")
    private BloodDonation donation;

    // Helper methods
    public boolean isExpired() {
        return LocalDate.now().isAfter(this.expiryDate);
    }

    public boolean canBeUsed() {
        return isActive && !isExpired() && unitsAvailable > 0;
    }
}
