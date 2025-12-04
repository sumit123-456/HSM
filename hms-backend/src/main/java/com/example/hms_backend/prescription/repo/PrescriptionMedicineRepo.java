package com.example.hms_backend.prescription.repo;

import com.example.hms_backend.prescription.entity.PrescriptionMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionMedicineRepo extends JpaRepository<PrescriptionMedicine, Long> {
}
