package com.example.hms_backend.prescription.repo;

import com.example.hms_backend.prescription.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepo extends JpaRepository<Prescription, Long> {
    List<Prescription> findByPatientId(Long patientId);
}

