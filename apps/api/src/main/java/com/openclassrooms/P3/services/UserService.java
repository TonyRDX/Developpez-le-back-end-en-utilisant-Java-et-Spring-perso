package com.openclassrooms.P3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.P3.beans.UserContext;
import com.openclassrooms.P3.exceptions.EmailAlreadyUsedException;
import com.openclassrooms.P3.model.User;
import com.openclassrooms.P3.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserContext userContext;

    public User register(String email, String name, String rawPassword) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyUsedException("Email already exists");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setPassword(passwordEncoder.encode(rawPassword));
        newUser.setRole("USER");

        userRepository.save(newUser);
        return newUser;
    }
    
    public User me() {
        return this.userRepository.findById(this.userContext.getUserId()).orElseThrow();
    }
}
