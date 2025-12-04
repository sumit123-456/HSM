package com.example.hms_backend.bloodManagement.contoller;


import com.example.hms_backend.bloodManagement.dtos.BloodDonationRequestDTO;
import com.example.hms_backend.bloodManagement.dtos.BloodDonationResponceDTO;
import com.example.hms_backend.bloodManagement.service.BloodDonationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/blood-donations")
@RequiredArgsConstructor
@CrossOrigin("*")
public class BloodDonationController {

    private static final Logger logger = LoggerFactory.getLogger(BloodDonationController.class);

    private final BloodDonationService bloodDonationService;

    @PostMapping
    public ResponseEntity<BloodDonationResponceDTO> createDonation(@Valid @RequestBody BloodDonationRequestDTO requestDTO) {
        logger.info("Received request to create donation for donor: {}", requestDTO.getDonorId());

        BloodDonationResponceDTO response = bloodDonationService.createDonation(requestDTO);

        logger.info("Donation created successfully: {}", response.getDonationId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodDonationResponceDTO> getDonationById(@PathVariable Long id) {
        logger.debug("Fetching donation by ID: {}", id);

        BloodDonationResponceDTO response = bloodDonationService.getDonationById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/donor/{donorId}")
    public ResponseEntity<List<BloodDonationResponceDTO>> getDonationsByDonor(@PathVariable String donorId) {
        logger.debug("Fetching donations for donor: {}", donorId);

        List<BloodDonationResponceDTO> response = bloodDonationService.getDonationsByDonor(donorId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BloodDonationResponceDTO>> getAllDonations() {
        logger.debug("Fetching all donations");

        List<BloodDonationResponceDTO> response = bloodDonationService.getAllDonations();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{donationId}/process")
    public ResponseEntity<Void> processDonation(@PathVariable String donationId) {
        logger.info("Processing donation: {}", donationId);

        bloodDonationService.processDonation(donationId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{donationId}/store")
    public ResponseEntity<Void> storeDonation(@PathVariable String donationId) {
        logger.info("Storing donation: {}", donationId);

        bloodDonationService.storeDonation(donationId);

        return ResponseEntity.noContent().build();
    }
}
