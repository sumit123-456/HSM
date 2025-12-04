package com.example.hms_backend.authentication.controller;

import com.example.hms_backend.authentication.dto.LoginRequestDto;
import com.example.hms_backend.authentication.dto.LoginResponce;
import com.example.hms_backend.authentication.entity.UserInfo;
import com.example.hms_backend.authentication.service.AuthService;
import com.example.hms_backend.authentication.service.UserInfoService;
import com.example.hms_backend.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;
    private UserInfoService userInfoService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) throws Exception {

        log.info("AuthController : loginUser() : Execution Start");
        LoginResponce loginResponce = authService.login(loginRequest);
        if (ObjectUtils.isEmpty(loginResponce)) {
            log.info("Error : {}","Login Unsuccessful");
            return CommonUtil.createErrorResponseMessage("Login Failed Bad Credentials", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CommonUtil.createBuildResponse(loginResponce, HttpStatus.OK);
    }

    @GetMapping("/profile-pic/{userId}")
    public ResponseEntity<byte[]> getProfilePic(@PathVariable Long userId) {
        Optional<UserInfo> userInfoOpt = userInfoService.findByUserId(userId);
        if (userInfoOpt.isPresent() && userInfoOpt.get().getProfilePic() != null) {
            byte[] imageBytes = userInfoOpt.get().getProfilePic();
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // or IMAGE_PNG
                    .body(imageBytes);
        }
        return ResponseEntity.notFound().build();
    }



}

