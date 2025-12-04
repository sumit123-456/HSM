package com.example.hms_backend.authentication.service;

import com.example.hms_backend.authentication.entity.CustomUserDetails;
import com.example.hms_backend.authentication.entity.Permission;
import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.authentication.entity.UserInfo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    private final UserInfoService userInfoService;


    private final PermissionService permissionService;


    //injecting through constructor
    public CustomUserDetailsService(UserService userService, UserInfoService userInfoService ,PermissionService permissionService) {
        this.userService = userService;
        this.permissionService = permissionService;
        this.userInfoService = userInfoService;

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // (1) Find user by username
        UserEntity user = userService.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // (2) Get permissions for this user
        List<Permission> permissions = permissionService.getUserPermissionsByUserId(user.getUserId());

        UserInfo userInfo = userInfoService.findByUserId(user.getUserId()).orElse(null);

        // (3) Wrap user and permissions inside CustomUserDetails
        return new CustomUserDetails(user, userInfo ,permissions);
    }
}
