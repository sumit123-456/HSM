package com.example.hms_backend.bloodManagement.service.impl;


import com.example.hms_backend.bloodManagement.dtos.StockRequestDTO;
import com.example.hms_backend.bloodManagement.dtos.StockResponseDTO;
import com.example.hms_backend.bloodManagement.dtos.UseUnitRequest;
import com.example.hms_backend.bloodManagement.model.BloodStock;
import com.example.hms_backend.bloodManagement.model.PatientUsage;
import com.example.hms_backend.bloodManagement.repo.BloodStockRepository;
import com.example.hms_backend.bloodManagement.repo.PatientUsageRepository;
import com.example.hms_backend.bloodManagement.service.BloodStockService;
import com.example.hms_backend.bloodManagement.utils.StockIdGenerator;
import com.example.hms_backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BloodStockServiceImpl implements BloodStockService {

    private static final Logger logger = LoggerFactory.getLogger(BloodStockServiceImpl.class);
    private final BloodStockRepository bloodStockRepository;
    private final StockIdGenerator stockIdGenerator;
    private final ModelMapper modelMapper;
    private final PatientUsageRepository patientUsageRepository;


    @Override
    public StockResponseDTO createStock(StockRequestDTO stockRequestDTO) {
        logger.info("Adding stock for blood group: {}", stockRequestDTO.getBloodGroup());

        Optional<BloodStock> existingStock = bloodStockRepository.findByBloodGroupAndIsActiveTrue(stockRequestDTO.getBloodGroup())
                .stream()
                .findFirst();

        if (existingStock.isPresent()) {
            BloodStock stock = existingStock.get();
            int currentUnits = stock.getUnitsAvailable();
            int newUnits = currentUnits + stockRequestDTO.getUnitsAvailable();

            // Update both units and expiry date
            stock.setUnitsAvailable(newUnits);
            stock.setExpiryDate(stockRequestDTO.getExpiryDate()); // Update with new expiry date

            // Ensure stock remains active
            stock.setIsActive(true);

            BloodStock updatedStock = bloodStockRepository.save(stock);
            logger.info("Updated existing stock {}: {} + {} = {} units, New expiry: {}",
                    stock.getStockId(), currentUnits, stockRequestDTO.getUnitsAvailable(),
                    newUnits, stockRequestDTO.getExpiryDate());

            return modelMapper.map(updatedStock, StockResponseDTO.class);
        } else {
            BloodStock stock = new BloodStock();
            stock.setBloodGroup(stockRequestDTO.getBloodGroup());
            stock.setUnitsAvailable(stockRequestDTO.getUnitsAvailable());
            stock.setExpiryDate(stockRequestDTO.getExpiryDate());
            stock.setIsActive(true);

            String generatedStockId = stockIdGenerator.generateStockId();
            stock.setStockId(generatedStockId);
            logger.info("Created new stock ID: {}", generatedStockId);

            BloodStock savedStock = bloodStockRepository.save(stock);
            logger.info("New stock created successfully with ID: {}", savedStock.getStockId());

            return modelMapper.map(savedStock, StockResponseDTO.class);
        }
    }

    @Override
    public StockResponseDTO getStockById(Long id) {
        logger.debug("Fetching stock by ID: {}", id);
        BloodStock stock = bloodStockRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Stock not found with ID: {}", id);
                    return new ResourceNotFoundException("Stock not found with ID: " + id);
                });
        return modelMapper.map(stock, StockResponseDTO.class);
    }

    @Override
    public StockResponseDTO useStockUnitWithDetails(Long id, UseUnitRequest request) {
        logger.info("Using {} units from stock ID: {}", request.getUnitsUsed(), id);
        BloodStock stock = bloodStockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found with ID: " + id));

        if (stock.getUnitsAvailable() <= 0) {
            throw new IllegalStateException("No units available in this stock");
        }
        if (request.getUnitsUsed() > stock.getUnitsAvailable()) {
            throw new IllegalStateException("Not enough units available");
        }
        if (!stock.getIsActive()) {
            throw new IllegalStateException("Stock is not active");
        }
        if (stock.getExpiryDate().isBefore(LocalDate.now())) {
            throw new IllegalStateException("Stock is expired");
        }
        int remaining = stock.getUnitsAvailable() - request.getUnitsUsed();
        stock.setUnitsAvailable(remaining);

        if (remaining == 0) {
            stock.setIsActive(false);
        }

        BloodStock updatedStock = bloodStockRepository.save(stock);

        PatientUsage usage = new PatientUsage();
        usage.setStockId(String.valueOf(stock.getId()));
        usage.setStockId(stock.getStockId());
        usage.setBloodGroup(stock.getBloodGroup());
        usage.setUnitsUsed(request.getUnitsUsed());
        usage.setPatientName(request.getPatientName());
        usage.setPatientAge(request.getPatientAge());
        usage.setPatientGender(request.getPatientGender());
        usage.setPatientContact(request.getPatientContact());
        usage.setUsedAt(LocalDateTime.now());

        patientUsageRepository.save(usage);
        logger.info("Usage recorded for stock {} by patient {}", stock.getStockId(), request.getPatientName());
        return modelMapper.map(updatedStock, StockResponseDTO.class);
    }

    @Override
    public StockResponseDTO getStockByStockId(String stockId) {
        logger.debug("Fetching stock by Stock ID: {}", stockId);
        BloodStock stock = bloodStockRepository.findByStockId(stockId)
                .orElseThrow(() -> {
                    logger.warn("Stock not found with Stock ID: {}", stockId);
                    return new ResourceNotFoundException("Stock not found with Stock ID: " + stockId);
                });
        return modelMapper.map(stock, StockResponseDTO.class);
    }

    @Override
    public List<StockResponseDTO> getAllStock() {
        logger.debug("Fetching all blood stock");

        return bloodStockRepository.findAll()
                .stream()
                .map(stock -> modelMapper.map(stock, StockResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StockResponseDTO> getActiveStock() {
        logger.debug("Fetching active blood stock");

        return bloodStockRepository.findByIsActiveTrue()
                .stream()
                .map(stock -> modelMapper.map(stock, StockResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StockResponseDTO> getStockByBloodGroup(String bloodGroup) {
        logger.debug("Fetching stock by blood group: {}", bloodGroup);

        return bloodStockRepository.findByBloodGroupAndIsActiveTrue(bloodGroup)
                .stream()
                .map(stock -> modelMapper.map(stock, StockResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public StockResponseDTO updateStock(Long id, StockRequestDTO stockRequestDTO) {
        logger.info("Updating stock with ID: {}", id);

        BloodStock existingStock = bloodStockRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Stock not found with ID: {}", id);
                    return new ResourceNotFoundException("Stock not found with ID: " + id);
                });
        modelMapper.map(stockRequestDTO, existingStock);

        BloodStock updatedStock = bloodStockRepository.save(existingStock);
        logger.info("Stock updated successfully with ID: {}", id);

        return modelMapper.map(updatedStock, StockResponseDTO.class);
    }

    @Override
    public void deactivateStock(Long id) {
        logger.info("Deactivating stock with ID: {}", id);

        BloodStock stock = bloodStockRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Stock not found with ID: {}", id);
                    return new ResourceNotFoundException("Stock not found with ID: " + id);
                });

        stock.setIsActive(false);
        bloodStockRepository.save(stock);
        logger.info("Stock deactivated successfully with ID: {}", id);
    }

    @Override
    public Integer getTotalUnitsByBloodGroup(String bloodGroup) {
        logger.debug("Getting total units for blood group: {}", bloodGroup);

        Integer totalUnits = bloodStockRepository.getTotalUnitsByBloodGroup(bloodGroup);
        return totalUnits != null ? totalUnits : 0;
    }

    @Override
    public List<StockResponseDTO> getExpiredStock() {
        logger.debug("Fetching expired blood stock");

        return bloodStockRepository.findByExpiryDateBeforeAndIsActiveTrue(LocalDate.now())
                .stream()
                .map(stock -> modelMapper.map(stock, StockResponseDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public List<StockResponseDTO> getExpiringSoon(int days) {
        logger.debug("Fetching stock expiring within {} days", days);

        LocalDate thresholdDate = LocalDate.now().plusDays(days);

        return bloodStockRepository.findByIsActiveTrue()
                .stream()
                .filter(stock -> !stock.getExpiryDate().isAfter(thresholdDate))
                .map(stock -> modelMapper.map(stock, StockResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public StockResponseDTO useStockUnit(String stockId) {
        logger.info("Using one unit from stock with ID: {}", stockId);

        // Find stock by stockId (not the database ID)
        BloodStock stock = bloodStockRepository.findByStockId(stockId)
                .orElseThrow(() -> {
                    logger.warn("Stock not found with Stock ID: {}", stockId);
                    return new ResourceNotFoundException("Stock not found with Stock ID: " + stockId);
                });

        // Validate if unit can be used
        if (stock.getUnitsAvailable() <= 0) {
            throw new IllegalStateException("No units available in this stock");
        }

        if (!stock.getIsActive()) {
            throw new IllegalStateException("Stock is not active");
        }

        if (stock.getExpiryDate().isBefore(LocalDate.now())) {
            throw new IllegalStateException("Stock has expired and cannot be used");
        }

        // Decrement units available
        int currentUnits = stock.getUnitsAvailable();
        stock.setUnitsAvailable(currentUnits - 1);

        // If units become 0, you might want to deactivate it (optional)
        if (stock.getUnitsAvailable() == 0) {
            stock.setIsActive(false);
        }

        BloodStock updatedStock = bloodStockRepository.save(stock);
        return modelMapper.map(updatedStock, StockResponseDTO.class);
    }

    @Override
    public StockResponseDTO useStockUnitById(Long id) {
        logger.info("Using one unit from stock with database ID: {}", id);

        BloodStock stock = bloodStockRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Stock not found with ID: {}", id);
                    return new ResourceNotFoundException("Stock not found with ID: " + id);
                });

        // Validate if unit can be used
        if (stock.getUnitsAvailable() <= 0) {
            throw new IllegalStateException("No units available in this stock");
        }

        if (!stock.getIsActive()) {
            throw new IllegalStateException("Stock is not active");
        }

        if (stock.getExpiryDate().isBefore(LocalDate.now())) {
            throw new IllegalStateException("Stock has expired and cannot be used");
        }

        // Decrement units available
        int currentUnits = stock.getUnitsAvailable();
        stock.setUnitsAvailable(currentUnits - 1);

        // If units become 0, deactivate it
        if (stock.getUnitsAvailable() == 0) {
            stock.setIsActive(false);
            logger.info("Stock {} is now empty and has been deactivated", stock.getStockId());
        }

        BloodStock updatedStock = bloodStockRepository.save(stock);
        logger.info("Unit used successfully from stock ID: {}. Remaining units: {}",
                stock.getStockId(), updatedStock.getUnitsAvailable());

        return modelMapper.map(updatedStock, StockResponseDTO.class);
    }

    @Override
    public StockResponseDTO addUnitsToStock(String bloodGroup, Integer unitsToAdd) {
        logger.info("Adding {} units to blood group: {}", unitsToAdd, bloodGroup);

        Optional<BloodStock> existingStock = bloodStockRepository.findByBloodGroupAndIsActiveTrue(bloodGroup)
                .stream()
                .findFirst();

        if (existingStock.isPresent()) {
            BloodStock stock = existingStock.get();
            int currentUnits = stock.getUnitsAvailable();
            int newUnits = currentUnits + unitsToAdd;

            // Update units and set new expiry date (1 month from now)
            stock.setUnitsAvailable(newUnits);
            stock.setExpiryDate(LocalDate.now().plusMonths(1));
            stock.setIsActive(true);

            BloodStock updatedStock = bloodStockRepository.save(stock);
            logger.info("Added {} units to existing stock {}. Total: {} units, New expiry: {}",
                    unitsToAdd, stock.getStockId(), newUnits, stock.getExpiryDate());

            return modelMapper.map(updatedStock, StockResponseDTO.class);
        } else {
            // Create new stock entry
            BloodStock newStock = new BloodStock();
            newStock.setBloodGroup(bloodGroup);
            newStock.setUnitsAvailable(unitsToAdd);
            newStock.setIsActive(true);
            newStock.setExpiryDate(LocalDate.now().plusMonths(1)); // Default 1 month expiry

            String generatedStockId = stockIdGenerator.generateStockId();
            newStock.setStockId(generatedStockId);

            BloodStock savedStock = bloodStockRepository.save(newStock);
            logger.info("Created new stock {} for blood group {} with {} units",
                    generatedStockId, bloodGroup, unitsToAdd);

            return modelMapper.map(savedStock, StockResponseDTO.class);
        }

    }
}
