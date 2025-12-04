package com.example.hms_backend.authentication.controller;

import com.example.hms_backend.authentication.service.CurrentUserAndPermissionService;
import com.example.hms_backend.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private CurrentUserAndPermissionService currentUserAndPermissionService;

    @GetMapping("/")
    public String showHome() {

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLogin() {

        return "login";
    }


}

