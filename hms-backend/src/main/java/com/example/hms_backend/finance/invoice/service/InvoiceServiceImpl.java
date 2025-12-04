package com.example.hms_backend.finance.invoice.service;

import com.example.hms_backend.RoomAndBedManager.entity.BedAssignment;
import com.example.hms_backend.RoomAndBedManager.repo.BedAssignmentRepo;
import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.enums.Enums;
import com.example.hms_backend.exception.ResourceNotFoundException;
import com.example.hms_backend.finance.invoice.dto.InvoiceDTO;
import com.example.hms_backend.finance.invoice.dto.childDTO.InvoiceDoctorDTO;
import com.example.hms_backend.finance.invoice.dto.childDTO.InvoiceMedicineDTO;
import com.example.hms_backend.finance.invoice.dto.childDTO.InvoiceRoomDTO;
import com.example.hms_backend.finance.invoice.entity.Invoice;
import com.example.hms_backend.finance.invoice.mapper.InvoiceMapper;
import com.example.hms_backend.finance.invoice.repo.InvoiceRepo;
import com.example.hms_backend.laboratory.pathology.entity.PathologyReport;
import com.example.hms_backend.laboratory.pathology.entity.PathologyTestResult;
import com.example.hms_backend.laboratory.pathology.repo.PathologyReportRepository;
import com.example.hms_backend.laboratory.radiology.entity.RadiologyReport;
import com.example.hms_backend.laboratory.radiology.entity.RadiologyScanDetail;
import com.example.hms_backend.laboratory.radiology.repo.RadiologyReportRepo;
import com.example.hms_backend.patient.entity.Patient;
import com.example.hms_backend.patient.repo.PatientEmergencyRepo;
import com.example.hms_backend.patient.repo.PatientIpdRepo;
import com.example.hms_backend.patient.repo.PatientOpdRepo;
import com.example.hms_backend.patient.repo.PatientRepo;
import com.example.hms_backend.pharmacy.medicine.entity.Medicine;
import com.example.hms_backend.prescription.repo.PrescriptionRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService{

    @Autowired
    PatientRepo patientRepo;
    @Autowired
    PatientIpdRepo patientIpdRepo;
    @Autowired
    PatientOpdRepo patientOpdRepo;
    @Autowired
    private PatientEmergencyRepo patientEmergencyRepo;
    @Autowired
    private PrescriptionRepo prescriptionRepo;
    @Autowired
    private BedAssignmentRepo bedAssignmentRepo;
    @Autowired
    private PathologyReportRepository pathologyReportRepository;
    @Autowired
    private InvoiceMapper invoiceMapper;
    @Autowired
    private RadiologyReportRepo radiologyReportRepo;
    @Autowired
    InvoiceRepo invoiceRepo;


    @Override
    public InvoiceDTO sendFormData(Long patientId)
    {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));

        InvoiceDTO invoiceDTO = new InvoiceDTO(); // lists already initialized

        // patient details
        invoiceDTO.setPatientId(patient.getId());
        invoiceDTO.setHospitalPatientId(patient.getPatientHospitalId());
        invoiceDTO.setPatientAge(patient.getAge());
        invoiceDTO.setPatientName(patient.getFirstName() + " " + patient.getLastName());
        invoiceDTO.setPatientContact(patient.getContactInfo());

        // doctor details
        Doctor doctor = null;

        if (patientIpdRepo.existsByPatient_Id(patient.getId())) {
            doctor = patientIpdRepo.findTopByPatient_IdOrderByIdDesc(patient.getId()).getDoctor();
        }
        else if (patientOpdRepo.existsByPatient_Id(patient.getId())) {
            doctor = patientOpdRepo.findTopByPatient_IdOrderByIdDesc(patient.getId()).getDoctor();
        }
        else if (patientEmergencyRepo.existsByPatient_Id(patient.getId())) {
            doctor = patientEmergencyRepo.findTopByPatient_IdOrderByIdDesc(patient.getId()).getDoctor();
        }

        if (doctor != null) {
            InvoiceDoctorDTO dto = new InvoiceDoctorDTO();
            dto.setDoctorId(doctor.getId());
            dto.setDoctorName(doctor.getUserEntity().getUserInfo().getFirstName() + " " +
                    doctor.getUserEntity().getUserInfo().getLastName());
            dto.setFee(doctor.getFees());
            invoiceDTO.getDoctors().add(dto);
        }

        // medicines
        List<Medicine> medicines = prescriptionRepo.findByPatientId(patientId).stream().
                filter(md-> md.getStatus() == Enums.PrescriptionStatus.TRANSFERED_TO_PHARMACY)
                .flatMap(p -> p.getPrescriptionMedicines().stream())
                .map(pm -> pm.getMedicine())
                .distinct()
                .collect(Collectors.toList());

        for (Medicine m : medicines) {
            InvoiceMedicineDTO dto = new InvoiceMedicineDTO();
            dto.setMedicineId(m.getId());
            dto.setMedicineName(m.getMedicineName());
            dto.setPricePerUnit(m.getPrice());
            invoiceDTO.getMedicines().add(dto);
        }

        List<BedAssignment> beds = bedAssignmentRepo.findAllByPatientId(patientId);

// Filter only ASSIGNED beds and map them to DTOs
        beds.stream()
                .filter(b -> "ASSIGNED".equals(b.getBedAssignmentStatus())) // FIXED
                .forEach(ba -> {
                    InvoiceRoomDTO dto = new InvoiceRoomDTO();
                    dto.setRoomId(ba.getBed().getRoom().getId());
                    dto.setRoomName(ba.getBed().getRoom().getRoomNumber());
                    dto.setBedId(ba.getBed().getId());
                    dto.setPricePerDay(ba.getBed().getRoom().getRoomType().getPricePerDay().doubleValue());

                    invoiceDTO.getRooms().add(dto);
                });

        // pathology tests
        for (PathologyReport p : pathologyReportRepository.findByPatient_Id(patientId)) {
            if (p.getReportStatus() == Enums.LabReportStatus.COMPLETED) {
                for (PathologyTestResult pt : p.getTestResults()) {
                    invoiceDTO.getTests().add(invoiceMapper.mapPathology(pt));
                }
            }
        }

        // radiology tests
        for (RadiologyReport r : radiologyReportRepo.findByPatient_Id(patientId)) {
            if (r.getReportStatus() == Enums.LabReportStatus.COMPLETED) {
                for (RadiologyScanDetail rd : r.getScanDetails()) {
                    invoiceDTO.getTests().add(invoiceMapper.mapRadiology(rd));
                }
            }
        }

        return invoiceDTO;
    }

    @Override
    public InvoiceDTO createInvoice(InvoiceDTO dto) {
        // convert and save
        Invoice entity = invoiceMapper.toEntity(dto);

        // Because of cascade = ALL on child lists, saving invoice will persist children.
        Invoice saved = invoiceRepo.save(entity);

        Patient patient = patientRepo.findById(saved.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + dto.getPatientId()));

        return invoiceMapper.toDTO(saved,patient);
    }

}
