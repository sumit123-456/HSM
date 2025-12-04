package com.example.hms_backend.laboratory.pathology.service;

import com.example.hms_backend.laboratory.pathology.dto.PathologyReportDTO;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface PathologyService {

    PathologyReportDTO createReport(PathologyReportDTO dto);

    PathologyReportDTO updateReport(Long id, PathologyReportDTO dto);

    PathologyReportDTO getReportById(Long id);

    List<PathologyReportDTO> getAllReports();

    List<PathologyReportDTO> getReportsByPatient(Long patientId);

    void deleteReport(Long id);

    String updateReortStatus(Long reportId, String status);
}
