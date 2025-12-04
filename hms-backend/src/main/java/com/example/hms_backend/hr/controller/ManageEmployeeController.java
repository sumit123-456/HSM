package com.example.hms_backend.hr.controller;

import com.example.hms_backend.hr.dto.EmployeeResponseDTO;
import com.example.hms_backend.hr.dto.EmployeeUpdateDTO;
import com.example.hms_backend.hr.service.ManageEmployeeServiceImpl;
import com.example.hms_backend.registration.dto.RegistrationDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class ManageEmployeeController {

    @Autowired
    private ManageEmployeeServiceImpl  manageEmployeeServiceImpl;

    @GetMapping("/role/{roleId}")
    public List<EmployeeResponseDTO> getUsersByRole(@PathVariable Long roleId) {


        System.out.println("Hello service");
        return manageEmployeeServiceImpl.getUsersByRole(roleId);
    }

    @PutMapping("/{userId}/status")
    public ResponseEntity<String> updateUserStatus(@PathVariable Long userId, @RequestBody Map<String, String> request) {
        String newStatus = request.get("status");
        return ResponseEntity.ok(manageEmployeeServiceImpl.updateStatus(userId,newStatus));
    }

    // ✅ Update user    consumes = {"multipart/form-data"}
    @PostMapping(value = "/update/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerUser(@Valid @RequestPart("dto") EmployeeUpdateDTO dto,
                                          @RequestPart(value = "idProofPic", required = false) MultipartFile idProofPic,
                                          @PathVariable Long userId) {


        return ResponseEntity.ok(manageEmployeeServiceImpl.updateEmployee(userId,dto, idProofPic));
    }
}
