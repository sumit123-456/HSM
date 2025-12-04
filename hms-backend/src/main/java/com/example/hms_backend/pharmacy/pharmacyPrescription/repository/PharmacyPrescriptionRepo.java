package com.example.hms_backend.pharmacy.pharmacyPrescription.repository;

import com.example.hms_backend.pharmacy.pharmacyPrescription.entity.PharmacyPrescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyPrescriptionRepo extends JpaRepository<PharmacyPrescription, Long> {
}

