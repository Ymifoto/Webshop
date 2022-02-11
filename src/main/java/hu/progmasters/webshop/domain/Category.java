package hu.progmasters.webshop.domain;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private String name;
    private List<Product> products;
    private String description;

    public Category() {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
