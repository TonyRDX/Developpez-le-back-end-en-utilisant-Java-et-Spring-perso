package com.openclassrooms.P3.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Map;

import com.openclassrooms.P3.services.JWTService;


@RestController
public class LoginController {

    @GetMapping("/user")
    public String getUser() {
        return "Welcome, User";
    }
    
    @GetMapping("/admin")
    public String getAdmin() {
        return "Welcome, Admin";
    }
    
    private final JWTService jwtService;

    public LoginController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/auth/login")
    public Map<String, String> login(Authentication authentication) {
        String token = jwtService.generateToken(authentication);
        return Map.of("token", token);
    }
}