package com.example.hms_backend.RoomAndBedManager.controller;

import com.example.hms_backend.RoomAndBedManager.dto.BedDTO;
import com.example.hms_backend.RoomAndBedManager.service.BedService;
import com.example.hms_backend.enums.Enums;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/beds")
public class BedController {

    private final BedService bedService;

    // ✅ Get Room list & statuses needed for Add Bed form UI
    @GetMapping("/form-data")
//    @PreAuthorize("hasAuthority('BED_ADD')")
    public ResponseEntity<?> getAddBedFormData() {
        return ResponseEntity.ok(
                new Object() {
                    public final Object rooms = bedService.getAllRoomsName();
                    public final Object bedStatus = Enums.BedStatus.values();
                }
        );
    }

    // ✅ Add Bed
    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('BED_ADD')")
    public ResponseEntity<?> saveBed(@Valid @RequestBody BedDTO bedDTO) {

        if (bedService.existsByBedNumber(String.valueOf(bedDTO.getBedNumber()))) {
            return ResponseEntity.badRequest().body("Bed number already exists!");
        }

        bedService.saveBed(bedDTO);
        return ResponseEntity.ok("Bed added successfully!");
    }
}