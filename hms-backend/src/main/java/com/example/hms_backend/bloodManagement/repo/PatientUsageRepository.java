package com.example.hms_backend.bloodManagement.repo;


import com.example.hms_backend.bloodManagement.model.PatientUsage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientUsageRepository extends JpaRepository<PatientUsage, Long>
{
}
