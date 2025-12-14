package com.incubyte.sweetshop.service;

import com.incubyte.sweetshop.controller.dto.RegisterRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {


    private final Set<String> registeredEmails = new HashSet<>();

    public void register(RegisterRequest request) {
        if (registeredEmails.contains(request.getEmail())) {
            throw new IllegalStateException("Email already exists");
        }

        registeredEmails.add(request.getEmail());
    }
}
