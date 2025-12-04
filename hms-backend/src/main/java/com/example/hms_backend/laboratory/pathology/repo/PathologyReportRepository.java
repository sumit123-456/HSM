package com.example.hms_backend.laboratory.pathology.repo;

import com.example.hms_backend.laboratory.pathology.entity.PathologyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PathologyReportRepository extends JpaRepository<PathologyReport, Long> {
    List<PathologyReport> findByPatient_Id(Long patientId);
}
