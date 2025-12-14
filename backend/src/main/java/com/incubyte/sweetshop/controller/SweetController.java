package com.sweetshop.controller;

import com.sweetshop.dto.SweetRequest;
import com.sweetshop.model.Sweet;
import com.sweetshop.service.SweetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/sweets")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class SweetController {

    private final SweetService sweetService;

    public SweetController(SweetService sweetService) {
        this.sweetService = sweetService;
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Sweet service is running");
    }

    @GetMapping
    public ResponseEntity<List<Sweet>> getAllSweets() {
        return ResponseEntity.ok(sweetService.getAllSweets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sweet> getSweetById(@PathVariable String id) {
        return ResponseEntity.ok(
                sweetService.getSweetById(id)
                        .orElseThrow(() -> new RuntimeException("Sweet not found"))
        );
    }

    @PostMapping
    public ResponseEntity<Sweet> createSweet(@RequestBody SweetRequest sweet) {
        return ResponseEntity.status(201)
                .body(sweetService.createSweet(sweet));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sweet> updateSweet(
            @PathVariable String id,
            @RequestBody SweetRequest sweet
    ) {
        return ResponseEntity.ok(sweetService.updateSweet(id, sweet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSweet(@PathVariable String id) {
        sweetService.deleteSweet(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/restock")
    public ResponseEntity<Sweet> restockSweet(
            @PathVariable String id,
            @RequestParam int quantity
    ) {
        return ResponseEntity.ok(sweetService.restockSweet(id, quantity));
    }

    @PostMapping("/{id}/purchase")
    public ResponseEntity<Sweet> purchaseSweet(
            @PathVariable String id,
            @RequestParam int quantity
    ) {
        return ResponseEntity.ok(sweetService.purchaseSweet(id, quantity));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Sweet>> searchSweets(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return ResponseEntity.ok(
                sweetService.searchSweets(name, category, minPrice, maxPrice)
        );
    }
}
