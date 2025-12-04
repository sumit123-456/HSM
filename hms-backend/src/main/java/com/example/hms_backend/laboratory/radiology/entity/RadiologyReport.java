package com.example.hms_backend.laboratory.radiology.entity;

import com.example.hms_backend.audit.Auditable;
import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.enums.Enums;
import com.example.hms_backend.laboratory.laboratorist.entity.Laboratorist;
import com.example.hms_backend.patient.entity.Patient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "radiology_reports")
public class RadiologyReport extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Patient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Patient patient;

    // Referring doctor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Doctor doctor;

    // Scan performed by technician/radiologist
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laboratorist_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Laboratorist radiologyPerformedBy;

    @Column(columnDefinition = "TEXT")
    private String finalSummary;

    @OneToMany(mappedBy = "radiologyReport", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RadiologyScanDetail> scanDetails = new ArrayList<>();

    private Double totalCost;

    private LocalDate reportDate;

    @Enumerated(EnumType.STRING)
    private Enums.LabReportStatus reportStatus;


}
