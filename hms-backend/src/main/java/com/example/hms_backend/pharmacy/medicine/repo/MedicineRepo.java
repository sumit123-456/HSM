package com.example.hms_backend.pharmacy.medicine.repo;

import com.example.hms_backend.pharmacy.medicine.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;

public interface MedicineRepo extends JpaRepository<Medicine, Long> {

}
