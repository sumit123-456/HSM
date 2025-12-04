package com.example.hms_backend.bloodManagement.service.impl;


import com.example.hms_backend.bloodManagement.dtos.BloodDonationRequestDTO;
import com.example.hms_backend.bloodManagement.dtos.BloodDonationResponceDTO;
import com.example.hms_backend.bloodManagement.model.BloodDonation;
import com.example.hms_backend.bloodManagement.model.BloodDonor;
import com.example.hms_backend.bloodManagement.model.BloodStock;
import com.example.hms_backend.bloodManagement.repo.BloodDonationRepository;
import com.example.hms_backend.bloodManagement.repo.BloodDonorRepository;
import com.example.hms_backend.bloodManagement.repo.BloodStockRepository;
import com.example.hms_backend.bloodManagement.service.BloodDonationService;
import com.example.hms_backend.bloodManagement.utils.DonationIdGenerator;
import com.example.hms_backend.bloodManagement.utils.StockIdGenerator;
import com.example.hms_backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BloodDonationServiceImpl implements BloodDonationService {

    private static final Logger logger = LoggerFactory.getLogger(BloodDonationService.class);

    private final BloodDonationRepository donationRepository;
    private final BloodDonorRepository donorRepository;
    private final BloodStockRepository stockRepository;
    private final DonationIdGenerator donationIdGenerator;
    private final StockIdGenerator stockIdGenerator;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public BloodDonationResponceDTO createDonation(BloodDonationRequestDTO requestDTO) {
        logger.info("Creating new donation for donor: {}", requestDTO.getDonorId());

        // 1. Find and validate donor
        BloodDonor donor = donorRepository.findByDonorId(requestDTO.getDonorId())
                .orElseThrow(() -> {
                    logger.error("Donor not found with ID: {}", requestDTO.getDonorId());
                    return new ResourceNotFoundException("Donor not found with ID: " + requestDTO.getDonorId());
                });

        validateDonation(donor, requestDTO);

        // 2. Create donation entity
        BloodDonation donation = new BloodDonation();
        donation.setDonationId(donationIdGenerator.generateDonationId());
        donation.setDonor(donor);
        donation.setDonationDate(requestDTO.getDonationDate());
        donation.setUnitsCollected(requestDTO.getUnitsCollected());
        donation.setBloodGroup(donor.getBloodGroup());
        donation.setNotes(requestDTO.getNotes());
        donation.setStatus("COLLECTED");

        // Set expiry date (42 days from donation date)
        donation.setExpiryDate(requestDTO.getDonationDate().plusDays(42));

        BloodDonation savedDonation = donationRepository.save(donation);
        logger.info("Donation created successfully with ID: {}", savedDonation.getDonationId());

        // 3. Create blood stock entry
        BloodStock bloodStock = createBloodStockFromDonation(savedDonation);
        BloodStock savedStock = stockRepository.save(bloodStock);
        logger.info("Blood stock created with ID: {}", savedStock.getStockId());

        // 4. Update donor's last donation date
        donor.setLastDonationDate(requestDTO.getDonationDate());
        donorRepository.save(donor);
        logger.info("Donor {} last donation date updated", donor.getDonorId());

        return convertToResponseDTO(savedDonation, savedStock);
    }

    private void validateDonation(BloodDonor donor, BloodDonationRequestDTO requestDTO) {
        // Check if donor is active
        if (donor.getIsActive() == null || !donor.getIsActive()) {
            throw new IllegalStateException("Donor is not active");
        }

        // Check donation frequency (3 months gap) - handle first-time donors
        if (donor.getLastDonationDate() != null) {
            LocalDate minNextDonationDate = donor.getLastDonationDate().plusMonths(3);
            if (requestDTO.getDonationDate().isBefore(minNextDonationDate)) {
                throw new IllegalStateException(
                        String.format("Donor cannot donate yet. Minimum gap required. Last donation: %s, Next available: %s",
                                donor.getLastDonationDate(), minNextDonationDate)
                );
            }
        }

        // Validate donation date
        if (requestDTO.getDonationDate() == null) {
            throw new IllegalStateException("Donation date is required");
        }

        if (requestDTO.getDonationDate().isAfter(LocalDate.now())) {
            throw new IllegalStateException("Donation date cannot be in the future");
        }

        // Validate units collected
        if (requestDTO.getUnitsCollected() == null || requestDTO.getUnitsCollected() <= 0) {
            throw new IllegalStateException("Units collected must be greater than 0");
        }
    }

    private BloodStock createBloodStockFromDonation(BloodDonation donation) {
        BloodStock stock = new BloodStock();
        stock.setStockId(stockIdGenerator.generateStockId());
        stock.setDonation(donation);
        stock.setBloodGroup(donation.getBloodGroup());
        stock.setUnitsAvailable(donation.getUnitsCollected());
        stock.setExpiryDate(donation.getExpiryDate());
        stock.setIsActive(true);
        return stock;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BloodDonationResponceDTO> getDonationsByDonor(String donorId) {
        logger.debug("Fetching donations for donor: {}", donorId);

        // Verify donor exists
        if (!donorRepository.existsByDonorId(donorId)) {
            throw new ResourceNotFoundException("Donor not found with ID: " + donorId);
        }

        List<BloodDonation> donations = donationRepository.findByDonorDonorId(donorId);
        return donations.stream()
                .map(donation -> {
                    BloodStock stock = stockRepository.findByDonationDonationId(donation.getDonationId()).orElse(null);
                    return convertToResponseDTO(donation, stock);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BloodDonationResponceDTO> getAllDonations() {
        logger.debug("Fetching all donations");

        List<BloodDonation> donations = donationRepository.findAll();
        return donations.stream()
                .map(donation -> {
                    BloodStock stock = stockRepository.findByDonationDonationId(donation.getDonationId()).orElse(null);
                    return convertToResponseDTO(donation, stock);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void storeDonation(String donationId) {
        logger.info("Storing donation: {}", donationId);

        BloodDonation donation = donationRepository.findByDonationId(donationId)
                .orElseThrow(() -> {
                    logger.error("Donation not found: {}", donationId);
                    return new ResourceNotFoundException("Donation not found: " + donationId);
                });

        if (!"PROCESSED".equals(donation.getStatus())) {
            throw new IllegalStateException("Donation must be processed before storage. Current status: " + donation.getStatus());
        }

        donation.setStatus("STORED");
        donationRepository.save(donation);
        logger.info("Donation {} stored successfully", donationId);
    }

    @Override
    @Transactional
    public void processDonation(String donationId) {
        logger.info("Processing donation: {}", donationId);

        BloodDonation donation = donationRepository.findByDonationId(donationId)
                .orElseThrow(() -> {
                    logger.error("Donation not found: {}", donationId);
                    return new ResourceNotFoundException("Donation not found: " + donationId);
                });

        if (!"COLLECTED".equals(donation.getStatus())) {
            throw new IllegalStateException(
                    "Donation can only be processed from COLLECTED status. Current status: " + donation.getStatus()
            );
        }

        donation.setStatus("PROCESSED");
        donationRepository.save(donation);
        logger.info("Donation {} processed successfully", donationId);
    }

    @Override
    @Transactional(readOnly = true)
    public BloodDonationResponceDTO getDonationById(Long id) {
        logger.debug("Fetching donation by ID: {}", id);

        BloodDonation donation = donationRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Donation not found with ID: {}", id);
                    return new ResourceNotFoundException("Donation not found with ID: " + id);
                });

        BloodStock stock = stockRepository.findByDonationDonationId(donation.getDonationId())
                .orElse(null);

        return convertToResponseDTO(donation, stock);
    }
    @Transactional(readOnly = true)
    public BloodDonationResponceDTO getDonationByDonationId(String donationId) {
        logger.debug("Fetching donation by donation ID: {}", donationId);

        BloodDonation donation = donationRepository.findByDonationId(donationId)
                .orElseThrow(() -> {
                    logger.error("Donation not found with donation ID: {}", donationId);
                    return new ResourceNotFoundException("Donation not found with donation ID: " + donationId);
                });

        BloodStock stock = stockRepository.findByDonationDonationId(donation.getDonationId())
                .orElse(null);

        return convertToResponseDTO(donation, stock);
    }

    @Transactional(readOnly = true)
    public List<BloodDonationResponceDTO> getAvailableDonations() {
        logger.debug("Fetching available donations for stock");

        return donationRepository.findAvailableDonationsForStock()
                .stream()
                .map(donation -> convertToResponseDTO(donation, null))
                .collect(Collectors.toList());
    }

    private BloodDonationResponceDTO convertToResponseDTO(BloodDonation donation, BloodStock stock) {
        BloodDonationResponceDTO response = new BloodDonationResponceDTO();

        // Map donation fields
        response.setId(donation.getId());
        response.setDonationId(donation.getDonationId());
        response.setDonationDate(donation.getDonationDate());
        response.setExpiryDate(donation.getExpiryDate());
        response.setUnitsCollected(donation.getUnitsCollected());
        response.setBloodGroup(donation.getBloodGroup());
        response.setStatus(donation.getStatus());
        response.setNotes(donation.getNotes());

        // Map donor information
        if (donation.getDonor() != null) {
            response.setDonorId(donation.getDonor().getDonorId());
            response.setDonorName(donation.getDonor().getDonorName());
            response.setDonorPhone(donation.getDonor().getPhone());
           // response.setDonorBloodGroup(donation.getDonor().getBloodGroup());
        }

        // Map stock information
        if (stock != null) {
            response.setStockId(stock.getStockId());
            response.setUnitsAvailable(stock.getUnitsAvailable());
           // response.setStockIsActive(stock.getIsActive());
        }

        return response;
    }

}
