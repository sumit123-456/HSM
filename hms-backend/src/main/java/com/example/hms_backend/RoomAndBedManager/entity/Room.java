package com.example.hms_backend.RoomAndBedManager.entity;

import com.example.hms_backend.audit.Auditable;
import com.example.hms_backend.enums.Enums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "rooms")
public class Room extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private  Long id;

    @Column(name = "room_number",unique = true)
    private String roomNumber;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "room_floorNumber")
    private Integer floorNumber;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Enums.RoomStatus status;



    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "room_Type", nullable = false)
    @JsonIgnore
    private RoomType roomType;

    @OneToMany(mappedBy = "room")
    @JsonIgnore
    List<Bed> beds = new ArrayList<Bed>();

}