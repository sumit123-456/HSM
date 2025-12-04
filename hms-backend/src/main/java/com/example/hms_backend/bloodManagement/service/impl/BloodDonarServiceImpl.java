package com.example.hms_backend.bloodManagement.service.impl;


import com.example.hms_backend.bloodManagement.dtos.DonorRequestDTO;
import com.example.hms_backend.bloodManagement.dtos.DonorResponseDTO;
import com.example.hms_backend.bloodManagement.model.BloodDonor;
import com.example.hms_backend.bloodManagement.repo.BloodDonorRepository;
import com.example.hms_backend.bloodManagement.service.BloodDonorService;
import com.example.hms_backend.bloodManagement.utils.DonorIdGenerator;
import com.example.hms_backend.exception.DonorAlreadyExistsException;
import com.example.hms_backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BloodDonarServiceImpl implements BloodDonorService {

    private static final Logger logger = LoggerFactory.getLogger(BloodDonarServiceImpl.class);
    private final BloodDonorRepository bloodDonorRepository;
    private final DonorIdGenerator donorIdGenerator;

    @Override
    public DonorResponseDTO createDonor(DonorRequestDTO donorRequestDTO) {
        logger.info("Creating new donor with phone: {}", donorRequestDTO.getPhone());

        // Check if phone number already exists
        if (bloodDonorRepository.existsByPhone(donorRequestDTO.getPhone())) {
            logger.warn("Donor with phone number {} already exists", donorRequestDTO.getPhone());
            throw new DonorAlreadyExistsException("Donor with phone number " + donorRequestDTO.getPhone() + " already exists");
        }

        // Check if email already exists (if provided)
        if (!ObjectUtils.isEmpty(donorRequestDTO.getEmail()) &&
                bloodDonorRepository.existsByEmail(donorRequestDTO.getEmail())) {
            logger.warn("Donor with email {} already exists", donorRequestDTO.getEmail());
            throw new DonorAlreadyExistsException("Donor with email " + donorRequestDTO.getEmail() + " already exists");
        }

        // Convert DTO to Entity
        BloodDonor donor = convertToEntity(donorRequestDTO);

        // Generate donor ID manually (alternative to @PrePersist)
        String generatedDonorId = donorIdGenerator.generateSimpleDonorId();
        donor.setDonorId(generatedDonorId);
        logger.info("Generated donor ID: {}", generatedDonorId);

        // Save donor
        BloodDonor savedDonor = bloodDonorRepository.save(donor);
        logger.info("Donor created successfully with ID: {}", savedDonor.getDonorId());

        return convertToResponseDTO(savedDonor);
    }

    @Override
    @Transactional(readOnly = true)
    public DonorResponseDTO getDonorById(String donorId) {
        logger.debug("Fetching donor by Donor ID: {}", donorId);
        BloodDonor donor = bloodDonorRepository.findByDonorId(donorId)
                .orElseThrow(() -> {
                    logger.warn("Donor not found with Donor ID: {}", donorId);
                    return new ResourceNotFoundException("Donor not found with Donor ID: " + donorId);
                });
        return convertToResponseDTO(donor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DonorResponseDTO> getAllDonors() {
        logger.debug("Fetching all donors");
        return bloodDonorRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    @Override
    public List<DonorResponseDTO> getActiveDonors() {
        logger.debug("Fetching all active donors");

        return bloodDonorRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DonorResponseDTO> getDonorsByBloodGroup(String bloodGroup) {
        logger.debug("Fetching donors by blood group: {}", bloodGroup);

        return bloodDonorRepository.findByBloodGroupAndIsActiveTrue(bloodGroup)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DonorResponseDTO updateDonor(Long donorId, DonorRequestDTO donorRequestDTO) {
        logger.info("Updating donor with ID: {}", donorId);

        // Check if donor exists
        BloodDonor existingDonor = bloodDonorRepository.findById(donorId)
                .orElseThrow(() -> {
                    logger.warn("Donor not found with ID: {}", donorId);
                    return new ResourceNotFoundException("Donor not found with ID: " + donorId);
                });

        // Check if phone number is being changed and already exists
        if (!existingDonor.getPhone().equals(donorRequestDTO.getPhone()) &&
                bloodDonorRepository.existsByPhone(donorRequestDTO.getPhone())) {
            logger.warn("Phone number {} already exists", donorRequestDTO.getPhone());
            throw new DonorAlreadyExistsException("Phone number " + donorRequestDTO.getPhone() + " already exists");
        }

        // Check if email is being changed and already exists
        if (!ObjectUtils.isEmpty(donorRequestDTO.getEmail()) &&
                !ObjectUtils.isEmpty(existingDonor.getEmail()) &&
                !existingDonor.getEmail().equals(donorRequestDTO.getEmail()) &&
                bloodDonorRepository.existsByEmail(donorRequestDTO.getEmail())) {
            logger.warn("Email {} already exists", donorRequestDTO.getEmail());
            throw new DonorAlreadyExistsException("Email " + donorRequestDTO.getEmail() + " already exists");
        }

        // Update donor details
        updateEntityFromDTO(existingDonor, donorRequestDTO);

        BloodDonor updatedDonor = bloodDonorRepository.save(existingDonor);
        logger.info("Donor updated successfully with ID: {}", updatedDonor.getDonorId());

        return convertToResponseDTO(updatedDonor);
    }

    @Override
    public void deactivateDonor(Long donorId) {
        logger.info("Deactivating donor with ID: {}", donorId);

        BloodDonor donor = bloodDonorRepository.findById(donorId)
                .orElseThrow(() -> {
                    logger.warn("Donor not found with ID: {}", donorId);
                    return new ResourceNotFoundException("Donor not found with ID: " + donorId);
                });

        donor.setIsActive(false);
        bloodDonorRepository.save(donor);
        logger.info("Donor deactivated successfully with ID: {}", donorId);
    }

    @Override
    public void activateDonor(Long donorId) {
        logger.info("Activating donor with ID: {}", donorId);

        BloodDonor donor = bloodDonorRepository.findById(donorId)
                .orElseThrow(() -> {
                    logger.warn("Donor not found with ID: {}", donorId);
                    return new ResourceNotFoundException("Donor not found with ID: " + donorId);
                });

        donor.setIsActive(true);
        bloodDonorRepository.save(donor);
        logger.info("Donor activated successfully with ID: {}", donorId);
    }

    @Override
    public boolean isPhoneNumberExists(String phone) {
        return bloodDonorRepository.existsByPhone(phone);
    }

    @Override
    public boolean isEmailExists(String email) {
        return bloodDonorRepository.existsByEmail(email);
    }

    @Override
    public List<DonorResponseDTO> searchDonors(String searchTerm) {
        logger.debug("Searching donors with term: {}", searchTerm);

        return bloodDonorRepository.searchDonors(searchTerm)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long getDonorCountByBloodGroup(String bloodGroup) {
        logger.debug("Getting donor count for blood group: {}", bloodGroup);
        return bloodDonorRepository.countActiveDonorsByBloodGroup(bloodGroup);
    }

    // Helper methods for conversion
    private BloodDonor convertToEntity(DonorRequestDTO dto) {
        BloodDonor donor = new BloodDonor();
        donor.setDonorName(dto.getDonorName());
        donor.setAge(dto.getAge());
        donor.setGender(dto.getGender());
        donor.setPhone(dto.getPhone());
        donor.setEmail(dto.getEmail());
        donor.setLastDonationDate(dto.getLastDonationDate());
        donor.setBloodGroup(dto.getBloodGroup());
        donor.setAddress(dto.getAddress());
        return donor;
    }

    private DonorResponseDTO convertToResponseDTO(BloodDonor donor) {
        DonorResponseDTO dto = new DonorResponseDTO();
        dto.setDonorId(donor.getDonorId());
        dto.setId(donor.getId());
        dto.setDonorName(donor.getDonorName());
        dto.setAge(donor.getAge());
        dto.setGender(donor.getGender());
        dto.setPhone(donor.getPhone());
        dto.setEmail(donor.getEmail());
        dto.setLastDonationDate(donor.getLastDonationDate());
        dto.setBloodGroup(donor.getBloodGroup());
        dto.setAddress(donor.getAddress());
        dto.setCreatedDate(donor.getCreatedDate());
        dto.setUpdatedDate(donor.getUpdatedDate());
        dto.setIsActive(donor.getIsActive());
        return dto;
    }

    private void updateEntityFromDTO(BloodDonor donor, DonorRequestDTO dto) {
        if (!ObjectUtils.isEmpty(dto.getDonorName())) {
            donor.setDonorName(dto.getDonorName());
        }
        if (!ObjectUtils.isEmpty(dto.getAge())) {
            donor.setAge(dto.getAge());
        }
        if (!ObjectUtils.isEmpty(dto.getGender())) {
            donor.setGender(dto.getGender());
        }
        if (!ObjectUtils.isEmpty(dto.getPhone())) {
            donor.setPhone(dto.getPhone());
        }
        if (!ObjectUtils.isEmpty(dto.getEmail())) {
            donor.setEmail(dto.getEmail());
        }
        if (!ObjectUtils.isEmpty(dto.getLastDonationDate())) {
            donor.setLastDonationDate(dto.getLastDonationDate());
        }
        if (!ObjectUtils.isEmpty(dto.getBloodGroup())) {
            donor.setBloodGroup(dto.getBloodGroup());
        }
        if (!ObjectUtils.isEmpty(dto.getAddress())) {
            donor.setAddress(dto.getAddress());
        }
    }
}
