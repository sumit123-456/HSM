package com.example.hms_backend.laboratory.pathology.service;

import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.doctor.repo.DoctorRepo;
import com.example.hms_backend.enums.Enums;
import com.example.hms_backend.laboratory.laboratorist.entity.Laboratorist;
import com.example.hms_backend.laboratory.laboratorist.repo.LaboratoristRepo;
import com.example.hms_backend.laboratory.pathology.dto.PathologyReportDTO;
import com.example.hms_backend.laboratory.pathology.entity.PathologyReport;
import com.example.hms_backend.laboratory.pathology.entity.PathologyTestResult;
import com.example.hms_backend.laboratory.pathology.repo.PathologyReportRepository;
import com.example.hms_backend.patient.entity.Patient;
import com.example.hms_backend.patient.repo.PatientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PathologyServiceImpl implements PathologyService {

    private final PathologyReportRepository reportRepo;
    private final PatientRepo patientRepo;
    private final DoctorRepo doctorRepo;
    private final LaboratoristRepo laboratoristRepo;

    // ====================================================
    // CREATE REPORT
    // ====================================================
    @Override
    public PathologyReportDTO createReport(PathologyReportDTO dto) {

        PathologyReport report = new PathologyReport();

        Patient patient = patientRepo.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient Not Found"));

        Doctor doctor = doctorRepo.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor Not Found"));

        Laboratorist lab = laboratoristRepo.findById(dto.getLabTechnicianId())
                .orElseThrow(() -> new RuntimeException("Technician Not Found"));

        report.setPatient(patient);
        report.setDoctor(doctor);
        report.setLabTechnician(lab);
        report.setSampleType(dto.getSampleType());
        report.setCollectedOn(dto.getCollectedOn());
        report.setCollectionTime(dto.getCollectionTime());
        report.setRemarks(dto.getRemarks());
        report.setReportStatus(Enums.LabReportStatus.COMPLETED);

        double total = 0;

        // ADD TEST RESULTS SAFELY
        for (PathologyReportDTO.TestResultDTO t : dto.getTestResults()) {
            PathologyTestResult r = new PathologyTestResult();
            r.setTestName(t.getTestName());
            r.setResultValue(t.getResultValue());
            r.setUnits(t.getUnits());
            r.setReferenceRange(t.getReferenceRange());
            r.setCost(t.getCost());

            report.getTestResults().add(r);   // IMPORTANT FIX
            total += t.getCost();
        }

        report.setTotalCost(total);

        return convertToDto(reportRepo.save(report));
    }


    // ====================================================
    // UPDATE REPORT
    // ====================================================
    @Override
    public PathologyReportDTO updateReport(Long id, PathologyReportDTO dto) {

        PathologyReport report = reportRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Report Not Found"));

        report.setSampleType(dto.getSampleType());
        report.setCollectedOn(dto.getCollectedOn());
        report.setCollectionTime(dto.getCollectionTime());
        report.setRemarks(dto.getRemarks());

        // *************** FIX HERE ***************
        // OLD CHILDREN CLEAR BUT DO NOT REPLACE LIST
        report.getTestResults().clear();


        double total = 0;

        // ADD NEW CHILDREN CORRECTLY
        for (PathologyReportDTO.TestResultDTO t : dto.getTestResults()) {
            PathologyTestResult r = new PathologyTestResult();
            r.setTestName(t.getTestName());
            r.setResultValue(t.getResultValue());
            r.setUnits(t.getUnits());
            r.setReferenceRange(t.getReferenceRange());
            r.setCost(t.getCost());


            report.getTestResults().add(r);  // IMPORTANT FIX
            total += t.getCost();
        }

        report.setTotalCost(total);

        return convertToDto(reportRepo.save(report));
    }


    // ====================================================
    // GET ONE
    // ====================================================
    @Override
    public PathologyReportDTO getReportById(Long id) {
        return convertToDto(reportRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Report Not Found")));
    }

    // ====================================================
    // GET ALL
    // ====================================================
    @Override
    public List<PathologyReportDTO> getAllReports() {
        return reportRepo.findAll().stream().map(this::convertToDto).toList();
    }

    // ====================================================
    // GET BY PATIENT
    // ====================================================
    @Override
    public List<PathologyReportDTO> getReportsByPatient(Long patientId) {
        return reportRepo.findByPatient_Id(patientId).stream()
                .map(this::convertToDto)
                .toList();
    }

    // ====================================================
    // DELETE
    // ====================================================
    @Override
    public void deleteReport(Long id) {
        reportRepo.deleteById(id);
    }

    // ====================================================
    // UPDATE REPORT STATUS
    // ====================================================

    @Override
    public String updateReortStatus(Long reportId, String status) {
        PathologyReport report = reportRepo.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found with id :-" + reportId));
        report.setReportStatus(Enums.LabReportStatus.valueOf(status));
        reportRepo.save(report);
        return "Status updated successfully";
    }


    // ====================================================
    // ENTITY → DTO
    // ====================================================
    private PathologyReportDTO convertToDto(PathologyReport report) {

        PathologyReportDTO dto = new PathologyReportDTO();

        dto.setId(report.getId());
        dto.setPatientId(report.getPatient().getId());
        dto.setDoctorId(report.getDoctor().getId());
        dto.setLabTechnicianId(report.getLabTechnician().getId());
        dto.setDoctorName(report.getDoctor().getUserEntity().getUserInfo().getFirstName()
                + " " + report.getDoctor().getUserEntity().getUserInfo().getLastName());
        dto.setPatientName(report.getPatient().getFirstName()
                + " " + report.getPatient().getLastName());
        dto.setPatientEmail(report.getPatient().getEmail());
        dto.setHospitalPatientId(report.getPatient().getPatientHospitalId());
        dto.setPatientAge(report.getPatient().getAge());
        dto.setPatientGender(report.getPatient().getGender().toString());
        dto.setPatientContact(report.getPatient().getContactInfo());
        dto.setReportStatus(report.getReportStatus().toString());
        dto.setSampleType(report.getSampleType());
        dto.setCollectedOn(report.getCollectedOn());
        dto.setCollectionTime(report.getCollectionTime());
        dto.setRemarks(report.getRemarks());
        dto.setLabTechnicianName(report.getLabTechnician().getUserEntity().getUserInfo().getFirstName()+" "+
                report.getLabTechnician().getUserEntity().getUserInfo().getLastName());
        dto.setTotalCost(report.getTotalCost());

        List<PathologyReportDTO.TestResultDTO> list = new ArrayList<>();

        for (PathologyTestResult r : report.getTestResults()) {
            list.add(new PathologyReportDTO.TestResultDTO(
                    r.getTestName(),
                    r.getResultValue(),
                    r.getUnits(),
                    r.getReferenceRange(),
                    r.getCost()
            ));
        }

        dto.setTestResults(list);

        return dto;
    }
}
