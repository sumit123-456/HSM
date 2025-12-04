package com.example.hms_backend.bloodManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "blood_donors")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class BloodDonor{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "donor_id", unique = true, nullable = false, updatable = false, length = 15)
    private String donorId; // Format: DON2412150001

    @NotBlank(message = "Donor name is mandatory")
    @Size(min = 2, max = 100, message = "Donor name must be between 2 and 100 characters")
    @Column(name = "donor_name", nullable = false, length = 100)
    private String donorName;

    @NotNull(message = "Age is mandatory")
    @Min(value = 18, message = "Donor must be at least 18 years old")
    @Max(value = 65, message = "Donor must be at most 65 years old")
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotBlank(message = "Gender is mandatory")
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
    @Column(name = "gender", nullable = false, length = 10)
    private String gender;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @Column(name = "phone", nullable = false, unique = true, length = 10)
    private String phone;

    @Email(message = "Email should be valid")
    @Column(name = "email", length = 100)
    private String email;

    @NotNull(message = "Last donation date is mandatory")
    @PastOrPresent(message = "Last donation date cannot be in the future")
    @Column(name = "last_donation_date", nullable = false)
    private LocalDate lastDonationDate;

    @NotBlank(message = "Blood group is mandatory")
    @Pattern(regexp = "^(A\\+|A-|B\\+|B-|AB\\+|AB-|O\\+|O-)$",
            message = "Blood group must be one of: A+, A-, B+, B-, AB+, AB-, O+, O-")
    @Column(name = "blood_group", nullable = false, length = 5)
    private String bloodGroup;

    @NotBlank(message = "Address is mandatory")
    @Size(min = 10, max = 500, message = "Address must be between 10 and 500 characters")
    @Column(name = "address", nullable = false, length = 500)
    private String address;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    //One donor can have many donations
    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<BloodDonation> donations = new ArrayList<>();

    // Default constructor
    public BloodDonor() {
        this.isActive = true;
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

}
