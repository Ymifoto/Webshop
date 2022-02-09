package hu.progmasters.webshop.domain;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private final List<Product> products = new ArrayList<>();
    private final String description;

    public Category(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getDescription() {
        return description;
    }
}
