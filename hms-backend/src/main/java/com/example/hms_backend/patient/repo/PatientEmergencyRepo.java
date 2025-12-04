package com.example.hms_backend.patient.repo;

import com.example.hms_backend.patient.entity.PatientEmergency;
import com.example.hms_backend.patient.entity.PatientIpd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientEmergencyRepo extends JpaRepository<PatientEmergency, Long> {

    // Fetch latest IPD entry of a patient
    PatientEmergency findTopByPatient_IdOrderByIdDesc(Long patientId);

    // Check if an active emergency admission exists for a patient
    boolean existsByPatient_Id(Long patientId);


}
