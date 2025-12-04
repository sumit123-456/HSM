package com.example.hms_backend.authentication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Permission entity
 * Defines fine-grained access like "READ_PATIENT", "WRITE_PRESCRIPTION".
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permissionId;

    @Column(nullable = false, unique = true, length = 100)
    private String permissionName;

    // getters & setters
}