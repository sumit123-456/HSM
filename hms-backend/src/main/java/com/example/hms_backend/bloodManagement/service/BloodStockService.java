package com.example.hms_backend.bloodManagement.service;



import com.example.hms_backend.bloodManagement.dtos.StockRequestDTO;
import com.example.hms_backend.bloodManagement.dtos.StockResponseDTO;
import com.example.hms_backend.bloodManagement.dtos.UseUnitRequest;

import java.util.List;

public interface BloodStockService {

    StockResponseDTO createStock(StockRequestDTO stockRequestDTO);
    StockResponseDTO getStockById(Long id);
    StockResponseDTO useStockUnitWithDetails(Long id, UseUnitRequest request);
    StockResponseDTO getStockByStockId(String stockId);
    List<StockResponseDTO> getAllStock();
    List<StockResponseDTO> getActiveStock();
    List<StockResponseDTO> getStockByBloodGroup(String bloodGroup);
    StockResponseDTO updateStock(Long id, StockRequestDTO stockRequestDTO);
    void deactivateStock(Long id);
    Integer getTotalUnitsByBloodGroup(String bloodGroup);
    List<StockResponseDTO> getExpiredStock();
    List<StockResponseDTO> getExpiringSoon(int days);
    StockResponseDTO useStockUnit(String stockId);
    StockResponseDTO useStockUnitById(Long id);
    StockResponseDTO addUnitsToStock(String bloodGroup, Integer unitsToAdd);
}
