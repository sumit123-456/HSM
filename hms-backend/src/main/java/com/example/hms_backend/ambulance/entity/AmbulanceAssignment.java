package com.example.hms_backend.ambulance.entity;

import com.example.hms_backend.audit.Auditable;
import com.example.hms_backend.enums.AmbulanceEnums;
import com.example.hms_backend.patient.entity.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "abulance_assignment")
public class AmbulanceAssignment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ambulance_id", nullable = false)
    private Ambulance ambulance;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient; // optional if transporting patient

    @Column(name = "from_location", nullable = false)
    private String fromLocation;

    @Column(name = "to_location", nullable = false)
    private String toLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AmbulanceEnums.AssignmentStatus status; // Scheduled, InProgress, Completed

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;
}
