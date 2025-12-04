package com.example.hms_backend.laboratory.pathology.repo;

import com.example.hms_backend.laboratory.pathology.entity.PathologyTestResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PathologyTestResultRepository extends JpaRepository<PathologyTestResult, Long> {}
