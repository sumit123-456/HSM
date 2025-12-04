package com.example.hms_backend.prescription.service;

import com.example.hms_backend.prescription.dto.AttendingDoctorDTO;
import com.example.hms_backend.prescription.dto.PrescriptionDTO;

import java.util.List;

public interface PrescriptionService {

    PrescriptionDTO createPrescription(PrescriptionDTO dto);

    PrescriptionDTO getPrescription(Long id);

    List<PrescriptionDTO> getByPatient(Long patientId);

    void deletePrescription(Long id);

    PrescriptionDTO updatePrescription(Long id, PrescriptionDTO dto);

    public List<PrescriptionDTO> getAllPrescription();

    String updateStatus(Long prescriptionId, String status);

    AttendingDoctorDTO getAttendingDoctorDetails(Long patientId);
}
