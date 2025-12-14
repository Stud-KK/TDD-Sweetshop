package com.incubyte.sweetshop.service;

import com.incubyte.sweetshop.model.Sweet;
import com.incubyte.sweetshop.repository.SweetRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.TEN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SweetServiceTest {

    private final SweetRepository sweetRepository = mock(SweetRepository.class);
    private final SweetService sweetService = new SweetService(sweetRepository);

    @Test
    void addSweet_shouldStoreSweet() {
        Sweet sweet = new Sweet(
                "Ladoo",
                "Indian",
                BigDecimal.valueOf(10),
                100
        );

        when(sweetRepository.save(sweet)).thenReturn(sweet);

        Sweet saved = sweetService.addSweet(sweet);

        assertNotNull(saved);
        assertEquals("Ladoo", saved.getName());
    }

    @Test
    void searchByCategory_shouldReturnMatchingSweets() {
        Sweet sweet = new Sweet("Ladoo", "Indian", TEN, 10);

        when(sweetRepository.findByCategoryIgnoreCase("Indian"))
                .thenReturn(List.of(sweet));

        List<Sweet> result = sweetService.searchByCategory("Indian");

        assertEquals(1, result.size());
        assertEquals("Indian", result.get(0).getCategory());
    }


    @Test
    void getAllSweets_shouldReturnAllAddedSweets() {
//SweetService sweetService = new SweetService();

        Sweet sweet1 = new Sweet("Ladoo", "Indian", BigDecimal.valueOf(10), 100);
        Sweet sweet2 = new Sweet("Barfi", "Indian", BigDecimal.valueOf(15), 50);

        sweetService.addSweet(sweet1);
        sweetService.addSweet(sweet2);

        List<Sweet> sweets = sweetService.getAllSweets();

        assertEquals(2, sweets.size());
    }
}
