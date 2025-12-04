package com.example.hms_backend.authentication.service;

import com.example.hms_backend.authentication.dto.LoginRequestDto;
import com.example.hms_backend.authentication.dto.LoginResponce;
import com.example.hms_backend.authentication.dto.UserRequest;
import com.example.hms_backend.authentication.entity.CustomUserDetails;
import com.example.hms_backend.authentication.jwt.JWTService;
import com.example.hms_backend.authentication.repo.RoleRepo;
import com.example.hms_backend.authentication.repo.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepo roleRepository;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Override
    public LoginResponce login(LoginRequestDto loginRequest) throws Exception {

        log.info("AuthServiceImpl : login() : Execution Start");

        if (loginRequest.getUsername() == null || loginRequest.getUsername().isBlank()) {
            throw new Exception("Username cannot be empty!");
        }

        Authentication authenticate = manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        if (authenticate.isAuthenticated()) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authenticate.getPrincipal();

            String token = jwtService.generateToken(
                    customUserDetails.getUser(),
                    customUserDetails.getUserInfo(),
                    customUserDetails.getPermissions()
            );
            Claims claims = jwtService.extractAllclaims(token);
            log.info("Decoded JWT Claims: {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(claims));
            log.info("Generated JWT Token: {}", token);

            UserRequest userDto = UserRequest.builder()
                    .userId(customUserDetails.getUser().getUserId())
                    .username(customUserDetails.getUser().getUsername())
                    .firstName(customUserDetails.getUserInfo() != null ? customUserDetails.getUserInfo().getFirstName() : null)
                    .lastName(customUserDetails.getUserInfo() != null ? customUserDetails.getUserInfo().getLastName() : null)
                    .email(customUserDetails.getUser().getEmail())
                    .build();

//            return LoginResponce.builder()
//                    .user(modelMapper.map(customUserDetails.getUser(), UserRequest.class))
//                    .token(token)
//                    .build();

            return LoginResponce.builder()
                    .user(userDto)
                    .token(token)
                    .build();


        }
        throw new Exception("Invalid login!");
    }
}
