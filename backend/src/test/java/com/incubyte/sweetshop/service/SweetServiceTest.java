package com.incubyte.sweetshop.service;

import com.incubyte.sweetshop.model.Sweet;
import com.incubyte.sweetshop.repository.SweetRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import java.util.Optional;

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
    void getAllSweets_shouldReturnAllSweets() {

        Sweet sweet1 = new Sweet("Ladoo", "Indian", BigDecimal.valueOf(10), 100);
        Sweet sweet2 = new Sweet("Barfi", "Indian", BigDecimal.valueOf(15), 50);

        when(sweetRepository.findAll())
                .thenReturn(List.of(sweet1, sweet2));

        List<Sweet> sweets = sweetService.getAllSweets();

        assertEquals(2, sweets.size());
    }

    // ---------- GET BY ID ----------
    @Test
    void getSweetById_existingId_shouldReturnSweet() {
        Sweet sweet = new Sweet();
        when(sweetRepository.findById("1"))
                .thenReturn(Optional.of(sweet));

        Optional<Sweet> result = sweetService.getSweetById("1");

        assertTrue(result.isPresent());
    }



    // ---------- UPDATE ----------
    @Test
    void updateSweet_existingId_shouldUpdateSweet() {
        Sweet existing = new Sweet();
        when(sweetRepository.findById("1"))
                .thenReturn(Optional.of(existing));
        when(sweetRepository.save(existing))
                .thenReturn(existing);

        Sweet request = new Sweet();
        request.setName("Barfi");
        request.setCategory("Indian");
        request.setPrice(TEN);
        request.setQuantity(20);
        request.setDescription("Updated");
        ;

        Sweet result = sweetService.updateSweet("1", request);

        assertEquals("Barfi", result.getName());
        verify(sweetRepository).save(existing);
    }

    // ---------- DELETE ----------
    @Test
    void deleteSweet_existingId_shouldDelete() {
        when(sweetRepository.existsById("1")).thenReturn(true);

        sweetService.deleteSweet("1");

        verify(sweetRepository).deleteById("1");
    }

    @Test
    void deleteSweet_nonExistingId_shouldThrowException() {
        when(sweetRepository.existsById("1")).thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> sweetService.deleteSweet("1"));
    }

    // ---------- PURCHASE ----------
    @Test
    void purchaseSweet_shouldReduceQuantity() {
        Sweet sweet = new Sweet();
        sweet.setQuantity(10);

        when(sweetRepository.findById("1"))
                .thenReturn(Optional.of(sweet));
        when(sweetRepository.save(sweet))
                .thenReturn(sweet);

        Sweet result = sweetService.purchaseSweet("1", 3);

        assertEquals(7, result.getQuantity());
    }

    @Test
    void purchaseSweet_insufficientStock_shouldThrowException() {
        Sweet sweet = new Sweet();
        sweet.setQuantity(2);

        when(sweetRepository.findById("1"))
                .thenReturn(Optional.of(sweet));

        assertThrows(RuntimeException.class,
                () -> sweetService.purchaseSweet("1", 5));
    }

    // ---------- RESTOCK ----------
    @Test
    void restockSweet_shouldIncreaseQuantity() {
        Sweet sweet = new Sweet();
        sweet.setQuantity(5);

        when(sweetRepository.findById("1"))
                .thenReturn(Optional.of(sweet));
        when(sweetRepository.save(sweet))
                .thenReturn(sweet);

        Sweet result = sweetService.restockSweet("1", 5);

        assertEquals(10, result.getQuantity());
    }

    // ---------- SEARCH ----------
    @Test
    void searchSweets_byName_shouldCallNameSearch() {
        sweetService.searchSweets("Ladoo", null, null, null);

        verify(sweetRepository)
                .findByNameContainingIgnoreCase("Ladoo");
    }

    @Test
    void searchSweets_byCategory_shouldCallCategorySearch() {
        sweetService.searchSweets(null, "Indian", null, null);

        verify(sweetRepository)
                .findByCategoryIgnoreCase("Indian");
    }

    @Test
    void searchSweets_byPriceRange_shouldCallPriceSearch() {
        sweetService.searchSweets(null, null,
                BigDecimal.valueOf(10), BigDecimal.valueOf(20));

        verify(sweetRepository)
                .findByPriceBetween(
                        BigDecimal.valueOf(10),
                        BigDecimal.valueOf(20)
                );
    }
}
