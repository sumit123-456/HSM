package com.example.hms_backend.bloodManagement.contoller;


import com.example.hms_backend.bloodManagement.dtos.DonorRequestDTO;
import com.example.hms_backend.bloodManagement.dtos.DonorResponseDTO;
import com.example.hms_backend.bloodManagement.service.BloodDonorService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/blood-donors")
@Validated
@CrossOrigin("*")
@Slf4j
public class BloodDonorController {

    private static final Logger logger = LoggerFactory.getLogger(BloodDonorController.class);
    private final BloodDonorService bloodDonorService;

    @Autowired
    public BloodDonorController(BloodDonorService bloodDonorService) {
        this.bloodDonorService = bloodDonorService;
    }
    @PostMapping
    public ResponseEntity<DonorResponseDTO> createDonor(@Valid @RequestBody DonorRequestDTO donorRequestDTO) {
        logger.info("Received request to create new donor");

        DonorResponseDTO response = bloodDonorService.createDonor(donorRequestDTO);

        logger.info("Donor created successfully with ID: {}", response.getDonorId());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{donorId}")
    public ResponseEntity<DonorResponseDTO> getDonorById(@PathVariable String donorId) {
        logger.debug("Received request to get donor by ID: {}", donorId);
        DonorResponseDTO response = bloodDonorService.getDonorById(donorId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DonorResponseDTO>> getAllDonors() {
        logger.debug("Received request to get all donors");

        List<DonorResponseDTO> response = bloodDonorService.getAllDonors();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    public ResponseEntity<List<DonorResponseDTO>> getActiveDonors() {
        logger.debug("Received request to get active donors");

        List<DonorResponseDTO> response = bloodDonorService.getActiveDonors();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/blood-group/{bloodGroup}")
    public ResponseEntity<List<DonorResponseDTO>> getDonorsByBloodGroup(@PathVariable String bloodGroup) {
        logger.debug("Received request to get donors by blood group: {}", bloodGroup);

        List<DonorResponseDTO> response = bloodDonorService.getDonorsByBloodGroup(bloodGroup);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{donorId}")
    public ResponseEntity<DonorResponseDTO> updateDonor(
            @PathVariable Long donorId,
            @Valid @RequestBody DonorRequestDTO donorRequestDTO) {

        logger.info("Received request to update donor with ID: {}", donorId);

        DonorResponseDTO response = bloodDonorService.updateDonor(donorId, donorRequestDTO);

        logger.info("Donor updated successfully with ID: {}", donorId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{donorId}/deactivate")
    public ResponseEntity<Void> deactivateDonor(@PathVariable Long donorId) {
        logger.info("Received request to deactivate donor with ID: {}", donorId);

        bloodDonorService.deactivateDonor(donorId);

        logger.info("Donor deactivated successfully with ID: {}", donorId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{donorId}/activate")
    public ResponseEntity<Void> activateDonor(@PathVariable Long donorId) {
        logger.info("Received request to activate donor with ID: {}", donorId);

        bloodDonorService.activateDonor(donorId);

        logger.info("Donor activated successfully with ID: {}", donorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check-phone")
    public ResponseEntity<Boolean> checkPhoneExists(@RequestParam String phone) {

        logger.debug("Checking if phone exists: {}", phone);

        boolean exists = bloodDonorService.isPhoneNumberExists(phone);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {

        logger.debug("Checking if email exists: {}", email);

        boolean exists = bloodDonorService.isEmailExists(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DonorResponseDTO>> searchDonors(@RequestParam String q) {

        logger.debug("Searching donors with query: {}", q);

        List<DonorResponseDTO> response = bloodDonorService.searchDonors(q);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/blood-group/{bloodGroup}/count")
    public ResponseEntity<Long> getDonorCountByBloodGroup(@PathVariable String bloodGroup) {

        logger.debug("Getting donor count for blood group: {}", bloodGroup);

        Long count = bloodDonorService.getDonorCountByBloodGroup(bloodGroup);
        return ResponseEntity.ok(count);
    }
}
