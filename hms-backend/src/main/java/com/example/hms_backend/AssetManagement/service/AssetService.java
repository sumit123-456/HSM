package com.example.hms_backend.AssetManagement.service;


import com.example.hms_backend.AssetManagement.dto.AssetRequestDTO;
import com.example.hms_backend.AssetManagement.dto.AssetResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssetService {
    AssetResponseDTO createAsset(AssetRequestDTO assetRequestDTO);
    AssetResponseDTO getAssetById(Long id);
    AssetResponseDTO getAssetByAssetId(String assetId);
    Page<AssetResponseDTO> getAllAssets(Pageable pageable);
    AssetResponseDTO updateAsset(Long id, AssetRequestDTO assetRequestDTO);
    void deleteAsset(Long id);
    AssetResponseDTO updateAssetStatus(Long id, String status);
}
