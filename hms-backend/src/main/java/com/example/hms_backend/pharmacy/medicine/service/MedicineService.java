package com.example.hms_backend.pharmacy.medicine.service;

import com.example.hms_backend.pharmacy.medicine.dto.MedicineDTO;
import com.example.hms_backend.pharmacy.medicine.entity.Medicine;

import java.util.List;
import java.util.Map;

public interface MedicineService {

    MedicineDTO saveMedicine(MedicineDTO dto);


    List<MedicineDTO> findAllMedicine();

    Map<Long , String> getMedicineIdAndName();
}
