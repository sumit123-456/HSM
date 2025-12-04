package com.example.hms_backend.patient.repo;

import com.example.hms_backend.enums.Enums;
import com.example.hms_backend.patient.entity.PatientEmergency;
import com.example.hms_backend.patient.entity.PatientIpd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientIpdRepo extends JpaRepository<PatientIpd,Long> {

    // Fetch latest IPD entry of a patient
    PatientIpd findTopByPatient_IdOrderByIdDesc(Long patientId);


    List<PatientIpd> findTopByPatientId(Long patientId);

    // Check if an active IPD record exists for a patient
    boolean existsByPatient_Id(Long patientId);

    boolean existsByPatientIdAndStatus(Long patientId, Enums.IpdStatus status);


    List<PatientIpd> findByPatient_PatientHospitalIdIgnoreCase(String hospitalId);

    List<PatientIpd> findByPatient_FirstNameContainingIgnoreCaseOrPatient_LastNameContainingIgnoreCase(
            String firstName, String lastName);

}
