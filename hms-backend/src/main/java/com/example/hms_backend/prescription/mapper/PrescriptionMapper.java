package com.example.hms_backend.prescription.mapper;

import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.doctor.service.DoctorService;
import com.example.hms_backend.patient.entity.Patient;
import com.example.hms_backend.patient.service.PatientService;
import com.example.hms_backend.pharmacy.medicine.entity.Medicine;
import com.example.hms_backend.pharmacy.medicine.repo.MedicineRepo;
import com.example.hms_backend.prescription.dto.PrescriptionDTO;
import com.example.hms_backend.prescription.dto.PrescriptionMedicineDTO;
import com.example.hms_backend.prescription.entity.Prescription;
import com.example.hms_backend.prescription.entity.PrescriptionMedicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PrescriptionMapper {

    @Autowired
    private MedicineRepo medicinerepo;


    public Prescription toEntity(PrescriptionDTO dto, Patient patient, Doctor doctor, Department department) {
        Prescription p = new Prescription();
        p.setId(dto.getId());
        p.setPatient(patient);
        p.setDoctor(doctor);
        p.setDepartment(department);
        p.setDiagnosis(dto.getDiagnosis());
        p.setSymptoms(dto.getSymptoms());
        p.setAdditionalNotes(dto.getAdditionalNotes());
        p.setPrescriptionDate(dto.getPrescriptionDate());

        List<PrescriptionMedicine> medList = dto.getMedicines().stream().map(m -> {
            PrescriptionMedicine pm = new PrescriptionMedicine();

            // 🔥 Fetch each medicine separately
            Medicine med = medicinerepo.findById(m.getMedicineId())
                    .orElseThrow(() -> new RuntimeException(
                            "Medicine not found with id: " + m.getMedicineId()));

            pm.setMedicine(med);
            pm.setFrequency(m.getFrequency());
            pm.setDuration(m.getDuration());
            pm.setPrescription(p);
            return pm;
        }).collect(Collectors.toList());

        p.setPrescriptionMedicines(medList);
        return p;
    }

    public PrescriptionDTO toDTO(Prescription p) {
        PrescriptionDTO dto = new PrescriptionDTO();
        dto.setId(p.getId());
        dto.setPatientId(p.getPatient().getId());
        dto.setPatientName(p.getPatient().getFirstName() + " " + p.getPatient().getLastName());
        dto.setDoctorId(p.getDoctor().getId());
        dto.setDoctorName(p.getDoctor().getUserEntity().getUserInfo().getFirstName() + " " + p.getDoctor().getUserEntity().getUserInfo().getLastName());
        dto.setDiagnosis(p.getDiagnosis());
        dto.setDepartmentId(p.getDepartment().getId());
        dto.setDepartmentName(p.getDepartment().getDepartment_name());
        dto.setAdditionalNotes(p.getAdditionalNotes());
        dto.setSymptoms(p.getSymptoms());
        dto.setPrescriptionDate(p.getPrescriptionDate());
        dto.setStatus(p.getStatus().toString());


        List<PrescriptionMedicineDTO> list = p.getPrescriptionMedicines().stream().map(pm -> {
            PrescriptionMedicineDTO m = new PrescriptionMedicineDTO();
            m.setId(pm.getId());
            m.setMedicineName(pm.getMedicine().getMedicineName());
            m.setFrequency(pm.getFrequency());
            m.setDuration(pm.getDuration());
            return m;
        }).collect(Collectors.toList());

        dto.setMedicines(list);
        return dto;
    }
}

