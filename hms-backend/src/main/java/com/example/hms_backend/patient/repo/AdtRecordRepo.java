package com.example.hms_backend.patient.repo;

import com.example.hms_backend.patient.entity.AdtRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdtRecordRepo extends JpaRepository<AdtRecord,Long> {
}
