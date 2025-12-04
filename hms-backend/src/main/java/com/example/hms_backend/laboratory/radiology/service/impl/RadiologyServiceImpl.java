package com.example.hms_backend.laboratory.radiology.service.impl;

import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.doctor.repo.DoctorRepo;
import com.example.hms_backend.enums.Enums;
import com.example.hms_backend.exception.ResourceNotFoundException;
import com.example.hms_backend.laboratory.laboratorist.entity.Laboratorist;
import com.example.hms_backend.laboratory.laboratorist.repo.LaboratoristRepo;
import com.example.hms_backend.laboratory.pathology.entity.PathologyReport;
import com.example.hms_backend.patient.entity.Patient;
import com.example.hms_backend.patient.repo.PatientRepo;
import com.example.hms_backend.laboratory.radiology.dto.RadiologyReportDto;
import com.example.hms_backend.laboratory.radiology.dto.RadiologyScanDto;
import com.example.hms_backend.laboratory.radiology.entity.RadiologyReport;
import com.example.hms_backend.laboratory.radiology.entity.RadiologyScanDetail;
import com.example.hms_backend.laboratory.radiology.repo.RadiologyReportRepo;
import com.example.hms_backend.laboratory.radiology.service.RadiologyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
public class RadiologyServiceImpl implements RadiologyService {

    private final RadiologyReportRepo reportRepo;
    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;
    private final LaboratoristRepo laboratoristRepo;

    @Override
    public RadiologyReportDto createReport(RadiologyReportDto dto, List<MultipartFile> files) {

        Laboratorist radiologist = null;
        Patient patient  = null;
        Doctor doctor = null;

        if (dto.getPatientId() != null) {
            patient = patientRepo.findById(dto.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found"));
        }

        if (dto.getDoctorId() != null) {
            doctor = doctorRepo.findById(dto.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
        }

        if (dto.getRadiologyPerformedById() != null) {
            radiologist = laboratoristRepo.findById(dto.getRadiologyPerformedById())
                    .orElseThrow(() -> new RuntimeException("Radiologist not found"));
        }

        RadiologyReport report = new RadiologyReport();
        report.setPatient(patient);
        report.setDoctor(doctor);
        report.setRadiologyPerformedBy(radiologist);
        report.setFinalSummary(dto.getFinalSummary());
        report.setReportStatus(Enums.LabReportStatus.COMPLETED);
        report.setReportDate((dto.getReportDate()));


        List<RadiologyScanDetail> scanList = new ArrayList<>();
        double total = 0;

        List<RadiologyScanDto> dtoScans = dto.getScanDetails() != null ? dto.getScanDetails() : new ArrayList<>();
        int countFiles = files != null ? files.size() : 0;

        for (int i = 0; i < dtoScans.size(); i++) {
            RadiologyScanDto scan = dtoScans.get(i);
            RadiologyScanDetail s = new RadiologyScanDetail();
            s.setScanName(scan.getScanName());
            s.setFindings(scan.getFindings());
            s.setImpression(scan.getImpression());
            s.setCost(scan.getCost());

            // attach file if provided
            if (files != null && i < countFiles && files.get(i) != null) {
                try {
                    s.setScanFile(files.get(i).getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (scan.getScanFile() != null) {
                s.setScanFile(scan.getScanFile());
            }

            s.setRadiologyReport(report);
            scanList.add(s);
            total += scan.getCost() != null ? scan.getCost() : 0;
        }

        report.setScanDetails(scanList);
        report.setTotalCost(total);

        RadiologyReport saved = reportRepo.save(report);
        return convertToDTO(saved);
    }

    @Override
    public RadiologyReportDto updateReport(Long id, RadiologyReportDto dto, List<MultipartFile> files) {

        RadiologyReport report = reportRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Radiology report not found"));

        // Update patient, doctor, technician if passed
        if (dto.getPatientId() != null) {
            Patient patient = patientRepo.findById(dto.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found"));
            report.setPatient(patient);
        }

        if (dto.getDoctorId() != null) {
            Doctor doctor = doctorRepo.findById(dto.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            report.setDoctor(doctor);
        }

        if (dto.getRadiologyPerformedById() != null) {
            Laboratorist radiologist = laboratoristRepo.findById(dto.getRadiologyPerformedById())
                    .orElseThrow(() -> new RuntimeException("Radiologist not found"));
            report.setRadiologyPerformedBy(radiologist);
        }

        // Update simple fields
        report.setFinalSummary(dto.getFinalSummary());
        report.setReportDate(dto.getReportDate());


        // Prepare new scan list
        List<RadiologyScanDto> dtoScans = dto.getScanDetails() != null ? dto.getScanDetails() : new ArrayList<>();
        int countFiles = files != null ? files.size() : 0;

        List<RadiologyScanDetail> newScanList = new ArrayList<>();
        double total = 0;

        for (int i = 0; i < dtoScans.size(); i++) {
            RadiologyScanDto scan = dtoScans.get(i);
            RadiologyScanDetail s = new RadiologyScanDetail();
            s.setScanName(scan.getScanName());
            s.setFindings(scan.getFindings());
            s.setImpression(scan.getImpression());
            s.setCost(scan.getCost());

            // if new file provided for this index, use it; else keep provided byte[] or null
            if (files != null && i < countFiles && files.get(i) != null) {
                try {
                    s.setScanFile(files.get(i).getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (scan.getScanFile() != null) {
                s.setScanFile(scan.getScanFile());
            }

            s.setRadiologyReport(report);
            newScanList.add(s);
            total += scan.getCost() != null ? scan.getCost() : 0;
        }

        // IMPORTANT: modify existing collection reference to avoid "orphan deletion" issues
        if (report.getScanDetails() == null) {
            report.setScanDetails(new ArrayList<>());
        }
        // clear existing elements (this keeps same collection reference)
        report.getScanDetails().clear();
        // add all new elements
        report.getScanDetails().addAll(newScanList);

        report.setTotalCost(total);

        RadiologyReport saved = reportRepo.save(report);
        return convertToDTO(saved);
    }


    // ====================================================
    // UPDATE REPORT STATUS
    // ====================================================

    @Override
    public String updateReportStatus(Long reportId, String status) {
        RadiologyReport report = reportRepo.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found with id :-" + reportId));
        report.setReportStatus(Enums.LabReportStatus.valueOf(status));
        reportRepo.save(report);
        return "Status updated successfully";
    }

    // ====================================================
    // Get report By Id
    // ====================================================

    @Override
    public RadiologyReportDto getReport(Long id) {

        RadiologyReport report = reportRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Radiology report not found"));

        return convertToDTO(report);
    }

    // ============================
    // ENTITY → DTO
    // ============================
    private RadiologyReportDto convertToDTO(RadiologyReport report) {

        RadiologyReportDto dto = new RadiologyReportDto();

        dto.setId(report.getId());

        if (report.getPatient() != null) {
            dto.setPatientId(report.getPatient().getId());
        }
        if (report.getDoctor() != null) {
            dto.setDoctorId(report.getDoctor().getId());
        }

        // NULL SAFE (MOST IMPORTANT FIX)
        if (report.getRadiologyPerformedBy() != null) {
            dto.setRadiologyPerformedById(report.getRadiologyPerformedBy().getId());
        }

        dto.setFinalSummary(report.getFinalSummary());
        dto.setTotalCost(report.getTotalCost());
        dto.setReportStatus(report.getReportStatus() != null ? report.getReportStatus().toString() : null);
        dto.setPatientName(report.getPatient().getFirstName()+" "+report.getPatient().getLastName());
        dto.setHospitalPatientId(report.getPatient().getPatientHospitalId());
        dto.setPatientAge(report.getPatient().getAge());
        dto.setReportStatus(report.getReportStatus() != null ? report.getReportStatus().toString() : null);
        dto.setPatientEmail(report.getPatient().getEmail());
        dto.setPatientGender(report.getPatient().getGender().toString());
        dto.setRadiologyTechnicianName(report.getRadiologyPerformedBy().getUserEntity().getUserInfo().getFirstName()+" "
                +report.getRadiologyPerformedBy().getUserEntity().getUserInfo().getLastName());
        dto.setPatientContact(report.getPatient().getContactInfo());
        dto.setReportDate(report.getReportDate());
        dto.setDoctorName(report.getDoctor().getUserEntity().getUserInfo().getFirstName()+" "+
                report.getDoctor().getUserEntity().getUserInfo().getLastName());
        dto.setScanDetails(new ArrayList<>());

        if (report.getScanDetails() != null) {
            for (RadiologyScanDetail scan : report.getScanDetails()) {
                RadiologyScanDto s = new RadiologyScanDto();
                s.setId(scan.getId());
                if (scan.getRadiologyReport() != null) {
                    s.setReportId(scan.getRadiologyReport().getId());
                } else {
                    s.setReportId(report.getId());
                }
                s.setScanName(scan.getScanName());
                s.setFindings(scan.getFindings());
                s.setImpression(scan.getImpression());
                s.setCost(scan.getCost());
                s.setScanFile(scan.getScanFile());

                dto.getScanDetails().add(s);
            }
        }

        return dto;
    }

    //get report according to patient id
    @Override
    public List<RadiologyReportDto> getReportsByPatient(Long patientId) {
        List<RadiologyReport> reports = reportRepo.findByPatient_Id(patientId);
        return reports.stream()
                .map(this::convertToDTO)
                .toList();
    }

    //get scans by report id
    @Override
    public List<RadiologyScanDto> getScansByReport(Long reportId) {

        RadiologyReport report = reportRepo.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found"));

        List<RadiologyScanDetail> scans = report.getScanDetails();

        return scans.stream().map(scan -> {
            RadiologyScanDto dto = new RadiologyScanDto();
            dto.setId(scan.getId());
            dto.setReportId(reportId);
            dto.setScanName(scan.getScanName());
            dto.setFindings(scan.getFindings());
            dto.setImpression(scan.getImpression());
            dto.setCost(scan.getCost());
            dto.setScanFile(scan.getScanFile());
            return dto;
        }).toList();
    }

    @Override
    public List<RadiologyReportDto> getAllReports() {
        return reportRepo.findAll()
                .stream().map(
                        this::convertToDTO
                ).toList();
    }
}
