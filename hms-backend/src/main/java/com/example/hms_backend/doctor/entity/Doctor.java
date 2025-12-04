package com.example.hms_backend.doctor.entity;

import com.example.hms_backend.audit.Auditable;
import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.enums.Enums;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "doctors")
public class Doctor extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "department_id")
    private Department department;

    private String specialization;

    private String experience;

    private String qualifications;

    private String licenseNumber;

    private double fees;



    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity userEntity;

}

