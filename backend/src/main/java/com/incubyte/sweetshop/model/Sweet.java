package com.incubyte.sweetshop.model;

import java.math.BigDecimal;

public class Sweet {

    private String name;
    private String category;
    private BigDecimal price;
    private int quantity;

    public Sweet(String name, String category, BigDecimal price, int quantity) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
