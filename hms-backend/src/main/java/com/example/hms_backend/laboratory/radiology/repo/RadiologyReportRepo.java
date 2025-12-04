package com.example.hms_backend.laboratory.radiology.repo;

import com.example.hms_backend.laboratory.radiology.entity.RadiologyReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RadiologyReportRepo extends JpaRepository<RadiologyReport, Long> {

    List<RadiologyReport> findByPatient_Id(Long patientId);
}
