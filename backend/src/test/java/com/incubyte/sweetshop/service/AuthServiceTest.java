package com.incubyte.sweetshop.service;

import com.incubyte.sweetshop.controller.dto.RegisterRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthServiceTest {

    private final AuthService authService = new AuthService();

    @Test
    void register_withExistingEmail_shouldThrowException() {
        RegisterRequest request = new RegisterRequest(
                "Komal",
                "komal@example.com",
                "Password123"
        );

        authService.register(request);

        assertThrows(
                IllegalStateException.class,
                () -> authService.register(request)
        );
    }
}
