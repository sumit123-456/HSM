package com.example.hms_backend.authentication.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;
    private final UserEntity user;
    private final UserInfo userInfo;
    private final List<GrantedAuthority> authorities;
    private final List<Permission> permissions;

    public CustomUserDetails(UserEntity user, List<Permission> permissions) {
        this(user, null, permissions); // Delegate to 3-arg constructor
    }


    public CustomUserDetails(UserEntity user, UserInfo userInfo, List<Permission> permissions) {
        this.user = user;
        this.userInfo = userInfo;
        this.permissions = permissions;

        System.out.println("under the CustomUserDetails Constructor" );

        List<GrantedAuthority> roleAuthorities = user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getRoleName())) // Add roles as authorities
                .collect(Collectors.toList());

        List<GrantedAuthority> permissionAuthorities = permissions.stream()
                .map(p -> new SimpleGrantedAuthority (p.getPermissionName())) // Add permissions as authorities
                .collect(Collectors.toList());

        this.authorities = new ArrayList<>();
        this.authorities.addAll(roleAuthorities);
        this.authorities.addAll(permissionAuthorities);
    }


    // ✅ Authorities = permissions (VIEW_PATIENT, ADD_PATIENT, etc.)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public List<Permission> getPermissions() {
        return permissions; // 👈 now you can return actual Permission objects
    }

    public UserEntity getUser() {
        return this.user; // <-  current user
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }


    // ✅ Password for authentication
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // ✅ Username for authentication
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // ✅ Account status
    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    // Extra getter
    public Long getId() {
        return user.getUserId();
    }
}