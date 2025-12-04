package com.example.hms_backend.patient.entity;

import com.example.hms_backend.audit.Auditable;
import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.enums.Enums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "adt_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AdtRecord extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Enumerated(EnumType.STRING)
    private Enums.AdtType type; // ADMITTED, DISCHARGED, TRANSFERRED, DECEASED

    @ManyToOne
    @JoinColumn(name = "transfer_from_department_id")
    private Department transferFromDepartment;

    @ManyToOne
    @JoinColumn(name = "transfer_to_department_id")
    private Department transferToDepartment;

    @Column(name = "admission_date")
    private LocalDate admissionDate;

    @Column(name = "discharged_date")
    private LocalDate dischargedDate;

    @Column(name = "transferred_date")
    private LocalDate transferredDate;

    @Column(name = "deceased_date")
    private LocalDate deceasedDate;

    @Column(name = "reason", length = 500)
    private String reason;

    @Column(name = "notes", length = 500)
    private String notes;


}
