package com.skill15.controller;

import com.skill15.entity.User;
import com.skill15.repository.UserRepository;
import com.skill15.service.CustomUserDetailsService;
import com.skill15.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private CustomUserDetailsService userDetailsService;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    /**
     * POST /api/auth/register
     * Body: { "username": "...", "password": "...", "role": "ROLE_ADMIN" | "ROLE_EMPLOYEE" }
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        String role     = body.get("role");

        if (username == null || password == null || role == null)
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "username, password, and role are required."));

        if (!role.equals("ROLE_ADMIN") && !role.equals("ROLE_EMPLOYEE"))
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "role must be ROLE_ADMIN or ROLE_EMPLOYEE."));

        if (userRepository.findByUsername(username).isPresent())
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Username already exists."));

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        userRepository.save(user);

        return ResponseEntity.ok(Map.of(
                "message", "User registered successfully.",
                "username", username,
                "role", role));
    }

    /**
     * POST /api/auth/login
     * Body: { "username": "...", "password": "..." }
     * Returns: { "token": "<JWT>", "username": "...", "role": "...", "message": "..." }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401)
                    .body(Map.of("message", "Invalid username or password."));
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Safely fetch role from DB
        String role = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found after authentication"))
                .getRole();

        String token = jwtUtil.generateToken(userDetails, role);

        return ResponseEntity.ok(Map.of(
                "token",    token,
                "username", username,
                "role",     role,
                "message",  "Login successful."));
    }
}
