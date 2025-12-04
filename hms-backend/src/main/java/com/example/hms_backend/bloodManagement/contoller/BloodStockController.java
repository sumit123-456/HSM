package com.example.hms_backend.bloodManagement.contoller;


import com.example.hms_backend.bloodManagement.dtos.StockRequestDTO;
import com.example.hms_backend.bloodManagement.dtos.StockResponseDTO;
import com.example.hms_backend.bloodManagement.dtos.UseUnitRequest;
import com.example.hms_backend.bloodManagement.service.BloodStockService;
import com.example.hms_backend.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/blood-stock")
@Validated
@CrossOrigin("*")
public class BloodStockController {

    private static final Logger logger = LoggerFactory.getLogger(BloodStockController.class);

    private final BloodStockService bloodStockService;

    @Autowired
    public BloodStockController(BloodStockService bloodStockService) {
        this.bloodStockService = bloodStockService;
    }

    @PostMapping
    public ResponseEntity<StockResponseDTO> createStock(@Valid @RequestBody StockRequestDTO stockRequestDTO) {
        logger.info("Received request to create new blood stock");

        StockResponseDTO response = bloodStockService.createStock(stockRequestDTO);

        logger.info("Blood stock created successfully with ID: {}", response.getStockId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockResponseDTO> getStockById(@PathVariable Long id) {
        logger.debug("Received request to get stock by ID: {}", id);

        StockResponseDTO response = bloodStockService.getStockById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/stock-id/{stockId}")
    public ResponseEntity<StockResponseDTO> getStockByStockId(@PathVariable String stockId) {
        logger.debug("Received request to get stock by Stock ID: {}", stockId);

        StockResponseDTO response = bloodStockService.getStockByStockId(stockId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<StockResponseDTO>> getAllStock() {
        logger.debug("Received request to get all blood stock");

        List<StockResponseDTO> response = bloodStockService.getAllStock();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    public ResponseEntity<List<StockResponseDTO>> getActiveStock() {
        logger.debug("Received request to get active blood stock");

        List<StockResponseDTO> response = bloodStockService.getActiveStock();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/blood-group/{bloodGroup}")
    public ResponseEntity<List<StockResponseDTO>> getStockByBloodGroup(@PathVariable String bloodGroup) {
        logger.debug("Received request to get stock by blood group: {}", bloodGroup);

        List<StockResponseDTO> response = bloodStockService.getStockByBloodGroup(bloodGroup);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/blood-group/{bloodGroup}/total-units")
    public ResponseEntity<Integer> getTotalUnitsByBloodGroup(@PathVariable String bloodGroup) {
        logger.debug("Received request to get total units for blood group: {}", bloodGroup);

        Integer totalUnits = bloodStockService.getTotalUnitsByBloodGroup(bloodGroup);

        return ResponseEntity.ok(totalUnits);
    }

    @GetMapping("/expired")
    public ResponseEntity<List<StockResponseDTO>> getExpiredStock() {
        logger.debug("Received request to get expired stock");

        List<StockResponseDTO> response = bloodStockService.getExpiredStock();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/expiring-soon")
    public ResponseEntity<List<StockResponseDTO>> getExpiringSoon(@RequestParam(defaultValue = "7") int days) {
        logger.debug("Received request to get stock expiring within {} days", days);

        List<StockResponseDTO> response = bloodStockService.getExpiringSoon(days);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/use-unit")
    public ResponseEntity<StockResponseDTO> useStockUnit(@PathVariable Long id) {
        logger.info("Received request to use unit from stock ID: {}", id);

        try {
            StockResponseDTO response = bloodStockService.useStockUnitById(id);
            logger.info("Unit used successfully from stock ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            logger.warn("Cannot use unit from stock ID {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (ResourceNotFoundException e) {
            logger.warn("Stock not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/stock-id/{stockId}/use-unit")
    public ResponseEntity<StockResponseDTO> useStockUnitByStockId(@PathVariable String stockId) {
        logger.info("Received request to use unit from stock: {}", stockId);

        try {
            StockResponseDTO response = bloodStockService.useStockUnit(stockId);
            logger.info("Unit used successfully from stock: {}", stockId);
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            logger.warn("Cannot use unit from stock {}: {}", stockId, e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (ResourceNotFoundException e) {
            logger.warn("Stock not found: {}", stockId);
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/{id}/use-unit/details")
    public ResponseEntity<StockResponseDTO> useStockUnitWithDetails(
            @PathVariable Long id,
            @RequestBody UseUnitRequest request) {

        logger.info("Received request to use unit with patient details for stock ID: {}", id);

        try {
            StockResponseDTO response = bloodStockService.useStockUnitWithDetails(id, request);
            return ResponseEntity.ok(response);

        } catch (IllegalStateException e) {
            logger.warn("Cannot use unit: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);

        } catch (ResourceNotFoundException e) {
            logger.warn("Stock not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

}
