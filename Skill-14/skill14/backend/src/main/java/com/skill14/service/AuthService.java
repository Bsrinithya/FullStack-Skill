package com.skill14.service;

import com.skill14.model.User;
import com.skill14.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    // ── Register ──
    public User register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already taken.");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered.");
        }
        // NOTE: In production, hash the password (e.g., BCrypt).
        // For this lab, plain text is used for simplicity.
        return userRepository.save(user);
    }

    // ── Login ──
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found."));
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password.");
        }
        return user;
    }

    // ── Fetch profile by username ──
    public User getProfile(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found."));
    }
}
