package com.example.hms_backend.authentication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Role entity
 * Defines user roles like ADMIN, DOCTOR, USER.
 * Each role can have multiple permissions.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(nullable = false, unique = true, length = 50)
    private String roleName;

    /** Many-to-Many with Permission */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Role_Permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();



    //de-comment it after adding the activate notice

//    /** MAny-to-Many with notice**/
//    @ManyToMany(mappedBy = "targetAudience")
//    private Set<Notice> notices = new HashSet<>();
}
