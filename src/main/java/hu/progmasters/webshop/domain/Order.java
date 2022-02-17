package hu.progmasters.webshop.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private final int id;
    private final Customer customer;
    private final List<Product> orderedProducts = new ArrayList<>();
    private final String shippingMethod;
    private final String paymentMethod;
    private final int shippingCost;
    private final int orderTotal;
    private final String orderTime;
    private double taxAmount;
    private boolean shipped;

    public Order(int id, Customer customer, String shippingMethod, String paymentMethod, int shippingCost, int orderTotal, String orderTime) {
        this.id = id;
        this.customer = customer;
        this.shippingMethod = shippingMethod;
        this.paymentMethod = paymentMethod;
        this.shippingCost = shippingCost;
        this.orderTotal = orderTotal;
        this.orderTime = orderTime;
        setTaxAmount();
    }

    private void setTaxAmount() {
        taxAmount = orderedProducts.stream().mapToDouble(p -> p.getPrice() * p.getTax().getAmount()).sum();
    }

    public List<Product> getOrderedProducts() {
        return orderedProducts;
    }

    public boolean isShipped() {
        return shipped;
    }

    public void setShipped(boolean shipped) {
        this.shipped = shipped;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append(", ");
        sb.append("Customer: ").append(customer.getId() + ", " + customer.getName() + ", " + customer.getEmail()).append(System.lineSeparator());
        sb.append("Ordered products: ").append(System.lineSeparator());
        orderedProducts.forEach(p -> sb.append(p.getName() + ", "));
        sb.append("Shipping method: ").append(shippingMethod).append("Shipping cost: ").append(shippingCost).append(System.lineSeparator());
        sb.append("Payment method: ").append(paymentMethod).append(System.lineSeparator());
        sb.append("General total: ").append(orderTotal+shippingCost).append(", ");
        sb.append("Tax amount: ").append(taxAmount).append(System.lineSeparator());
        sb.append("Order time: ").append(orderTime);
        return sb.toString();
    }

    public int getId() {
        return id;
    }
}
