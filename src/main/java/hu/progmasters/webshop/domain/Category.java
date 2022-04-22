package hu.progmasters.webshop.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Getter
@Setter
public class Category {

    private int id;
    private String name;
    private final List<Product> products = new ArrayList<>();
    private String description = "";

    public Map<String, Object> getData() {
        Map<String, Object> data = new TreeMap<>();
        data.put("id", String.valueOf(id));
        data.put("category_name", name);
        data.put("category_desc", description);
        return data;
    }

    public void updateData(Map<String, Object> data) {
        if (data.containsKey("category_name") && data.get("category_name") != null) {
            name = (String) data.get("category_name");
        }
        if (data.containsKey("category_desc") && data.get("category_desc") != null) {
            description = (String) data.get("category_desc");
        }
    }

    public void setDescription(String description) {
        if (description != null) {
            this.description = description;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append(", ");
        sb.append("Name: ").append(name);
        sb.append(description.length() > 0 ? ", Description: " + description : description);
        return sb.toString();
    }
}
