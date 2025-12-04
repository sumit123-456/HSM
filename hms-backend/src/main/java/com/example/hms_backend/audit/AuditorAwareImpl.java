package com.example.hms_backend.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

//AuditorAwareImpl is automatically used by Spring Data JPA to fetch the current auditor (user) whenever an entity is created or updated.
//triggered by the @CreatedBy and @LastModifiedBy annotations in your Auditable base class.
@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        //principal instanceof UserDetails: This checks if the principal object is an instance of the UserDetails interface.
        // The UserDetails interface is a core interface in Spring Security that represents user information.

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return Optional.of(((UserDetails) principal).getUsername());
        } else {
            return Optional.of(principal.toString());
        }
    }

}
