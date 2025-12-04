package com.example.hms_backend.patient.entity;

import com.example.hms_backend.audit.Auditable;
import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.enums.Enums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;


@Entity
@Table(name = "patients_opd")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PatientOpd extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

//     Parent Visit (nullable for first visit)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_visit_id")
    private PatientOpd parentVisit;

    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "visit_type", nullable = false)
    private Enums.VisitSequenceType visitSequenceType;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Enums.VisitStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referred_to_department")
    private Department referredToDepartment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referred_to_doctor")
    private Doctor referredToDoctor;

    @Column(name = "reason")
    private String reason;

    @Column(name = "symptoms", columnDefinition = "TEXT")
    private String symptoms;

}
