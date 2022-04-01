package hu.progmasters.webshop.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

    private final int id;
    private final Customer customer;
    private final List<Product> orderedProducts = new ArrayList<>();
    private final String shippingMethod;
    private final String paymentMethod;
    private final int shippingCost;
    private final int orderTotal;
    private final Date orderTime;
    private BigDecimal taxAmount;
    private final boolean shipped;

    public Order(int id, Customer customer, String shippingMethod, String paymentMethod, int shippingCost, int orderTotal, Date orderTime, boolean shipped) {
        this.id = id;
        this.customer = customer;
        this.shippingMethod = shippingMethod;
        this.paymentMethod = paymentMethod;
        this.shippingCost = shippingCost;
        this.orderTotal = orderTotal;
        this.orderTime = orderTime;
        this.shipped = shipped;
    }

    private void setTaxAmount() {
        taxAmount = BigDecimal.valueOf(orderedProducts.stream().mapToDouble(p -> p.getPrice() * p.getTax().getAmount()).sum());
    }

    public List<Product> getOrderedProducts() {
        return orderedProducts;
    }

    @Override
    public String toString() {
        setTaxAmount();
        final StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append(", ");
        sb.append("Customer: ").append(customer.getId() + ", " + customer.getName() + ", " + customer.getEmail()).append(System.lineSeparator());
        sb.append("Ordered products: ");
        orderedProducts.forEach(p -> sb.append(p.getName() + ", "));
        sb.append(System.lineSeparator());
        sb.append("Shipping method: ").append(shippingMethod).append(", Shipping cost: ").append(shippingCost).append(System.lineSeparator());
        sb.append("Payment method: ").append(paymentMethod).append(", General total: ").append(orderTotal+shippingCost).append(", ");
        sb.append("Tax amount: ").append(taxAmount).append(System.lineSeparator());
        sb.append("Order time: ").append(orderTime).append(", Shipped: " + (shipped ? "yes" : "no"));
        return sb.toString();
    }

    public int getId() {
        return id;
    }
}
