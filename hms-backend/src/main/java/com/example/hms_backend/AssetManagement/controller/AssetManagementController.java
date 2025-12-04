package com.example.hms_backend.AssetManagement.controller;


import com.example.hms_backend.AssetManagement.dto.AssetRequestDTO;
import com.example.hms_backend.AssetManagement.dto.AssetResponseDTO;
import com.example.hms_backend.AssetManagement.service.AssetService;
import com.example.hms_backend.AssetManagement.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor

public class AssetManagementController {

    private final AssetService assetService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<AssetResponseDTO>> createAsset(
            @Valid @RequestBody AssetRequestDTO assetRequestDTO) {
        log.info("Received request to create asset");

        AssetResponseDTO response = assetService.createAsset(assetRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Asset created successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AssetResponseDTO>> getAssetById(@PathVariable Long id) {
        log.info("Received request to get asset by id: {}", id);
        AssetResponseDTO response = assetService.getAssetById(id);
        return ResponseEntity.ok(ApiResponse.success("Asset retrieved successfully", response));
    }

    @GetMapping("/asset-id/{assetId}")
    public ResponseEntity<ApiResponse<AssetResponseDTO>> getAssetByAssetId(@PathVariable String assetId) {
        log.info("Received request to get asset by assetId: {}", assetId);
        AssetResponseDTO response = assetService.getAssetByAssetId(assetId);
        return ResponseEntity.ok(ApiResponse.success("Asset retrieved successfully", response));
    }

    @GetMapping("/view")
    public ResponseEntity<ApiResponse<Page<AssetResponseDTO>>> getAllAssets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        log.info("Received request to get all assets - page: {}, size: {}", page, size);

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AssetResponseDTO> response = assetService.getAllAssets(pageable);

        return ResponseEntity.ok(ApiResponse.success("Assets retrieved successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AssetResponseDTO>> updateAsset(
            @PathVariable Long id,
            @Valid @RequestBody AssetRequestDTO assetRequestDTO) {

        log.info("Received request to update asset with id: {}", id);
        AssetResponseDTO response = assetService.updateAsset(id, assetRequestDTO);
        return ResponseEntity.ok(ApiResponse.success("Asset updated successfully", response));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<AssetResponseDTO>> updateAssetStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        log.info("Received request to update asset status with id: {} to {}", id, status);
        AssetResponseDTO response = assetService.updateAssetStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Asset status updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAsset(@PathVariable Long id) {
        log.info("Received request to delete asset with id: {}", id);
        assetService.deleteAsset(id);
        return ResponseEntity.ok(ApiResponse.success("Asset deleted successfully", null));
    }
}
