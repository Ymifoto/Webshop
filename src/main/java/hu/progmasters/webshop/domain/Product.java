package hu.progmasters.webshop.domain;

public class Product {

    private int id;
    private String name;
    private String vendor;
    private int price;
    private  int sale_price;
    private String description;
    private int shipping_price;
    private String productType;
    private Tax tax;
    private boolean onSale;
    private boolean inStock;

    public Product(int id, String name, String vendor, int price, int sale_price, String description, int shipping_price, String productType, Tax tax, boolean onSale, boolean inStock) {
        this.id = id;
        this.name = name;
        this.vendor = vendor;
        this.price = price;
        this.sale_price = sale_price;
        this.description = description;
        this.shipping_price = shipping_price;
        this.productType = productType;
        this.tax = tax;
        this.onSale = onSale;
        this.inStock = inStock;
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

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
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

    public int getShipping_price() {
        return shipping_price;
    }

    public void setShipping_price(int shipping_price) {
        this.shipping_price = shipping_price;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
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

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append(", ");
        sb.append("Name: ").append(name).append(", ");
        sb.append("Vendor: ").append(vendor).append(System.lineSeparator());
        sb.append("Price: ").append(price).append(", ");
        sb.append(onSale ? "Sale price: " + sale_price : "");
        sb.append("Product type: ").append(productType).append(", ");
        sb.append("Description: ").append(description).append(System.lineSeparator());
        sb.append("Shipping price: ").append(shipping_price).append(", ");
        sb.append("In stock: ").append(inStock ? "yes" : "no");
        return sb.toString();
    }
}

