package com.example.hms_backend.RoomAndBedManager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "room_type")
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "room_type_name")
    private String roomTypeName; //icu,general-ward,private etc

    @Column(name = "description")
    private String description;

    @Column(name = "price_per_day", precision = 10, scale = 2, nullable = false)
    private BigDecimal pricePerDay;

    @OneToMany(mappedBy = "roomType")
    @JsonIgnore
    List<Room> rooms =  new ArrayList<>();







}
