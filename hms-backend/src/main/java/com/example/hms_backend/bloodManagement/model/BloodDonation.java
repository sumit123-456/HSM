    package com.example.hms_backend.bloodManagement.model;
    
    import jakarta.persistence.*;
    import jakarta.validation.constraints.*;
    import lombok.*;
    import java.time.LocalDate;
    import java.time.LocalDateTime;
    
    @Entity
    @Table(name = "blood_donations")
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public class BloodDonation {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;
    
        @Column(name = "donation_id", unique = true, nullable = false, updatable = false, length = 20)
        private String donationId; // Format: DNT2412150001
    
        // Many donations can come from one donor
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "donor_id", nullable = false)
        private BloodDonor donor;
    
        // One donation creates one blood stock entry
        @OneToOne(mappedBy = "donation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private BloodStock bloodStock;
    
        @NotNull(message = "Donation date is mandatory")
        @PastOrPresent(message = "Donation date cannot be in the future")
        @Column(name = "donation_date", nullable = false)
        private LocalDate donationDate;
    
        @NotNull(message = "Expiry date is mandatory")
        @Future(message = "Expiry date must be in the future")
        @Column(name = "expiry_date", nullable = false)
        private LocalDate expiryDate;
    
        @NotNull(message = "Units collected is mandatory")
        @Min(value = 1, message = "At least 1 unit must be collected")
        @Max(value = 2, message = "Cannot collect more than 2 units in one donation")
        @Column(name = "units_collected", nullable = false)
        private Integer unitsCollected;
    
        @NotBlank(message = "Blood group is mandatory")
        @Pattern(regexp = "^(A\\+|A-|B\\+|B-|AB\\+|AB-|O\\+|O-)$",
                message = "Blood group must be one of: A+, A-, B+, B-, AB+, AB-, O+, O-")
        @Column(name = "blood_group", nullable = false, length = 5)
        private String bloodGroup;
    
        @Column(name = "donor_name", nullable = false, length = 100)
        private String donorName; // Denormalized for performance
    
        @Column(name = "donor_phone", length = 10)
        private String donorPhone; // Denormalized for quick reference

        @Column(name = "status")
        @Builder.Default
        private String status = "COLLECTED"; // COLLECTED, PROCESSED, STORED, USED, DISCARDED
    
        @Column(name = "notes")
        private String notes;
    
        @Column(name = "created_date", nullable = false, updatable = false)
        private LocalDateTime createdDate;
    
        @Column(name = "updated_date", nullable = false)
        private LocalDateTime updatedDate;
    
        @Column(name = "is_active", nullable = false)
        @Builder.Default
        private Boolean isActive = true;

        // Default constructor
        public BloodDonation() {
            this.isActive = true;
            this.createdDate = LocalDateTime.now();
            this.updatedDate = LocalDateTime.now();
            this.status = "COLLECTED";
            this.unitsCollected = 1;
        }
    
        @PrePersist
        public void prePersist() {
            if (this.createdDate == null) {
                this.createdDate = LocalDateTime.now();
            }
            if (this.updatedDate == null) {
                this.updatedDate = LocalDateTime.now();
            }
            if (this.isActive == null) {
                this.isActive = true;
            }
            if (this.status == null) {
                this.status = "COLLECTED";
            }
            if (this.unitsCollected == null) {
                this.unitsCollected = 1;
            }
    
            // Auto-populate denormalized fields from donor
            if (this.donor != null) {
                if (this.donorName == null) {
                    this.donorName = this.donor.getDonorName();
                }
                if (this.donorPhone == null) {
                    this.donorPhone = this.donor.getPhone();
                }
                if (this.bloodGroup == null) {
                    this.bloodGroup = this.donor.getBloodGroup();
                }
            }
        }
    
        @PreUpdate
        public void preUpdate() {
            this.updatedDate = LocalDateTime.now();
        }
    
        // Helper method to check if donation is expired
        public boolean isExpired() {
            return LocalDate.now().isAfter(this.expiryDate);
        }
    
        // Helper method to check if donation can be used
        public boolean canBeUsed() {
            return isActive && !isExpired() && "STORED".equals(status);
        }
    }
