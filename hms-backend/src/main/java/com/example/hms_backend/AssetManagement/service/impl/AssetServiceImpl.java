package com.example.hms_backend.AssetManagement.service.impl;


import com.example.hms_backend.AssetManagement.dto.AssetRequestDTO;
import com.example.hms_backend.AssetManagement.dto.AssetResponseDTO;
import com.example.hms_backend.AssetManagement.entity.Asset;
import com.example.hms_backend.AssetManagement.mapper.AssetMapper;
import com.example.hms_backend.AssetManagement.repo.AssetRepository;
import com.example.hms_backend.AssetManagement.service.AssetService;
import com.example.hms_backend.exception.DuplicateResourceException;
import com.example.hms_backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final AssetMapper assetMapper;

    @Override
    @Transactional
    public AssetResponseDTO createAsset(AssetRequestDTO assetRequestDTO) {
        log.info("Creating new asset with assetId: {}", assetRequestDTO.getAssetId());

        // Check for duplicate assetId
        if (assetRepository.existsByAssetId(assetRequestDTO.getAssetId())) {
            throw new DuplicateResourceException(
                    String.format("Asset with ID %s already exists", assetRequestDTO.getAssetId())
            );
        }

        // Check for duplicate serial number
        if (assetRepository.existsBySerialNumber(assetRequestDTO.getSerialNumber())) {
            throw new DuplicateResourceException(
                    String.format("Asset with serial number %s already exists", assetRequestDTO.getSerialNumber())
            );
        }

        Asset asset = assetMapper.toEntity(assetRequestDTO);
        Asset savedAsset = assetRepository.save(asset);
        log.info("Asset created successfully with id: {}", savedAsset.getId());

        return assetMapper.toDTO(savedAsset);
    }

    @Override
    @Transactional(readOnly = true)
    public AssetResponseDTO getAssetById(Long id) {
        log.debug("Fetching asset by id: {}", id);
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Asset not found with id: %d", id)
                ));
        return assetMapper.toDTO(asset);
    }

    @Override
    @Transactional(readOnly = true)
    public AssetResponseDTO getAssetByAssetId(String assetId) {
        log.debug("Fetching asset by assetId: {}", assetId);
        Asset asset = assetRepository.findByAssetId(assetId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Asset not found with assetId: %s", assetId)
                ));
        return assetMapper.toDTO(asset);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AssetResponseDTO> getAllAssets(Pageable pageable) {
        log.debug("Fetching all assets with pagination: {}", pageable);
        return assetRepository.findAll(pageable) .
                map(assetMapper::toDTO);
    }

    @Override
    @Transactional
    public AssetResponseDTO updateAsset(Long id, AssetRequestDTO assetRequestDTO) {
        log.info("Updating asset with id: {}", id);

        Asset existingAsset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Asset not found with id: %d", id)
                ));

        // Check for duplicate assetId if changed
        if (!existingAsset.getAssetId().equals(assetRequestDTO.getAssetId()) &&
                assetRepository.existsByAssetId(assetRequestDTO.getAssetId())) {
            throw new DuplicateResourceException(
                    String.format("Asset with ID %s already exists", assetRequestDTO.getAssetId()));

        }
        // Check for duplicate serial number if changed
        if (!existingAsset.getSerialNumber().equals(assetRequestDTO.getSerialNumber()) &&
                assetRepository.existsBySerialNumber(assetRequestDTO.getSerialNumber())) {
            throw new DuplicateResourceException(
                    String.format("Asset with serial number %s already exists", assetRequestDTO.getSerialNumber())
            );
        }

        Asset updatedAsset = assetMapper.updateEntityFromDTO(assetRequestDTO, existingAsset);
        Asset savedAsset = assetRepository.save(updatedAsset);
        log.info("Asset updated successfully with id: {}", id);

        return assetMapper.toDTO(savedAsset);
    }

    @Override
    public void deleteAsset(Long id) {
        log.info("Deleting asset with id: {}", id);

        if (!assetRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format("Asset not found with id: %d", id)
            );
        }
        assetRepository.deleteById(id);
        log.info("Asset deleted successfully with id: {}", id);
    }

    @Override
    public AssetResponseDTO updateAssetStatus(Long id, String status) {
        log.info("Updating asset status with id: {} to {}", id, status);

        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Asset not found with id: %d", id)
                ));

        try {
            Asset.AssetStatus assetStatus = Asset.AssetStatus.valueOf(status.toUpperCase());
            asset.setStatus(assetStatus);
            Asset updatedAsset = assetRepository.save(asset);
            log.info("Asset status updated successfully for id: {}", id);

            return assetMapper.toDTO(updatedAsset);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value: " + status);
        }
    }
}
