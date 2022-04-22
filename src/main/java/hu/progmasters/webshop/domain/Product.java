package hu.progmasters.webshop.domain;

import lombok.Getter;

import java.util.Map;
import java.util.TreeMap;

@Getter
public class Product {

    private int id;
    private String name;
    private String vendor;
    private long price;
    private long salePrice;
    private String description;
    private String productType;
    private Tax tax;
    private boolean onSale;
    private boolean inStock;

    public String getValuesForList() {
        return name + ", Sale price: " + getPrice() + ", " + (onSale ? "On Sale! Original price: " + price : "") + ", " + (inStock ? "In stock" : "Out of stock");
    }

    public Map<String, Object> getData() {
        Map<String, Object> data = new TreeMap<>();
        data.put("name", name);
        data.put("vendor", vendor);
        data.put("price", String.valueOf(price));
        data.put("sale_price", String.valueOf(salePrice));
        data.put("description", description);
        data.put("product_type", productType);
        data.put("in_stock", inStock ? "1" : "0");
        return data;
    }

    public Product updateData(Map<String, Object> data) {

        if (data.containsKey("id") && data.get("id") != null) {
            id = (Integer) data.get("id");
        }

        if (data.containsKey("name") && data.get("name") != null) {
            name = (String) data.get("name");
        }

        if (data.containsKey("vendor_name") && data.get("vendor_name") != null) {
            vendor = (String) data.get("vendor_name");
        }

        if (data.containsKey("product_type_name") && data.get("product_type_name") != null) {
            productType = (String) data.get("product_type_name");
        }

        if (data.containsKey("price") && data.get("price") != null) {
            price =  (Long) data.get("price");
        }

        if (data.containsKey("sale_price") && data.get("sale_price") != null) {
            salePrice =  (Long) data.get("sale_price");
        }

        if (data.containsKey("description") && data.get("description") != null) {
            description = (String) data.get("description");
        }

        if (data.containsKey("tax") && data.get("tax") != null) {
            tax = Tax.valueOf((String) data.get("tax"));
        }

        if (data.containsKey("in_stock") && data.get("in_stock") != null) {
            inStock = (Boolean) data.get("in_stock");
        }
        setOnSale();
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append(", ");
        sb.append("Name: ").append(name).append(", ");
        sb.append("Vendor: ").append(vendor).append(System.lineSeparator());
        sb.append("Price: ").append(price).append(", ");
        sb.append(onSale ? "Sale price: " + salePrice + ", " : "");
        sb.append("Product type: ").append(productType).append(", ");
        sb.append("In stock: ").append(inStock ? "yes" : "no").append(System.lineSeparator());
        sb.append("Description: ").append(description).append(System.lineSeparator());
        return sb.toString();
    }

    public long getFinalPrice() {
        return onSale ? salePrice : price;
    }

    private void setOnSale() {
        onSale = salePrice > 0;
    }
}

