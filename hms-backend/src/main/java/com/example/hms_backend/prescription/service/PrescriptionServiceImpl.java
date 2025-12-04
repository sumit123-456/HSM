package com.example.hms_backend.prescription.service;

import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.department.repo.DepartmentRepo;
import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.doctor.repo.DoctorRepo;
import com.example.hms_backend.enums.Enums;
import com.example.hms_backend.patient.entity.Patient;
import com.example.hms_backend.patient.repo.PatientEmergencyRepo;
import com.example.hms_backend.patient.repo.PatientIpdRepo;
import com.example.hms_backend.patient.repo.PatientOpdRepo;
import com.example.hms_backend.patient.repo.PatientRepo;
import com.example.hms_backend.pharmacy.medicine.entity.Medicine;
import com.example.hms_backend.pharmacy.medicine.repo.MedicineRepo;
import com.example.hms_backend.pharmacy.pharmacyPrescription.entity.PharmacyPrescription;
import com.example.hms_backend.pharmacy.pharmacyPrescription.entity.PrescriptionItem;
import com.example.hms_backend.pharmacy.pharmacyPrescription.repository.PharmacyPrescriptionRepo;
import com.example.hms_backend.prescription.dto.AttendingDoctorDTO;
import com.example.hms_backend.prescription.dto.PrescriptionDTO;
import com.example.hms_backend.prescription.dto.PrescriptionMedicineDTO;
import com.example.hms_backend.prescription.entity.Prescription;
import com.example.hms_backend.prescription.entity.PrescriptionMedicine;
import com.example.hms_backend.prescription.mapper.PrescriptionMapper;
import com.example.hms_backend.prescription.repo.PrescriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepo prescriptionRepo;

    @Autowired
    private PrescriptionMapper mapper;
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private DoctorRepo doctorRepo;
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private MedicineRepo medicineRepo;
    @Autowired
    private PatientOpdRepo patientOpdRepo;
    @Autowired
    private PatientIpdRepo patientIpdRepo;
    @Autowired
    private PatientEmergencyRepo patientEmergencyRepo;


    @Autowired
    PharmacyPrescriptionRepo pharmacyPrescriptionRepo;


    public PrescriptionDTO createPrescription(PrescriptionDTO dto) {

        Patient  patient = null;
        Doctor doc = null;
        Department department = null;
        Medicine medicine = null;


        //getting Patient entity from dto and saving it
        if (dto.getPatientId()!=null) {
           patient = patientRepo.findById(dto.getPatientId()).orElseThrow(()-> new RuntimeException("Patient not found with id :-"+dto.getPatientId()));
        }

        //getting Doctor entity from dto and saving it
        if (dto.getDoctorId()!=null) {
             doc = doctorRepo.findById(dto.getDoctorId()).orElseThrow(()-> new RuntimeException("Doctor not found with id :-"+dto.getDoctorId()));
        }

        //getting Department entity from dto and saving it
        if (dto.getDepartmentId()!=null) {
             department = departmentRepo.findById(dto.getDepartmentId()).orElseThrow(()-> new RuntimeException("Department not found with id :-"+dto.getDepartmentId()));
        }


        Prescription entity = mapper.toEntity(dto, patient, doc, department);
        entity.setStatus(Enums.PrescriptionStatus.PENDING);
        Prescription saved = prescriptionRepo.save(entity);
        return mapper.toDTO(saved);
    }

    public PrescriptionDTO getPrescription(Long id) {
        Prescription p = prescriptionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));
        return mapper.toDTO(p);
    }

    public List<PrescriptionDTO> getByPatient(Long patientId) {
        return prescriptionRepo.findByPatientId(patientId)
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public List<PrescriptionDTO> getAllPrescription() {
        return prescriptionRepo.findAll()
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    //prescription status update method
    @Transactional
    @Override
    public String updateStatus(Long prescriptionId, String status) {
         Prescription p =new Prescription();
         p = prescriptionRepo.findById(prescriptionId).orElseThrow(()-> new RuntimeException("Prescription not found with id :-"+prescriptionId));
         p.setStatus(Enums.PrescriptionStatus.valueOf(status));
         prescriptionRepo.save(p);

         //After status update to transfer to pharmacy Saving prescription to pharmacy prescription table
        PharmacyPrescription pp = new PharmacyPrescription();
        pp.setPrescriptionId(p.getId());
        pp.setDoctorName(p.getDoctor().getUserEntity().getUserInfo().getFirstName()+" "+p.getDoctor().getUserEntity().getUserInfo().getLastName());
        pp.setPatientName(p.getPatient().getFirstName()+" "+p.getPatient().getLastName());
        pp.setStatus(Enums.PharmacyPrescriptionStatus.PENDING);
        pp.setPatientAge(p.getPatient().getAge());
        pp.setNotes(p.getAdditionalNotes());
        pp.setDate(p.getPrescriptionDate().toString());
        pp.setDiagnosis(p.getDiagnosis());

           //saving Prescription item
        List<PrescriptionItem> pi = new ArrayList<>();
        for (PrescriptionMedicine pm : p.getPrescriptionMedicines()) {
            PrescriptionItem item = new PrescriptionItem();
            item.setMedicineName(pm.getMedicine().getMedicineName());
            item.setFrequency(pm.getFrequency());
            item.setDuration(pm.getDuration());
            item.setPharmacyPrescription(pp); // set the parent reference
            pi.add(item);
        }
        pp.setItems(pi);

        pharmacyPrescriptionRepo.save(pp);

         return "Prescription status updated successfully";
    }

    // get attending doctor details by patient id
    @Override
    public AttendingDoctorDTO getAttendingDoctorDetails(Long patientId) {

        return null;


    }

    public void deletePrescription(Long id) {
        prescriptionRepo.deleteById(id);
    }

    public PrescriptionDTO updatePrescription(Long id, PrescriptionDTO dto) {

        Prescription existing = prescriptionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        if (existing.getStatus()== Enums.PrescriptionStatus.COMPLETED) {
            throw new RuntimeException("Completed prescriptions cannot be modified");
        }

        // Update simple fields
        existing.setSymptoms(dto.getSymptoms());
        existing.setDiagnosis(dto.getDiagnosis());
        existing.setAdditionalNotes(dto.getAdditionalNotes());
        existing.setPrescriptionDate(dto.getPrescriptionDate());

        // Update patient, doctor, department
        if (dto.getPatientId() != null) {
            existing.setPatient(patientRepo.findById(dto.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found")));
        }

        if (dto.getDoctorId() != null) {
            existing.setDoctor(doctorRepo.findById(dto.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found")));
        }

        if (dto.getDepartmentId() != null) {
            existing.setDepartment(departmentRepo.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found")));
        }

        // ❗ FIX: DO NOT REPLACE THE LIST
        List<PrescriptionMedicine> existingMeds = existing.getPrescriptionMedicines();

        // Step 1: Clear existing list but keep the same object
        existingMeds.clear();

        // Step 2: Add all new medicines to the SAME list
        for (PrescriptionMedicineDTO m : dto.getMedicines()) {
            PrescriptionMedicine pm = new PrescriptionMedicine();

            Medicine med = medicineRepo.findById(m.getMedicineId())
                    .orElseThrow(() -> new RuntimeException("Medicine not found"));
            pm.setMedicine(med);
            pm.setFrequency(m.getFrequency());
            pm.setDuration(m.getDuration());
            pm.setPrescription(existing);
            existingMeds.add(pm);
        }

        // Hibernate will detect deletions & insertions automatically
        Prescription updated = prescriptionRepo.save(existing);
        return mapper.toDTO(updated);
    }

}
