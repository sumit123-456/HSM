package com.example.hms_backend.DeathCertificate.repo;


import com.example.hms_backend.DeathCertificate.entity.DeathCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeathCertificateRepo extends JpaRepository<DeathCertificate, Long> {
    @Query("SELECT dc.patient.id FROM DeathCertificate dc")
    List<Long> findAllDeadPatientIds();
}