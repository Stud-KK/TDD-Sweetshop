package com.incubyte.sweetshop.service;

import com.incubyte.sweetshop.dto.RegisterRequest;
import com.incubyte.sweetshop.security.JwtUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final Map<String, String> users = new HashMap<>();
    private final Map<String, String> roles = new HashMap<>();
    private final JwtUtil jwtUtil;

    public AuthService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    @PostConstruct
    void initAdmin() {
        users.put("admin@sweetshop.com", "admin123");
        roles.put("admin@sweetshop.com", "ADMIN");
    }

    public void register(RegisterRequest request) {
        if (users.containsKey(request.getEmail())) {
            throw new IllegalStateException("Email already exists");
        }
        users.put(request.getEmail(), request.getPassword());
        roles.put(request.getEmail(), "USER");
    }

    public String login(String email, String password) {
        if (!users.containsKey(email)) {
            throw new IllegalStateException("User not found");
        }
        if (!users.get(email).equals(password)) {
            throw new IllegalStateException("Invalid credentials");
        }

        return jwtUtil.generateToken(email, roles.get(email));
    }

}
