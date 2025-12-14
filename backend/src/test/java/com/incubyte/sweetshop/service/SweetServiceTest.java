package com.incubyte.sweetshop.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import com.incubyte.sweetshop.model.Sweet;
class SweetServiceTest {

    @Test
    void addSweet_shouldStoreSweet() {
        Sweet sweet = new Sweet(
                "Ladoo",
                "Indian",
                BigDecimal.valueOf(10),
                100
        );

        SweetService sweetService = new SweetService();

        Sweet saved = sweetService.addSweet(sweet);

        assertNotNull(saved);
        assertEquals("Ladoo", saved.getName());
    }
}
