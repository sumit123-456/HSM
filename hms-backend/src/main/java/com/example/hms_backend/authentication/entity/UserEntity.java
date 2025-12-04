package com.example.hms_backend.authentication.entity;

import com.example.hms_backend.audit.Auditable;
import com.example.hms_backend.doctor.entity.Doctor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;

/**
 * Users entity
 * Stores authentication details like username, email, password.
 * Each User can have multiple Roles and one UserInfo.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "Users")
public class UserEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    private Boolean enabled = true;



    /** Many-to-Many with Role */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "User_Role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    /** One-to-One with UserInfo */
    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL,optional = true)
    private UserInfo userInfo;

    // getters & setters

    public boolean isEnabled() {   // <-- Getter Spring Security expects
        return enabled;
    }
}