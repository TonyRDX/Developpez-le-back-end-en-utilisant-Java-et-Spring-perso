package com.openclassrooms.P3.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;
import jakarta.validation.Valid;

import com.openclassrooms.P3.dtos.GetUserDto;
import com.openclassrooms.P3.dtos.LoginRequest;
import com.openclassrooms.P3.dtos.RegisterRequest;
import com.openclassrooms.P3.services.JWTService;
import com.openclassrooms.P3.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;


    @GetMapping("/user")
    public String getUser() {
        return "Welcome, User";
    }
    
    @GetMapping("/admin")
    public String getAdmin() {
        return "Welcome, Admin";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest loginRequest) {
        return Map.of("token", this.getToken(loginRequest.getEmail(), loginRequest.getPassword()));
    }

    @PostMapping("/register")
    public Map<String, String> register(@Valid @RequestBody RegisterRequest req) {
        // try {
            userService.register(req.getEmail(), req.getName(), req.getPassword());
        // } catch (Exception e) {
        //     return Map.of("token", e.getMessage());
        // }

        return Map.of("token", this.getToken(req.getEmail(), req.getPassword()));
    }

    private String getToken(String login, String password) {
        var authToken = new UsernamePasswordAuthenticationToken(login, password);
        var auth = authenticationManager.authenticate(authToken);

        return jwtService.generateToken(auth);
    }

    @GetMapping("/me")
    public GetUserDto me(Authentication authentication) {
        return modelMapper.map(userService.me(), GetUserDto.class);
    }
}