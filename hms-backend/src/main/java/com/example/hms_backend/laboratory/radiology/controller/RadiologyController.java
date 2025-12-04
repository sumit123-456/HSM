package com.example.hms_backend.laboratory.radiology.controller;

import com.example.hms_backend.laboratory.radiology.dto.RadiologyReportDto;
import com.example.hms_backend.laboratory.radiology.dto.RadiologyScanDto;
import com.example.hms_backend.laboratory.radiology.service.RadiologyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/radiology")
@RequiredArgsConstructor
public class RadiologyController {
    private final RadiologyService radiologyService;

    @PostMapping(
            value = "/create",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }
    )
    public ResponseEntity<RadiologyReportDto> createReport(
            @RequestPart("report") String reportJson,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        RadiologyReportDto dto = mapper.readValue(reportJson, RadiologyReportDto.class);

        return ResponseEntity.ok(radiologyService.createReport(dto, files));
    }

    // NEW: Update endpoint (multipart + json)
    @PutMapping(
            value = "/update/{id}",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }
    )
    public ResponseEntity<RadiologyReportDto> updateReport(
            @PathVariable Long id,
            @RequestPart("report") String reportJson,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        RadiologyReportDto dto = mapper.readValue(reportJson, RadiologyReportDto.class);

        return ResponseEntity.ok(radiologyService.updateReport(id, dto, files));
    }

    @GetMapping("/{id}")
    public RadiologyReportDto get(@PathVariable Long id) {
        return radiologyService.getReport(id);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<RadiologyReportDto>> getReportsByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(radiologyService.getReportsByPatient(patientId));
    }

    @GetMapping("/report/{reportId}/scans")
    public ResponseEntity<List<RadiologyScanDto>> getScansByReport(@PathVariable Long reportId) {
        return ResponseEntity.ok(radiologyService.getScansByReport(reportId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RadiologyReportDto>> getAllReports() {
        return ResponseEntity.ok(radiologyService.getAllReports());
    }

    //update report status
    @PutMapping("/status/{reportId}")
    public ResponseEntity<String> updateReportStatus(
            @PathVariable Long reportId,
            @PathVariable String status
    ) {
        return ResponseEntity.ok(radiologyService.updateReportStatus(reportId, status));
    }
}
