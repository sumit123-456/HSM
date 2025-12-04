package com.example.hms_backend.patient.repo;

import com.example.hms_backend.patient.entity.Patient;
import com.example.hms_backend.patient.entity.PatientIpd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepo extends JpaRepository<Patient,Long>
{
    // 🔹 Fetch patient by hospital ID
    Optional<Patient> findByPatientHospitalId(String patientHospitalId);

}
