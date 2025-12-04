package com.example.hms_backend.RoomAndBedManager.controller;

import com.example.hms_backend.RoomAndBedManager.dto.BedAssignmentDTO;
import com.example.hms_backend.RoomAndBedManager.repo.BedAssignmentRepo;
import com.example.hms_backend.RoomAndBedManager.service.BedAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BedAssignmentController {


    private final BedAssignmentService bedAssignmentService;

    private final BedAssignmentRepo bedAssignmentRepo;

    public BedAssignmentController(BedAssignmentService bedAssignmentService, BedAssignmentRepo bedAssignmentRepo) {
        this.bedAssignmentService = bedAssignmentService;
        this.bedAssignmentRepo = bedAssignmentRepo;
    }

    // ✅ Get Bed List (Room → Vacant Beds count)
    @GetMapping("/rooms")
//    @PreAuthorize("hasAuthority('BED_LIST')")
    public ResponseEntity<?> getBedList() {
        return ResponseEntity.ok(bedAssignmentService.findAllTotalVaccantBedsByRoom());
    }

    // ✅ Get Assign Bed Page Data (Bed numbers, Patients, Room details)
    @GetMapping("/assign/{roomId}")
    public ResponseEntity<?> getAssignBedData(@PathVariable Long roomId) {

        if (!bedAssignmentService.checkingVacantBedInRoom(roomId)) {
            return ResponseEntity.badRequest().body("No vacant beds available!");
        }

        return ResponseEntity.ok(
                new Object() {
                    public final Object bedNumbers = bedAssignmentService.showAllAvailableBedNumbers(roomId);
                    public final Object patientIds = bedAssignmentService.findAllPatientId();
                    public final Object room = bedAssignmentService.showRoomDTOById(roomId);
                }
        );
    }

    // ✅ Assign a bed
    @PostMapping("/assign")
//   @PreAuthorize("")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('BED_ASSIGN')")
    public ResponseEntity<?> assignBed(@RequestBody BedAssignmentDTO bedAssignmentDTO, Authentication auth) {

        System.out.println("Auth in controller: " + auth);
        System.out.println("Authorities in controller: " + auth.getAuthorities());

        Long roomId = bedAssignmentDTO.getRoomId();

        long activeAssignments = bedAssignmentRepo.countActiveAssignmentsForPatient(bedAssignmentDTO.getPatientId());
        System.out.println("Active assignments: " + activeAssignments);
        if (activeAssignments > 0) {
            return ResponseEntity.badRequest().body("Patient is already assigned to a bed.");
        }

        try {
            bedAssignmentService.saveBedAssignment(bedAssignmentDTO);
            return ResponseEntity.ok("Bed assigned successfully!");

        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Get All Allocated Beds
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('BED_ASSIGN')")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allocated")
    public ResponseEntity<?> getAllocatedBeds() {
        return ResponseEntity.ok(bedAssignmentService.showAllotedBed());
    }

    // ✅ Release Bed
    @PutMapping("/release/{bedAssignmentId}")
    public ResponseEntity<?> releaseBed(@PathVariable Long bedAssignmentId) {
        bedAssignmentService.releaseBed(bedAssignmentId);
        return ResponseEntity.ok("Bed released successfully!");
    }
}

