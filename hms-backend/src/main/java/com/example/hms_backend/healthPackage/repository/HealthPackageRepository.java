package com.example.hms_backend.healthPackage.repository;


import com.example.hms_backend.healthPackage.entity.HealthPackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthPackageRepository extends JpaRepository<HealthPackage, Long> {

    boolean existsByNameIgnoreCase(String name);

}

