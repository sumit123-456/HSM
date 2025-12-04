package com.example.hms_backend.department.entity;

import com.example.hms_backend.audit.Auditable;
import com.example.hms_backend.doctor.entity.Doctor;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "departments")
public class Department extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy = "department")
    @JsonManagedReference
    private List<Doctor> doctors = new ArrayList<Doctor>();  // List of doctors in this department

    @Column(nullable = false, unique = true)
    private String department_name;


    private String department_head;

    private String description;

}
