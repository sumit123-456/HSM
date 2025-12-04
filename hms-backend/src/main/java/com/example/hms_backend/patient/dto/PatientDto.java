package com.example.hms_backend.patient.dto;


import com.example.hms_backend.authentication.dto.AddressDto;
import com.example.hms_backend.enums.Enums;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
    private Long id; // optional for update
    private String patientHospitalId; // generated after save

    @Valid
    @NotNull(message = "Address is required")
    private AddressDto address;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must be under 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must be under 50 characters")
    private String lastName;

    @Email(message = "Email must be valid")
    private String email; // optional

    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @Min(value = 0, message = "Age must be a positive number")
    @Max(value = 150, message = "Age must be realistic")
    private Integer age;

    @NotNull(message = "Gender is required")
    private Enums.Gender gender;

    @Size(max = 100, message = "Occupation must be under 100 characters")
    private String occupation; // optional

    private Enums.BloodGroup bloodGroup; // optional

    @NotBlank(message = "Contact info is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Contact must be a valid 10-digit Indian mobile number")
    private String contactInfo;

    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Emergency contact must be a valid 10-digit Indian mobile number")
    private String emergencyContact; // optional but format enforced if present

    private Enums.IdProofType idProofType; // optional

    private byte[] idProofFile; // optional

    @NotNull(message = "Marital status is required")
    private Enums.MaritalStatus maritalStatus;

//    @NotNull(message = "Visit type is required")
//    private Enums.VisitType visitType;

    @Size(max = 1000, message = "Note must be under 1000 characters")
    private String note; // optional

}
