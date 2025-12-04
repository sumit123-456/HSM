package com.example.hms_backend.pharmacy.pharmacyPrescription.mapper;

import com.example.hms_backend.pharmacy.pharmacyPrescription.dto.PrescriptionDto;
import com.example.hms_backend.pharmacy.pharmacyPrescription.dto.PrescriptionItemDto;
import com.example.hms_backend.pharmacy.pharmacyPrescription.entity.PharmacyPrescription;
import com.example.hms_backend.pharmacy.pharmacyPrescription.entity.PrescriptionItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PharmacyPrescriptionMapper {
    public PrescriptionDto toDto(PharmacyPrescription e) {
        if (e == null) return null;
        PrescriptionDto d = new PrescriptionDto();
        d.setId(e.getId());
        d.setPrescriptionId(e.getPrescriptionId());
        d.setDoctorName(e.getDoctorName());
        d.setPatientName(e.getPatientName());
        d.setPatientAge(e.getPatientAge());
        d.setDiagnosis(e.getDiagnosis());
        d.setDate(e.getDate());
        d.setNotes(e.getNotes());
        if (e.getItems() != null) d.setItems(e.getItems().stream().map(this::itemToDto).collect(Collectors.toList()));
        return d;
    }

    public PharmacyPrescription toEntity(PrescriptionDto d) {
        if (d == null) return null;

        PharmacyPrescription e = new PharmacyPrescription();
        e.setId(d.getId());
        e.setPrescriptionId(d.getPrescriptionId());
        e.setDoctorName(d.getDoctorName());
        e.setPatientName(d.getPatientName());
        e.setPatientAge(d.getPatientAge());
        e.setDiagnosis(d.getDiagnosis());
        e.setDate(d.getDate());
        e.setNotes(d.getNotes());


        if (d.getItems() != null) {
            List<PrescriptionItem> items = d.getItems()
                    .stream()
                    .map(this::itemToEntity)
                    .collect(Collectors.toList());

            // 🔥 VERY IMPORTANT STEP
            items.forEach(i -> i.setPharmacyPrescription(e));

            e.setItems(items);
        }

        return e;
    }

    private PrescriptionItemDto itemToDto(PrescriptionItem i) {
        PrescriptionItemDto d = new PrescriptionItemDto();
        d.setId(i.getId());
        d.setMedicineName(i.getMedicineName());
        d.setDosage(i.getDosage());
        d.setDuration(i.getDuration());
        d.setFrequency(i.getFrequency());
        d.setInstructions(i.getInstructions());
        return d;
    }

    private PrescriptionItem itemToEntity(PrescriptionItemDto d) {
        PrescriptionItem i = new PrescriptionItem();
        i.setId(d.getId());
        i.setMedicineName(d.getMedicineName());
        i.setDosage(d.getDosage());
        i.setDuration(d.getDuration());
        i.setFrequency(d.getFrequency());
        i.setInstructions(d.getInstructions());
        return i;
    }
}

