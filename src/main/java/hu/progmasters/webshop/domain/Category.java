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

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append(", ");
        sb.append("Name: ").append(name);
        sb.append(description.length() > 0 ? ", Description: " + description : description);
        return sb.toString();
    }

    public Map<String, String> getData() {
        Map<String, String> data = new TreeMap<>();
        data.put("id", String.valueOf(id));
        data.put("category_name", name);
        data.put("category_desc", description);
        return data;
    }

    public void updateData(Map<String, String> data) {
        if (data.get("category_name").length() > 0) {
            name = data.get("category_name");
        }
        if (data.get("category_desc").length() > 0) {
            description = data.get("category_desc");
        }
    }
}
