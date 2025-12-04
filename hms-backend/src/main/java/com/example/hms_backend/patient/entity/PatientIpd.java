package com.example.hms_backend.patient.entity;

import com.example.hms_backend.birthCertificate.entity.BabyBirthRecord;
import com.example.hms_backend.audit.Auditable;
import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.enums.ChronicConditionsEnum;
import com.example.hms_backend.enums.Enums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "patients_ipd")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PatientIpd extends Auditable {

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
    private PatientIpd parentVisit;

    private boolean readmissionFlag; // true = readmission, false = first admission

    @Column(length = 500)
    private String allergies; // free text





//    @ElementCollection
//    @CollectionTable(
//            name = "ipd_chronic_conditions",
//            joinColumns = @JoinColumn(name = "ipd_id", referencedColumnName = "id")
//    )
//    @Enumerated(EnumType.STRING) // store as VARCHAR
//    @Column(name = "condition")
//    private Set<ChronicConditionsEnum> chronicConditions = new HashSet<>();


    @ElementCollection
    @CollectionTable(
            name = "ipd_chronic_conditions",
            joinColumns = @JoinColumn(name = "ipd_id", referencedColumnName = "id")
    )
    @Enumerated(EnumType.STRING) // store as VARCHAR
    @Column(name = "chronic_condition") // avoid reserved keyword
    private Set<ChronicConditionsEnum> chronicConditions = new HashSet<>();



    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Enums.IpdStatus status; // ADMITTED, DISCHARGED, DECEASED, TRANSFERRED

    @OneToMany(mappedBy = "motherPatient", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<BabyBirthRecord> babyBirthRecords = new ArrayList<>();

}


//    @ElementCollection(targetClass = ChronicConditionsEnum.class)
//    @Enumerated(EnumType.STRING)
//    @CollectionTable(name = "ipd_chronic_conditions", joinColumns = @JoinColumn(name = "ipd_id", referencedColumnName = "id"))
//    @Column(name = "condition")
//    private Set<ChronicConditionsEnum> chronicConditions = new HashSet<>();
