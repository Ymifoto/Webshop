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
    private final Tax tax;
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

    public String getValuesForList() {
        return name + ", Sale price: " + getPrice() + ", " + (onSale ? "On Sale! Original price: " + price : "") + ", " + (inStock ? "In stock" : "Out of stock");
    }

    public Map<String, String> getData() {
        Map<String, String> data = new TreeMap<>();
        data.put("name", name);
        data.put("vendor", vendor);
        data.put("price", String.valueOf(price));
        data.put("sale_price", String.valueOf(salePrice));
        data.put("description", description);
        data.put("product_type", productType);
        data.put("in_stock", inStock ? "1" : "0");
        return data;
    }

    public void updateData(Map<String, String> data) {

        if (data.get("name") != null && data.get("name").length() > 0) {
            name = data.get("name");
        }
        if (data.get("vendor") != null && data.get("vendor").length() > 0) {
            vendor = data.get("vendor");
        }
        if (data.get("product_type") != null && data.get("product_type").length() > 0) {
            productType = data.get("product_type");
        }
        try {
            if (data.get("price") != null && data.get("price").length() > 0) {
                price = Integer.parseInt(data.get("price"));
            }
        } catch (NumberFormatException e) {
            System.out.println("Not a number, price not updated");
        }
        try {
            if (data.get("sale_price") != null && data.get("sale_price").length() > 0) {
                salePrice = Integer.parseInt(data.get("sale_price"));
            }
        } catch (NumberFormatException e) {
            System.out.println("Not a number, sale price not updated");
        }
        if (data.get("description") != null && data.get("description").length() > 0) {
            description = data.get("description");
        }
        if (data.get("in_stock") != null && data.get("in_stock").length() > 0) {
            inStock = data.get("in_stock").equals("1");
        }
        setOnSale();
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

    public boolean isInStock() {
        return inStock;
    }

    public String getVendor() {
        return vendor;
    }

    public String getProductType() {
        return productType;
    }

    public int getSalePrice() {
        return salePrice;
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

    public int getBasicPrice() {
        return price;
    }

    public Tax getTax() {
        return tax;
    }

    public String getDescription() {
        return description;
    }

    private void setOnSale() {
        onSale = salePrice > 0;
    }

    public boolean isOnSale() {
        return onSale;
    }
}

