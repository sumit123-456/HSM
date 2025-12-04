package com.example.hms_backend.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")//enables JPA auditing in your application.
public class JpaConfig {
    @Bean
    @Primary
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }
}