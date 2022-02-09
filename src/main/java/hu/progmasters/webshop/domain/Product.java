package hu.progmasters.webshop.domain;

public class Product {

    private int id;
    private String name;
    private int price;
    private  int sale_price;
    private String description;
    private boolean active;
    private int shipping_price;
    private int productType;
    private Tax tax;
    private boolean onSale;

    public Product(int id, String name, int price, int sale_price, String description, boolean active, int shipping_price, int productType, Tax tax, boolean onSale) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sale_price = sale_price;
        this.description = description;
        this.active = active;
        this.shipping_price = shipping_price;
        this.productType = productType;
        this.tax = tax;
        this.onSale = onSale;
    }

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSale_price() {
        return sale_price;
    }

    public void setSale_price(int sale_price) {
        this.sale_price = sale_price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getShipping_price() {
        return shipping_price;
    }

    public void setShipping_price(int shipping_price) {
        this.shipping_price = shipping_price;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public boolean isOnSale() {
        return onSale;
    }

    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }
}

