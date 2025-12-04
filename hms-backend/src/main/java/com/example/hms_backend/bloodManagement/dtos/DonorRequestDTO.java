package com.example.hms_backend.bloodManagement.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonorRequestDTO {

    @NotBlank(message = "Donor name is mandatory")
    @Size(min = 2, max = 100, message = "Donor name must be between 2 and 100 characters")
    private String donorName;

    @NotNull(message = "Age is mandatory")
    @Min(value = 18, message = "Donor must be at least 18 years old")
    @Max(value = 65, message = "Donor must be at most 65 years old")
    private Integer age;

    @NotBlank(message = "Gender is mandatory")
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
    private String gender;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phone;

    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Last donation date is mandatory")
    @PastOrPresent(message = "Last donation date cannot be in the future")
    private LocalDate lastDonationDate;

    @NotBlank(message = "Blood group is mandatory")
    @Pattern(regexp = "^(A\\+|A-|B\\+|B-|AB\\+|AB-|O\\+|O-)$",
            message = "Blood group must be one of: A+, A-, B+, B-, AB+, AB-, O+, O-")
    private String bloodGroup;

    @NotBlank(message = "Address is mandatory")
    @Size(min = 10, max = 500, message = "Address must be between 10 and 500 characters")
    private String address;

    String Status;

}
