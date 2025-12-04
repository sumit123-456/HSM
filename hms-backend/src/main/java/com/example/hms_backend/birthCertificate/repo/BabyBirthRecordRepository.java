package com.example.hms_backend.birthCertificate.repo;

import com.example.hms_backend.birthCertificate.entity.BabyBirthRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BabyBirthRecordRepository extends JpaRepository<BabyBirthRecord, Long> {
}
