package com.example.hms_backend.pharmacy.medicine.service;

import com.example.hms_backend.pharmacy.medicine.dto.MedicineDTO;
import com.example.hms_backend.pharmacy.medicine.entity.Medicine;
import com.example.hms_backend.pharmacy.medicine.mapper.MedicineMapper;
import com.example.hms_backend.pharmacy.medicine.repo.MedicineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    MedicineRepo medicineRepo;

    @Autowired
    MedicineMapper medicineMapper;


    @Override
    public MedicineDTO saveMedicine(MedicineDTO dto) {
        Medicine entity = medicineMapper.toEntity(dto);
        Medicine saved = medicineRepo.save(entity);
        return medicineMapper.toDTO(saved);
    }

    @Override
    public List<MedicineDTO> findAllMedicine() {
       return medicineRepo.findAll().stream().map(m -> medicineMapper.toDTO(m)).collect(Collectors.toList());
    }

    @Override
    public Map<Long, String> getMedicineIdAndName() {
        Map<Long, String> medicineMap = new HashMap<>();
        List<Medicine> medicines = medicineRepo.findAll();
        for (Medicine med : medicines) {
            medicineMap.put(med.getId(), med.getMedicineName());
        }
        return medicineMap;
    }


}
