package com.example.hms_backend.authentication.service;

import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.authentication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;


    PasswordEncoder passwordEncoder;

    public UserServiceImpl(@Lazy PasswordEncoder passwordEncoder) {  // <-- add @Lazy
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createUser(UserEntity userEntity)
    {
        String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(encodedPassword);

        return userRepo.save(userEntity);


    }
    public Optional<UserEntity> findUserByUsername(String username)
    {
        return userRepo.findByUsername(username);
    }
}
