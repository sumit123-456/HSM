package com.example.hms_backend.laboratory.pathology.controller;

import com.example.hms_backend.laboratory.pathology.dto.PathologyReportDTO;
import com.example.hms_backend.laboratory.pathology.service.PathologyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/pathology")
@RequiredArgsConstructor
public class PathologyController {

    private final PathologyService pathologyService;

    // CREATE REPORT (PURE JSON)

    @PostMapping("/create")
    public ResponseEntity<PathologyReportDTO> createReport(
            @RequestBody PathologyReportDTO dto
    ) {
        return ResponseEntity.ok(pathologyService.createReport(dto));
    }

    // UPDATE REPORT (EDIT)
    @PutMapping("/update/{id}")
    public ResponseEntity<PathologyReportDTO> updateReport(
            @PathVariable Long id,
            @RequestBody PathologyReportDTO dto
    ) {
        return ResponseEntity.ok(pathologyService.updateReport(id, dto));
    }

    // GET ONE
    @GetMapping("/{id}")
    public PathologyReportDTO getReportById(@PathVariable Long id) {
        return pathologyService.getReportById(id);
    }

    // GET BY PATIENT
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(pathologyService.getReportsByPatient(patientId));
    }

    // GET ALL
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(pathologyService.getAllReports());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        pathologyService.deleteReport(id);
        return "Report Deleted Successfully";
    }

    //update report status
    @PutMapping("/status/{reportId}/{status}")
    public ResponseEntity<String> updateReportStatus(
            @PathVariable Long reportId,
            @PathVariable String status
    ) {
        return ResponseEntity.ok(pathologyService.updateReortStatus(reportId, status));
    }
}
