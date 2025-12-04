package com.example.hms_backend.RoomAndBedManager.controller;

import com.example.hms_backend.RoomAndBedManager.dto.CreateRoomDTO;
import com.example.hms_backend.RoomAndBedManager.service.RoomService;
import com.example.hms_backend.enums.Enums;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    /**
     * ✅ Get dropdown data for Add Room form
     */
    @GetMapping("/form-data")
//    @PreAuthorize("hasAuthority('ROOM_ADD')")
    public ResponseEntity<?> getRoomFormData() {
        return ResponseEntity.ok().body(
                new Object() {
                    public final Enums.RoomStatus[] roomStatus = Enums.RoomStatus.values();
                    public final Object roomTypes = Enums.RoomType.values();
                }
        );
    }

    /**
     * ✅ Save New Room
     */
    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('ROOM_ADD')")
    public ResponseEntity<?> saveRoom(@Valid @RequestBody CreateRoomDTO dto, BindingResult result) {

        // Check duplicate room number
        if (roomService.checkRoomNumberExists(dto.getRoomNo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Room number already exists!");
        }

        // If validation errors exist return them
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
        }

        roomService.createRoom(dto);
        return ResponseEntity.ok("Room created successfully!");
    }
}