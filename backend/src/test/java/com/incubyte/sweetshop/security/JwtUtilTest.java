package com.incubyte.sweetshop.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private final JwtUtil jwtUtil = new JwtUtil();

    @Test
    void validateToken_shouldReturnEmail() {
        String token = jwtUtil.generateToken("komal@example.com");

        String email = jwtUtil.validateAndExtractEmail(token);

        assertEquals("komal@example.com", email);
    }



}
