package com.skill14.controller;

import com.skill14.model.User;
import com.skill14.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // ── POST /api/auth/register ──
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User saved = authService.register(user);
            return ResponseEntity.ok(Map.of(
                "message", "User registered successfully.",
                "username", saved.getUsername()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // ── POST /api/auth/login ──
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        try {
            User user = authService.login(body.get("username"), body.get("password"));
            return ResponseEntity.ok(Map.of(
                "message", "Login successful.",
                "username", user.getUsername(),
                "userId", user.getId()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of("message", e.getMessage()));
        }
    }

    // ── GET /api/auth/profile/{username} ──
    @GetMapping("/profile/{username}")
    public ResponseEntity<?> profile(@PathVariable String username) {
        try {
            User user = authService.getProfile(username);
            // Return user without password
            return ResponseEntity.ok(Map.of(
                "id",       user.getId(),
                "username", user.getUsername(),
                "email",    user.getEmail(),
                "fullName", user.getFullName()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("message", e.getMessage()));
        }
    }
}
