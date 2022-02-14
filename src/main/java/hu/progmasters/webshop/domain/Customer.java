package hu.progmasters.webshop.domain;

import java.util.Map;
import java.util.TreeMap;

public class Customer {

    private final int id;
    private String name;
    private String shippingAddress;
    private String billingAddress;
    private int discount;
    private String email;
    private boolean regularCustomer;
    private String companyName;
    private boolean company;
    private String taxNumber;

    public Customer(int id, String name, String shippingAddress, String billingAddress, int discount, String email, boolean regularCustomer, String companyName, boolean company, String taxNumber) {
        this.id = id;
        this.name = name;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.discount = discount;
        this.email = email;
        this.regularCustomer = regularCustomer;
        this.companyName = companyName;
        this.company = company;
        this.taxNumber = taxNumber;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isRegular_customer() {
        return regularCustomer;
    }

    public void setRegular_customer(boolean regular_customer) {
        this.regularCustomer = regular_customer;
    }

    public boolean isCompany() {
        return company;
    }

    public void setCompany(boolean company) {
        this.company = company;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append(", ");
        sb.append("Name:").append(name).append(", ");
        sb.append("Email: ").append(email).append(System.lineSeparator());
        sb.append("Shipping address: ").append(shippingAddress).append(", ");
        sb.append("Billing address: ").append(billingAddress).append(System.lineSeparator());
        sb.append("Discount: ").append(discount).append("% ").append(", ");
        sb.append("Regular customer: ").append(regularCustomer ? "yes" : "no").append(", ");
        sb.append(company ? "Tax number: " + taxNumber + System.lineSeparator() : "");
        return sb.toString();
    }

    public Map<String, String> getData() {
        Map<String, String> data = new TreeMap<>();
        data.put("name", name);
        data.put("shipping_address", shippingAddress);
        data.put("billing_address", billingAddress);
        data.put("discount", String.valueOf(discount));
        data.put("email",email);
        data.put("regular_costumer",String.valueOf(regularCustomer));
        data.put("company",String.valueOf(company));
        data.put("tax_number",taxNumber);
        return data;
    }
}
