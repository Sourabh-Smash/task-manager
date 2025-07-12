package com.sourabh.task_manager.controller;

import com.sourabh.task_manager.entity.UserAuthEntity;
import com.sourabh.task_manager.service.UserAuthEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class UserAuthController {
    @Autowired
    UserAuthEntityService userAuthEntityService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserAuthEntity userAuthDetails){
        // Add validation checks
        if (userAuthDetails.getUsername() == null || userAuthDetails.getUsername().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Username cannot be empty");
        }

        if (userAuthDetails.getPassword() == null || userAuthDetails.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Password cannot be empty");
        }

        // Set default role if not provided
        if (userAuthDetails.getRole() == null || userAuthDetails.getRole().trim().isEmpty()) {
            userAuthDetails.setRole("ROLE_USER");
        }

        try {
            // Hash the password before storing
            userAuthDetails.setPassword(passwordEncoder.encode(userAuthDetails.getPassword()));
            // Save user
            userAuthEntityService.save(userAuthDetails);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }
}