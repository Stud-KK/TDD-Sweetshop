package com.incubyte.sweetshop.service;

import com.incubyte.sweetshop.controller.dto.RegisterRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.incubyte.sweetshop.security.JwtUtil;
class AuthServiceTest {


    private final JwtUtil jwtUtil = mock(JwtUtil.class);
    private final AuthService authService = new AuthService(jwtUtil);

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
    @Test
    void login_shouldReturnJwtToken() {
        when(jwtUtil.generateToken("komal@example.com"))
                .thenReturn("dummy-jwt-token");

        RegisterRequest register = new RegisterRequest(
                "Komal",
                "komal@example.com",
                "Password123"
        );

        authService.register(register);

        String token = authService.login(
                "komal@example.com",
                "Password123"
        );

        assertNotNull(token);
    }
}
