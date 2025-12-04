package com.example.hms_backend.healthPackage.controller;


import com.example.hms_backend.department.dto.DepartmentDTO;
import com.example.hms_backend.healthPackage.common.ApiResponse;
import com.example.hms_backend.healthPackage.dto.HealthPackageDTO;
import com.example.hms_backend.healthPackage.dto.HealthPackageResponseDTO;
import com.example.hms_backend.healthPackage.repository.HealthPackageRepository;
import com.example.hms_backend.healthPackage.service.HealthPackageService;
import com.example.hms_backend.registration.dto.RegistrationDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/health-package")
@AllArgsConstructor
public class HealthPackageController {

    private final HealthPackageService service;

    private final HealthPackageRepository repository;
    @Valid

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<String>> create(@Valid @RequestPart("dto") HealthPackageDTO dto,
                                                      @RequestPart(value = "image", required = false) MultipartFile image) {
        if (service.existsByNameIgnoreCase(dto.getName()))
        {
            ResponseEntity.badRequest().body("Package name already exists!");
        }

        System.err.println("Form data : " + dto);
        // ✅ Attach files to DTO
        dto.setImage(image);


        service.create(dto);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Health package created successfully")
                        .data(null)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<HealthPackageResponseDTO>> listPackages() {
        List<HealthPackageResponseDTO> packages = service.getAll();

        return ResponseEntity.ok(ApiResponse.<HealthPackageResponseDTO>builder()
                .success(true)
                .message("Health package created successfully")
                .dataList(packages)
                .build());
    }


//    @PutMapping((value{id} = ,consumes = {"multipart/form-data"})
//    public ResponseEntity<ApiResponse<String>> update(@PathVariable Long id,@RequestBody HealthPackageDTO dto) {
//        service.update(id,dto);
//        return ResponseEntity.ok(ApiResponse.<String>builder()
//                .success(true)
//                .message("Health package updated successfully")
//                .data(null)
//                .build());
//    }


    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<String>> updateHealthPackage(
            @PathVariable Long id,
            @ModelAttribute HealthPackageDTO dto) {

        System.out.println(dto.toString());

        if (service.existsByNameIgnoreCase(dto.getName()))
        {
            ResponseEntity.badRequest().body("Package name already exists!");
        }

        service.update(id, dto);

        return ResponseEntity.ok(ApiResponse.<String>builder()
                .success(true)
                .message("Health package updated successfully")
                .data(null)
                .build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Health package deleted successfully")
                        .data(null)
                        .build()
        );
    }


}
