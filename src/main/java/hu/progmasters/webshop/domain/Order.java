package hu.progmasters.webshop.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private final int id;
    private final Customer customer;
    private final List<Product> orderedProducts = new ArrayList<>();
    private final String shipping_method;
    private final int shippingCost;
    private final int orderTotal;
    private final int generalTotal;
    private final String orderTime;
    private double taxAmount;

    public Order(int id, Customer customer, String shipping_method, int shippingCost, int orderTotal, String orderTime) {
        this.id = id;
        this.customer = customer;
        this.shipping_method = shipping_method;
        this.shippingCost = shippingCost;
        this.orderTotal = orderTotal;
        this.orderTime = orderTime;
        generalTotal = shippingCost + orderTotal;
        setTaxAmount();
    }

    private void setTaxAmount() {
        taxAmount = orderedProducts.stream().mapToDouble(p -> p.getPrice() * p.getTax().getAmount()).sum();
    }

    public List<Product> getOrderedProducts() {
        return orderedProducts;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append(", ");
        sb.append("Customer: ").append(customer.getId() + ", " + customer.getName() + ", " + customer.getEmail()).append(System.lineSeparator());
        sb.append("Ordered products: ").append(System.lineSeparator());
        orderedProducts.forEach(p -> sb.append(p.getName() + ", "));
        sb.append("Shipping method: ").append(shipping_method).append("Shipping cost: ").append(shippingCost).append(System.lineSeparator());
        sb.append("General total: ").append(generalTotal).append(", ");
        sb.append("Tax amount: ").append(taxAmount).append(System.lineSeparator());
        sb.append("Order time: ").append(orderTime);
        return sb.toString();
    }
}
