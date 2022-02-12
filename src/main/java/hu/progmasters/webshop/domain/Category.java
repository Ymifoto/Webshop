package hu.progmasters.webshop.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Category {

    private int id;
    private String name;
    private final List<Product> products = new ArrayList<>();
    private String description = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append(", ");
        sb.append("Name: ").append(name).append(", ");
        sb.append(description.length() > 0 ? "Description: " + description : description);
        return sb.toString();
    }

    public Map<String, String> getData() {
        Map<String, String> data = new TreeMap<>();
        data.put("id", String.valueOf(id));
        data.put("name", name);
        data.put("description", description);
        return data;
    }
}
