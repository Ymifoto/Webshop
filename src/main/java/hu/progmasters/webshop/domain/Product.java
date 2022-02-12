package hu.progmasters.webshop.domain;

import java.util.Map;
import java.util.TreeMap;

public class Product {

    private final int id;
    private String name;
    private String vendor;
    private Integer price;
    private Integer salePrice;
    private String description;
    private String productType;
    private Tax tax;
    private boolean onSale;
    private boolean inStock;

    public Product(int id, String name, String vendor, Integer price, Integer salePrice, String description, String productType, Tax tax, boolean inStock) {
        this.id = id;
        this.name = name;
        this.vendor = vendor;
        this.price = price;
        this.salePrice = salePrice;
        this.description = description;
        this.productType = productType;
        this.tax = tax;
        this.inStock = inStock;
        setOnSale();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return onSale ? salePrice : price;
    }

    public void setSalePrice(int sale_price) {
        this.salePrice = sale_price;
        setOnSale();
    }

    public Tax getTax() {
        return tax;
    }

    private void setOnSale() {
        onSale = salePrice > 0;
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

    public Map<String, String> getData() {
        Map<String, String> data = new TreeMap<>();
        data.put("name", name);
        data.put("vendor", vendor);
        data.put("price", price.toString());
        data.put("sale_price", salePrice.toString());
        data.put("description",description);
        data.put("product_type",productType);
        data.put("in_stock",String.valueOf(inStock));
        return data;
    }
}

