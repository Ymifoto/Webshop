package hu.progmasters.webshop.domain;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private Customer customer;
    private final List<Product> productList = new ArrayList<>();

    public void addProduct(Product product) {
        if (product != null) {
            productList.add(product);
        }
    }

    public List<Product> getProductList() {
        return productList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
