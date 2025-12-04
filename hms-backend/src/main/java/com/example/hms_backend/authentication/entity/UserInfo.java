package com.example.hms_backend.authentication.entity;

import com.example.hms_backend.audit.Auditable;
import com.example.hms_backend.enums.Enums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

/**
 * UserInfo entity
 * Stores personal details of users.
 * Linked to both User and Address entities.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Users_Info")
public class UserInfo extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** One-to-One with User */
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    /** One-to-One with Address */
    @OneToOne
    @JoinColumn(name = "address_id", unique = true)
    private Address address;

    private String firstName;
    private String lastName;
    private String mobileNumber;

    @Lob
    private byte[] profilePic;

    @Enumerated(EnumType.STRING)
    private Enums.Gender gender;

    private LocalDate dob;
    private Integer age;
    private LocalDate joiningDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group", nullable = false)
    private Enums.BloodGroup bloodGroup;

    private String idProofName;

    @Lob
    private byte[] idProofPic;

    @Enumerated(EnumType.STRING)
    private Enums.AvailabilityStatus availabilityStatus;






    // getters & setters
}