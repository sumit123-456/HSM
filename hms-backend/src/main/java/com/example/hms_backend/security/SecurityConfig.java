package com.example.hms_backend.security;

import com.example.hms_backend.authentication.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)  //@Preauthorized will stop access of unathorized user
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtFilter jwtFilter;


    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtFilter jwtFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/data/states","/api/register/form-data",
                                "/api/auth/**", // login, register
                                "/api/forgot-password/**",
                                "/css/**", "/javascript/**", "/images/**", "/vendors/**","/api/users/**","/api/department/**",
                                "/api/assets/**","/api/health-package/**","/api/birth-report/**","/api/death-certificate/**",
                                "/api/doctor-schedule/**","/api/ambulance/**","/api/driver/**","/api/prescriptions/**","/api/patients/**","/api/patients/all","/api/appointment/**","/api/notices/**","/api/attachment/**","/api/beds/**",
                                "/api/doctor/**","/api/v1/**","/insurance/**","/api/invoice/**","/api/pathology/**"
                        ).permitAll()
                        .requestMatchers("/ping","/api/rooms/**").permitAll()
                        .requestMatchers("/super_admin/**").hasRole("SUPER_ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/doctor/**").hasRole("DOCTOR")
                        .requestMatchers("/headnurse/**").hasRole("HEADNURSE")
                        .requestMatchers("/pharmacist/**").hasRole("PHARMACIST")
                        .requestMatchers("/accountant/**").hasRole("ACCOUNTANT")
                        .requestMatchers("/hr/**").hasRole("HR")
                        .requestMatchers("/insurance/**").hasRole("INSURANCE")
                        .requestMatchers("/laboratorist/**").hasRole("LABORATORIST")
                        .requestMatchers("/receptionist/**").hasRole("RECEPTIONIST")

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173","http://localhost:5175",
                "http://localhost:5176","http://localhost:5174","https://*.railway.app","https://incredible-playfulness-production-0e24.up.railway.app")); // React dev server
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


}



