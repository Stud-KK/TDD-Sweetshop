package com.incubyte.sweetshop.controller;

import com.incubyte.sweetshop.controller.dto.RegisterRequest;
import com.incubyte.sweetshop.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.incubyte.sweetshop.controller.dto.LoginRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.login(
                request.getEmail(),
                request.getPassword()
        );
    }

}
