package com.incubyte.sweetshop.service;

import com.incubyte.sweetshop.model.Sweet;
import com.incubyte.sweetshop.repository.SweetRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SweetService {

    private final SweetRepository sweetRepository;
    public Sweet addSweet(Sweet sweet) {
        return sweetRepository.save(sweet);
    }

    public SweetService(SweetRepository sweetRepository) {
        this.sweetRepository = sweetRepository;
    }

    // ---------- CREATE ----------
    public Sweet createSweet(Sweet sweetRequest) {
        Sweet sweet = new Sweet();
        sweet.setName(sweetRequest.getName());
        sweet.setCategory(sweetRequest.getCategory());
        sweet.setPrice(sweetRequest.getPrice());
        sweet.setQuantity(sweetRequest.getQuantity());
        sweet.setDescription(sweetRequest.getDescription());
        sweet.setCreatedAt(LocalDateTime.now());

        return sweetRepository.save(sweet);
    }

    // ---------- READ ----------
    public List<Sweet> getAllSweets() {
        return sweetRepository.findAll();
    }

    public Optional<Sweet> getSweetById(String id) {
        return sweetRepository.findById(id);
    }

    public List<Sweet> searchByCategory(String category) {
        return sweetRepository.findByCategoryIgnoreCase(category);
    }

    // ---------- UPDATE ----------
    public Sweet updateSweet(String id, Sweet sweetRequest) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sweet not found: " + id));

        sweet.setName(sweetRequest.getName());
        sweet.setCategory(sweetRequest.getCategory());
        sweet.setPrice(sweetRequest.getPrice());
        sweet.setQuantity(sweetRequest.getQuantity());
        sweet.setDescription(sweetRequest.getDescription());
        sweet.setUpdatedAt(LocalDateTime.now());

        return sweetRepository.save(sweet);
    }

    // ---------- DELETE ----------
    public void deleteSweet(String id) {
        if (!sweetRepository.existsById(id)) {
            throw new IllegalArgumentException("Sweet not found: " + id);
        }
        sweetRepository.deleteById(id);
    }

    // ---------- BUSINESS ----------
    public Sweet purchaseSweet(String id, int quantity) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sweet not found: " + id));

        if (sweet.getQuantity() < quantity) {
            throw new IllegalStateException("Insufficient stock");
        }

        sweet.setQuantity(sweet.getQuantity() - quantity);
        sweet.setUpdatedAt(LocalDateTime.now());

        return sweetRepository.save(sweet);
    }

    public Sweet restockSweet(String id, int quantity) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sweet not found: " + id));

        sweet.setQuantity(sweet.getQuantity() + quantity);
        sweet.setUpdatedAt(LocalDateTime.now());

        return sweetRepository.save(sweet);
    }

    // ---------- SEARCH ----------
    public List<Sweet> searchSweets(
            String name,
            String category,
            BigDecimal minPrice,
            BigDecimal maxPrice
    ) {
        if (name != null && category != null && minPrice != null && maxPrice != null) {
            return sweetRepository
                    .findByNameContainingAndCategoryContainingAndPriceBetween(
                            name, category, minPrice, maxPrice);
        }

        if (name != null) {
            return sweetRepository.findByNameContainingIgnoreCase(name);
        }

        if (category != null) {
            return sweetRepository.findByCategoryIgnoreCase(category);
        }

        if (minPrice != null && maxPrice != null) {
            return sweetRepository.findByPriceBetween(minPrice, maxPrice);
        }

        return sweetRepository.findAll();
    }
}
