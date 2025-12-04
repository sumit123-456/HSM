//package com.example.hms_backend.security;
//
//import com.example.hms_backend.authentication.entity.Role;
//import com.example.hms_backend.authentication.entity.UserEntity;
//import com.example.hms_backend.authentication.repo.RoleRepo;
//import com.example.hms_backend.authentication.repo.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Set;
//// this is component use for creating admin by runnining the program
//@Component
//public class TestUserSeeder implements CommandLineRunner {
//
//
//
//    private final UserRepo userRepo;
//    private final RoleRepo roleRepo;
//    private final PasswordEncoder passwordEncoder;
//
//    public TestUserSeeder(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
//        this.userRepo = userRepo;
//        this.roleRepo = roleRepo;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    @Transactional
//    public void run(String... args) {
//        // Skip if already exists
//        if (userRepo.existsByUsername("harish")) return;
//
//        // Fetch role manually (adjust ID or use findByRoleName if needed)
//        Role role = roleRepo.findById(2L)
//                .orElseThrow(() -> new RuntimeException("Role not found"));
//
//        // Create and save user
//        UserEntity user = new UserEntity();
//        user.setUsername("harish");
//        user.setEmail("harisha@gmail.com");
//        user.setPassword(passwordEncoder.encode("Hello@456")); // encrypted password
//        user.setRoles(Set.of(role));
//
//        userRepo.save(user);
//
//    }
//}
