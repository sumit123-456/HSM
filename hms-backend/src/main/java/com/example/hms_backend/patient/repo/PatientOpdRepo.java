package com.example.hms_backend.patient.repo;

import com.example.hms_backend.patient.entity.PatientEmergency;
import com.example.hms_backend.patient.entity.PatientIpd;
import com.example.hms_backend.patient.entity.PatientOpd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientOpdRepo extends JpaRepository<PatientOpd,Long> {

    // Fetch latest IPD entry of a patient
    PatientOpd findTopByPatient_IdOrderByIdDesc(Long patientId);

    // Check if any OPD records exist for a patient
    boolean existsByPatient_Id(Long patientId);
}
