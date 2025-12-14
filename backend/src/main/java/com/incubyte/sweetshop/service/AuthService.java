package com.incubyte.sweetshop.service;

import com.incubyte.sweetshop.controller.dto.RegisterRequest;
import com.incubyte.sweetshop.security.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final Map<String, String> users = new HashMap<>();
    private final JwtUtil jwtUtil;

    public AuthService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public void register(RegisterRequest request) {
        if (users.containsKey(request.getEmail())) {
            throw new IllegalStateException("Email already exists");
        }
        users.put(request.getEmail(), request.getPassword());
    }

    public String login(String email, String password) {
      return null;
    }
}
