package com.example.hms_backend.authentication.repo;

import com.example.hms_backend.authentication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
    Optional<Role> findById(Long roleId);

}
