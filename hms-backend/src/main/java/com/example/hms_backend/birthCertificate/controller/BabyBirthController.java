package com.example.hms_backend.birthCertificate.controller;

import com.example.hms_backend.AssetManagement.util.ApiResponse;
import com.example.hms_backend.birthCertificate.dto.BabyBirthRecordDTO;
import com.example.hms_backend.birthCertificate.service.BabyBirthRecordService;
import com.example.hms_backend.patient.dto.PatientIpdViewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/birth-report")
public class BabyBirthController {

    @Autowired
    private BabyBirthRecordService babyBirthRecordService;


    //  Create New Birth Record
    @PostMapping
    public ResponseEntity
            <ApiResponse<BabyBirthRecordDTO>> createBirthRecord(
            @RequestBody BabyBirthRecordDTO dto) {

        BabyBirthRecordDTO savedRecord = babyBirthRecordService.createBabyBirthRecord(dto);

        return ResponseEntity.ok(ApiResponse.success(
                "Baby birth record created successfully",
                savedRecord
        ));
    }

    //  Get All Birth Records
    @GetMapping
    public ResponseEntity<ApiResponse<List<BabyBirthRecordDTO>>> getAllBirthRecords() {

        List<BabyBirthRecordDTO> records = babyBirthRecordService.getAllBirthRecords();

        return ResponseEntity.ok(ApiResponse.success(
                "Baby birth records retrieved successfully",
                records
        ));
    }



    //  update Birth Record
    @PutMapping("/{id}")
    public ResponseEntity
            <ApiResponse<BabyBirthRecordDTO>> updateBirthRecord(@PathVariable Long id,
            @RequestBody BabyBirthRecordDTO dto) {
        System.out.println(id);

        BabyBirthRecordDTO savedRecord = babyBirthRecordService.updateBabyBirthRecord(dto,id);

        return ResponseEntity.ok(ApiResponse.success(
                "Baby birth record updated successfully",
                savedRecord
        ));
    }


    // get available ipd patient for mother id
    @GetMapping("/ipd/mother")
    public ResponseEntity<List<PatientIpdViewDTO>> getAllPatientIpd()
    {
        List<PatientIpdViewDTO> pl = babyBirthRecordService.getAllIpdPatients();

        return ResponseEntity.ok(pl);
    }

}
