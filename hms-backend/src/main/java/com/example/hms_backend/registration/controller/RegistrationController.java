package com.example.hms_backend.registration.controller;

import com.example.hms_backend.authentication.entity.Role;
import com.example.hms_backend.authentication.repo.RoleRepo;
import com.example.hms_backend.authentication.repo.UserRepo;
import com.example.hms_backend.department.repo.DepartmentRepo;
import com.example.hms_backend.department.service.DepartmentService;
import com.example.hms_backend.enums.Enums;
import com.example.hms_backend.registration.dto.RegistrationDto;
import com.example.hms_backend.registration.dto.RegistrationDtoValidator;
import com.example.hms_backend.registration.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final RoleRepo roleRepo;
    private final DepartmentRepo departmentRepo;
    private final UserRepo userRepo;
    private final RegistrationDtoValidator registrationDtoValidator;
    private final DepartmentService departmentService;

    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    // ✅ Get dropdown data for React form
    @GetMapping("/form-data")
    public ResponseEntity<?> getFormData() {

        List<Role> allRoles = roleRepo.findAll();
        List<Role> filteredRoles = allRoles.stream()
                .filter(role -> !role.getRoleName().equals("ROLE_SUPER_ADMIN") && !role.getRoleName().equals("ROLE_ADMIN"))
                .map(role -> {
                    Role displayRole = new Role();
                    displayRole.setRoleId(role.getRoleId());
                    displayRole.setRoleName(role.getRoleName().replace("ROLE_", "")); // Remove prefix
                    return displayRole;
                })
                .toList();

        return ResponseEntity.ok(
                new Object() {
                    public final List<Role> roles = filteredRoles;
                    public final Object departments = departmentService.findIdName() ;
                    public final Object experienceOptions = Enums.ExperienceLevel.values();
                }
        );
    }

    // ✅ Register user    consumes = {"multipart/form-data"}
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping(value = "/add",consumes = {"multipart/form-data"})
    public ResponseEntity<?> registerUser(@Valid @RequestPart("dto") RegistrationDto dto,
                                          @RequestPart(value = "profilePic", required = false) MultipartFile profilePic,
                                          @RequestPart(value = "idProofPic", required = false) MultipartFile idProofPic) {

        System.out.println(1);
        // ✅ Attach files to DTO
        dto.setProfilePic(profilePic);
        dto.setIdProofPic(idProofPic);
        System.out.println(2);

        // Manually run validator (since form-binding is gone)
        DataBinder binder = new DataBinder(dto);
        System.out.println(3);
        binder.addValidators(registrationDtoValidator);
        System.out.println(4);

        binder.validate();
        System.out.println(5);
        BindingResult result = binder.getBindingResult();
        System.out.println(6);

        // Check duplicate username
        if (userRepo.existsByUsername(dto.getUsername())) {
            System.out.println(7);
            result.rejectValue("username", "duplicate", "Username already exists");
        }

        // Check duplicate email
        if (userRepo.existsByEmail(dto.getEmail())) {
            System.out.println(8);
            result.rejectValue("email", "duplicate", "Email already exists");
        }

        // Password match
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            System.out.println(9);
            result.rejectValue("confirmPassword", "mismatch", "Passwords do not match");
        }

        // If errors → return JSON error response
        if (result.hasErrors()) {
            System.out.println(10);
            List<Map<String, String>> errorList = result.getFieldErrors().stream()
                    .map(fieldError -> {
                        Map<String, String> errorMap = new HashMap<>();
                        errorMap.put("field", fieldError.getField());
                        errorMap.put("defaultMessage", fieldError.getDefaultMessage());
                        return errorMap;
                    })
                    .collect(Collectors.toList());
            System.out.println(11);
            return ResponseEntity.badRequest().body(errorList);

        }

        try {
            System.out.println(12);
            registrationService.registerUser(dto);
        } catch (IllegalArgumentException | IOException e) {
            System.out.println(13);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        System.out.println(14);
        return ResponseEntity.ok("Registration Successful!");
    }
}

