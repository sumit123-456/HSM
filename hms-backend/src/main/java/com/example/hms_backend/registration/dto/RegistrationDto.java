package com.example.hms_backend.registration.dto;

import com.example.hms_backend.accountant.dto.AccountantDto;
import com.example.hms_backend.authentication.dto.AddressDto;
import com.example.hms_backend.doctor.dto.DoctorDto;
import com.example.hms_backend.enums.Enums;
import com.example.hms_backend.head_nurse.dto.HeadNurseDto;
import com.example.hms_backend.hr.dto.HumanResourceDto;
import com.example.hms_backend.insurance.dto.InsurerDto;
import com.example.hms_backend.laboratory.laboratorist.dto.LaboratoristDto;
import com.example.hms_backend.pharmacy.pharmacist.dto.PharmacistDto;
import com.example.hms_backend.receptionist.dto.ReceptionistDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationDto {

    // users table
    @NotBlank(message = "Username is required")
    @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
    private String username;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#~])[A-Za-z\\d@$!%*?&#~]{8,}$",
            message = "Password must include uppercase, number, and special character")
    private String password;

    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#~])[A-Za-z\\d@$!%*?&#~]{8,}$",
            message = "Password must include uppercase, number, and special character")
    private String confirmPassword;


    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "First name must contain only letters and spaces")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Last name must contain only letters and spaces")
    private String lastName;


    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp="^[0-9]{10}$", message="Invalid mobile number")
    private String mobileNumber;

    private MultipartFile profilePic;

    @NotNull(message = "Gender is required")
    private Enums.Gender gender ;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    private Integer age;

    @NotNull(message = "ID Proof is required")
    private Enums.IdProofType idProofType;

    private MultipartFile idProofPic;

    @NotNull(message = "Joining date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joiningDate;

    @NotNull(message = "Blood group is required")
    private Enums.BloodGroup bloodGroup;

    @NotNull(message = "Role is required")
    private Long roleId;


    @Valid
    private AddressDto addressDto;

    private DoctorDto doctorDto;

    private AccountantDto accountantDto;

    private LaboratoristDto laboratoristDto;

    private HeadNurseDto headNurseDto;

    private ReceptionistDto receptionistDto;

    private PharmacistDto pharmacistDto;

    private HumanResourceDto humanResourceDto;

    private InsurerDto insurerDto;



}
