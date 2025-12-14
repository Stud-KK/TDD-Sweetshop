package com.incubyte.sweetshop.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import com.incubyte.sweetshop.model.Sweet;

import java.util.List;
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
    @Test
    void getAllSweets_shouldReturnAllAddedSweets() {
        SweetService sweetService = new SweetService();

        Sweet sweet1 = new Sweet("Ladoo", "Indian", BigDecimal.valueOf(10), 100);
        Sweet sweet2 = new Sweet("Barfi", "Indian", BigDecimal.valueOf(15), 50);

        sweetService.addSweet(sweet1);
        sweetService.addSweet(sweet2);

        List<Sweet> sweets = sweetService.getAllSweets();

        assertEquals(2, sweets.size());
    }
}
