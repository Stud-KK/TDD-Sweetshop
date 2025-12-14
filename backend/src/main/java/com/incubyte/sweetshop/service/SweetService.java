package com.incubyte.sweetshop.service;

import com.incubyte.sweetshop.model.Sweet;
import java.util.ArrayList;
import java.util.List;
public class SweetService {

    private final List<Sweet> sweets = new ArrayList<>();

    public Sweet addSweet(Sweet sweet) {
        sweets.add(sweet);
        return sweet;
    }

    public List<Sweet> getAllSweets() {
        return sweets;
    }


}
