package hu.progmasters.webshop.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private final int id;
    private final Customer customer;
    private final List<Product> orderedProducts = new ArrayList<>();
    private final int shipping_cost;
    private final int orderTotal;
    private final int generalTotal;
    private final String orderTime;
    private double taxAmount;

    public Order(int id, Customer customer, int shipping_cost, int orderTotal, int generalTotal, String orderTime) {
        this.id = id;
        this.customer = customer;
        this.shipping_cost = shipping_cost;
        this.orderTotal = orderTotal;
        this.generalTotal = generalTotal;
        this.orderTime = orderTime;
        setTaxAmpunt();
    }

    private void setTaxAmpunt () {
        taxAmount = orderedProducts.stream().mapToDouble(p -> p.getPrice() * p.getTax().getAmount()).sum();
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getOrderedProducts() {
        return orderedProducts;
    }

    public int getShipping_cost() {
        return shipping_cost;
    }

    public int getOrderTotal() {
        return orderTotal;
    }

    public int getGeneralTotal() {
        return generalTotal;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public double getTaxAmount() {
        return taxAmount;
    }
}
