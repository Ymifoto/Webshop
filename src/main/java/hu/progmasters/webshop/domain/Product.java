package hu.progmasters.webshop.domain;

import java.util.Map;
import java.util.TreeMap;

public class Product {

    private final int id;
    private String name;
    private String vendor;
    private int price;
    private int salePrice;
    private String description;
    private String productType;
    private Tax tax;
    private boolean onSale;
    private boolean inStock;

    public Product(int id, String name, String vendor, int price, int salePrice, String description, String productType, Tax tax, boolean inStock) {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
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
        data.put("price", String.valueOf(price));
        data.put("sale_price", String.valueOf(salePrice));
        data.put("description",description);
        data.put("product_type",productType);
        data.put("in_stock",String.valueOf(inStock));
        return data;
    }
}

