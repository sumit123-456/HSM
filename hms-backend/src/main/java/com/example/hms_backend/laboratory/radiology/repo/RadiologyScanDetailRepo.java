package com.example.hms_backend.laboratory.radiology.repo;

import com.example.hms_backend.laboratory.radiology.entity.RadiologyScanDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RadiologyScanDetailRepo extends JpaRepository<RadiologyScanDetail, Long> {

    List<RadiologyScanDetail> findByRadiologyReport_Id(Long reportId);
}
