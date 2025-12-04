package com.example.hms_backend.ambulance.entity;

import com.example.hms_backend.audit.Auditable;
import com.example.hms_backend.enums.AmbulanceEnums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "ambulance")
public class Ambulance extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_number", nullable = false, unique = true)
    private String vehicleNumber;

    @Column(name = "ambulance_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AmbulanceEnums.AmbulanceType ambulanceType; // Enum: BASIC, ICU, etc.

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AmbulanceEnums.AmbulanceStatus ambulanceStatus; // Enum: AVAILABLE, ON_DUTY, MAINTENANCE

    @Column(name = "last_maintenance_date")
    private LocalDate lastMaintenanceDate;

}
