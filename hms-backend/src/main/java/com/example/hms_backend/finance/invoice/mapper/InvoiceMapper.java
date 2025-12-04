package com.example.hms_backend.finance.invoice.mapper;

import com.example.hms_backend.finance.invoice.dto.*;
import com.example.hms_backend.finance.invoice.dto.childDTO.*;
import com.example.hms_backend.finance.invoice.entity.Invoice;
import com.example.hms_backend.finance.invoice.entity.childEntity.*;
import com.example.hms_backend.RoomAndBedManager.entity.Room;
import com.example.hms_backend.laboratory.pathology.entity.PathologyReport;
import com.example.hms_backend.laboratory.pathology.entity.PathologyTestResult;
import com.example.hms_backend.laboratory.radiology.entity.RadiologyScanDetail;
import com.example.hms_backend.patient.entity.Patient;
import com.example.hms_backend.pharmacy.medicine.entity.Medicine;
import com.example.hms_backend.doctor.entity.Doctor;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InvoiceMapper {



    private final EntityManager em;

    public InvoiceMapper(EntityManager em) {
        this.em = em;
    }

    /* =========================
       DTO -> Entity
       ========================= */
    public Invoice toEntity(InvoiceDTO dto) {
        if (dto == null) return null;

        Invoice invoice = new Invoice();
        invoice.setId(dto.getId());
        invoice.setPatientId(dto.getPatientId());
        invoice.setAdmissionDate(dto.getAdmissionDate());
        invoice.setDischargeDate(dto.getDischargeDate());
        invoice.setTotalAmount(dto.getTotalAmount());
        invoice.setPaymentMethod(dto.getPaymentMethod());
        invoice.setPaymentStatus(dto.getPaymentStatus());

        // children: doctors
        if (dto.getDoctors() != null) {
            List<InvoiceDoctor> doctorEntities = dto.getDoctors().stream()
                    .map(d -> toDoctorEntity(d, invoice))
                    .collect(Collectors.toList());
            invoice.setDoctors(doctorEntities);
        } else {
            invoice.setDoctors(Collections.emptyList());
        }

        // medicines
        if (dto.getMedicines() != null) {
            List<InvoiceMedicine> medEntities = dto.getMedicines().stream()
                    .map(m -> toMedicineEntity(m, invoice))
                    .collect(Collectors.toList());
            invoice.setMedicines(medEntities);
        } else {
            invoice.setMedicines(Collections.emptyList());
        }

        // rooms
        if (dto.getRooms() != null) {
            List<InvoiceRoom> roomEntities = dto.getRooms().stream()
                    .map(r -> toRoomEntity(r, invoice))
                    .collect(Collectors.toList());
            invoice.setRooms(roomEntities);
        } else {
            invoice.setRooms(Collections.emptyList());
        }

        // tests
        if (dto.getTests() != null) {
            List<InvoiceTest> testEntities = dto.getTests().stream()
                    .map(t -> toTestEntity(t, invoice))
                    .collect(Collectors.toList());
            invoice.setTests(testEntities);
        } else {
            invoice.setTests(Collections.emptyList());
        }

        // Set bi-directional link (already set in each child factory)
        return invoice;
    }

    private InvoiceDoctor toDoctorEntity(InvoiceDoctorDTO dto, Invoice invoice) {
        InvoiceDoctor e = new InvoiceDoctor();
        e.setId(dto.getDoctorId() == null ? null : dto.getDoctorId()); // optional id
        e.setInvoice(invoice);

        // set doctor reference only by id (no DB hit) using getReference
        if (dto.getDoctorId() != null) {
            Doctor docRef = em.getReference(Doctor.class, dto.getDoctorId());
            e.setDoctor(docRef);
        } else {
            e.setDoctor(null);
        }
        e.setFee(dto.getFee());
        return e;
    }

    private InvoiceMedicine toMedicineEntity(InvoiceMedicineDTO dto, Invoice invoice) {
        InvoiceMedicine e = new InvoiceMedicine();
        e.setId(null); // will be generated
        e.setInvoice(invoice);

        if (dto.getMedicineId() != null) {
            Medicine medRef = em.getReference(Medicine.class, dto.getMedicineId());
            e.setMedicine(medRef);
        } else {
            e.setMedicine(null);
        }

        e.setQty(dto.getQty());
        e.setPricePerUnit(dto.getPricePerUnit());
        e.setTotalPrice(dto.getTotalPrice());
        return e;
    }

    private InvoiceRoom toRoomEntity(InvoiceRoomDTO dto, Invoice invoice) {
        InvoiceRoom e = new InvoiceRoom();
        e.setId(null);
        e.setInvoice(invoice);

        if (dto.getRoomId() != null) {
            Room roomRef = em.getReference(Room.class, dto.getRoomId());
            e.setRoom(roomRef);
        } else {
            e.setRoom(null);
        }

        e.setDays(dto.getDays());
        e.setPricePerDay(dto.getPricePerDay());
        e.setTotalPrice(dto.getTotalPrice());
        return e;
    }

    private InvoiceTest toTestEntity(InvoiceTestDTO dto, Invoice invoice) {
        InvoiceTest e = new InvoiceTest();
        e.setId(null);
        e.setInvoice(invoice);

        // this DTO only has price/qty/total. If you have testId and Test entity, you can use getReference similar to others.
        // e.g. if dto.getTestId() != null -> em.getReference(Test.class, dto.getTestId())
        e.setPrice(dto.getPrice());
        e.setQuantity(dto.getQuantity());
        e.setTotalPrice(dto.getTotalPrice());
        return e;
    }

    /* =========================
       Entity -> DTO
       ========================= */
    public InvoiceDTO toDTO(Invoice entity, Patient patient) {
        if (entity == null) return null;

        InvoiceDTO dto = new InvoiceDTO();
        dto.setId(entity.getId());
        dto.setPatientId(entity.getPatientId());
        dto.setPatientName(patient.getFirstName()+" "+patient.getLastName());
        dto.setHospitalPatientId(patient.getPatientHospitalId());
        dto.setAdmissionDate(entity.getAdmissionDate());
        dto.setDischargeDate(entity.getDischargeDate());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setPaymentStatus(entity.getPaymentStatus());

        // doctors
        if (entity.getDoctors() != null) {
            dto.setDoctors(entity.getDoctors().stream().map(this::toDoctorDTO).collect(Collectors.toList()));
        }

        // medicines
        if (entity.getMedicines() != null) {
            dto.setMedicines(entity.getMedicines().stream().map(this::toMedicineDTO).collect(Collectors.toList()));
        }

        // rooms
        if (entity.getRooms() != null) {
            dto.setRooms(entity.getRooms().stream().map(this::toRoomDTO).collect(Collectors.toList()));
        }

        // tests
        if (entity.getTests() != null) {
            dto.setTests(entity.getTests().stream().map(this::toTestDTO).collect(Collectors.toList()));
        }

        // NOTE: Your Invoice entity currently only stores patientId. If you want patientName/age/contact in DTO,
        // either (A) add those columns to Invoice entity (snapshot), or (B) fetch patient record and set them here.
        // Example (optional):
        // if (entity.getPatientId() != null) {
        //     Patient p = em.find(Patient.class, entity.getPatientId());
        //     if (p != null) {
        //         dto.setPatientName(p.getName());
        //         dto.setPatientAge(p.getAge());
        //         dto.setPatientContact(p.getContact());
        //     }
        // }

        return dto;
    }

    private InvoiceDoctorDTO toDoctorDTO(InvoiceDoctor e) {
        InvoiceDoctorDTO dto = new InvoiceDoctorDTO();
        dto.setDoctorId(e.getDoctor() != null ? getEntityId(e.getDoctor()) : null);
        dto.setDoctorName(e.getDoctor() != null ? safeGetDoctorName(e.getDoctor()) : null);
        dto.setFee(e.getFee());
        return dto;
    }

    private InvoiceMedicineDTO toMedicineDTO(InvoiceMedicine e) {
        InvoiceMedicineDTO dto = new InvoiceMedicineDTO();
        dto.setMedicineId(e.getMedicine() != null ? getEntityId(e.getMedicine()) : null);
        dto.setQty(e.getQty());
        dto.setPricePerUnit(e.getPricePerUnit());
        dto.setTotalPrice(e.getTotalPrice());
        // you can populate medicineName using e.getMedicine().getMedicineName() if that getter exists
        return dto;
    }

    private InvoiceRoomDTO toRoomDTO(InvoiceRoom e) {
        InvoiceRoomDTO dto = new InvoiceRoomDTO();
        dto.setRoomId(e.getRoom() != null ? getEntityId(e.getRoom()) : null);
        dto.setDays(e.getDays());
        dto.setPricePerDay(e.getPricePerDay());
        dto.setTotalPrice(e.getTotalPrice());
        return dto;
    }

    private InvoiceTestDTO toTestDTO(InvoiceTest e) {
        InvoiceTestDTO dto = new InvoiceTestDTO();
        // if you have an actual Test entity reference in InvoiceTest you can set dto.setTestId(...)
        dto.setPrice(e.getPrice());
        dto.setQuantity(e.getQuantity());
        dto.setTotalPrice(e.getTotalPrice());
        return dto;
    }

    // helper to read id from referenced entities that might not have field named "id"
    // This assumes your entities have a standard getter for id or the JPA @Id field name.
    private <T> Long getEntityId(T entity) {
        try {
            // try common getter names
            try {
                return (Long) entity.getClass().getMethod("getId").invoke(entity);
            } catch (NoSuchMethodException ignore) { }
            try {
                return (Long) entity.getClass().getMethod("getMedicineId").invoke(entity);
            } catch (NoSuchMethodException ignore) { }
            try {
                return (Long) entity.getClass().getMethod("getRoomId").invoke(entity);
            } catch (NoSuchMethodException ignore) { }
            try {
                return (Long) entity.getClass().getMethod("getDoctorId").invoke(entity);
            } catch (NoSuchMethodException ignore) { }
        } catch (Exception ex) {
            // fallback null
        }
        return null;
    }

    private String safeGetDoctorName(Object doctor) {
        try {
            return (String) doctor.getClass().getMethod("getDoctorName").invoke(doctor);
        } catch (Exception e) {
            try {
                return (String) doctor.getClass().getMethod("getName").invoke(doctor);
            } catch (Exception ex) {
                return null;
            }
        }
    }

    //helper Mapper functions can be added here

    public InvoiceTestDTO mapPathology(PathologyTestResult p) {
        InvoiceTestDTO dto = new InvoiceTestDTO();
        dto.setTestName(p.getTestName());
        dto.setPrice(p.getCost());
        return dto;
    }

    public InvoiceTestDTO mapRadiology(RadiologyScanDetail r) {
        InvoiceTestDTO dto = new InvoiceTestDTO();
        dto.setTestName(r.getScanName());
        dto.setPrice(r.getCost());
        return dto;
    }
}
