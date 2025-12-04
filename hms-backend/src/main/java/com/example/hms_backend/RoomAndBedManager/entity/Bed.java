package com.example.hms_backend.RoomAndBedManager.entity;

import com.example.hms_backend.audit.Auditable;
import com.example.hms_backend.enums.Enums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "beds")
public class Bed extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bed_id")
    private Long id;

    @Column(name = "bed_number")
    private String bedNumber;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private Room room;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Enums.BedStatus status;



}