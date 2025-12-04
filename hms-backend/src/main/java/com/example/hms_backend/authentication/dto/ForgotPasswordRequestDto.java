package com.example.hms_backend.authentication.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ForgotPasswordRequestDto {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 60, message = "Email must be under 60 characters")
    private String email;

    @Pattern(regexp = "\\d{6}", message = "OTP must be a 6-digit number")
    private String otp;

    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#~])[A-Za-z\\d@$!%*?&#~]{8,}$",
            message = "Password must include uppercase, number, and special character"
    )
    private String newPassword;

    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#~])[A-Za-z\\d@$!%*?&#~]{8,}$",
            message = "Password must include uppercase, number, and special character"
    )
    private String confirmPassword;

}