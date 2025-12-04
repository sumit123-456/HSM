package com.example.hms_backend.pharmacy.medicine.mapper;


import com.example.hms_backend.pharmacy.medicine.dto.MedicineDTO;
import com.example.hms_backend.pharmacy.medicine.entity.Medicine;
import org.springframework.stereotype.Component;

@Component
public class MedicineMapper {

    public MedicineDTO toDTO(Medicine entity) {
        MedicineDTO dto = new MedicineDTO();
        dto.setMedicineId(entity.getId());
        dto.setMedicineName(entity.getMedicineName());
        dto.setManufacturer(entity.getManufacturer());
        dto.setBatchNumber(entity.getBatchNumber());
        dto.setExpiryDate(entity.getExpiryDate());
        dto.setPrice(entity.getPrice());
        return dto;
    }

    public Medicine toEntity(MedicineDTO dto) {
        Medicine med = new Medicine();
        med.setId(dto.getMedicineId());
        med.setMedicineName(dto.getMedicineName());
        med.setManufacturer(dto.getManufacturer());
        med.setBatchNumber(dto.getBatchNumber());
        med.setExpiryDate(dto.getExpiryDate());
        med.setPrice(dto.getPrice());
        return med;
    }
}

