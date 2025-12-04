package com.example.hms_backend.pharmacy.medicine.controller;

import com.example.hms_backend.pharmacy.medicine.dto.MedicineDTO;
import com.example.hms_backend.pharmacy.medicine.repo.MedicineRepo;
import com.example.hms_backend.pharmacy.medicine.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    @Autowired
    MedicineService medicineService;



    @PostMapping
    public ResponseEntity<MedicineDTO> createMedicine(@RequestBody MedicineDTO dto) {
        return ResponseEntity.ok(medicineService.saveMedicine(dto));
    }

    @GetMapping()
    public ResponseEntity<List<MedicineDTO>> findAllMedicineNAmeAndId()
    {
        return ResponseEntity.ok(medicineService.findAllMedicine());
    }

    //send only is and name of medicines

    @GetMapping("/id-name")
    public ResponseEntity<Map<Long, String>> getMedicineIdAndName() {
        Map<Long, String> medicineMap = medicineService.getMedicineIdAndName();
        return ResponseEntity.ok(medicineMap);
    }
}
