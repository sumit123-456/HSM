package com.example.hms_backend.laboratory.radiology.service;

import com.example.hms_backend.laboratory.radiology.dto.RadiologyReportDto;
import com.example.hms_backend.laboratory.radiology.dto.RadiologyScanDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RadiologyService {
    RadiologyReportDto createReport(RadiologyReportDto dto , List<MultipartFile> files);
    RadiologyReportDto updateReport(Long id, RadiologyReportDto dto, List<MultipartFile> files);
    RadiologyReportDto getReport(Long id);
    List<RadiologyReportDto> getReportsByPatient(Long patientId);
    List<RadiologyScanDto> getScansByReport(Long reportId);
    List<RadiologyReportDto> getAllReports();
    String updateReportStatus(Long reportId, String status);
}
