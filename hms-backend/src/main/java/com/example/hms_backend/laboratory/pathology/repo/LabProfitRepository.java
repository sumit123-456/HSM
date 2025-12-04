package com.example.hms_backend.laboratory.pathology.repo;

import com.example.hms_backend.laboratory.pathology.entity.LabProfit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LabProfitRepository extends JpaRepository<LabProfit, Long> {
    Optional<LabProfit> findByReportId(Long reportId);
}
