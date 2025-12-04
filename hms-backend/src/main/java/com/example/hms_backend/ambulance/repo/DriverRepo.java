package com.example.hms_backend.ambulance.repo;

import com.example.hms_backend.ambulance.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepo extends JpaRepository<Driver, Long> {
}
