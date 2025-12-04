package com.example.hms_backend.RoomAndBedManager.entity;

import com.example.hms_backend.audit.Auditable;
import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.patient.entity.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "bed_assignment")
public class BedAssignment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private  Long id;

    // Bed assigned
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bed_id", nullable = false)
    private Bed bed;

    //  Patient assigned to bed
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    //  User (staff/doctor/nurse) who assigned the bed
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by", nullable = false)
    private UserEntity assignedBy;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    @Column(name = "released_at")
    private LocalDateTime releasedAt;

    @Column (name = "status", nullable = false)
    private String bedAssignmentStatus;


}
