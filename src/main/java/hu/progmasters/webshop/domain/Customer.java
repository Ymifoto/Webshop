package hu.progmasters.webshop.domain;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Customer implements Comparable<Customer> {

    private int id;
    private String name;
    private final Address shippingAddress;
    private final Address billingAddress;
    private String email;
    private String companyName;
    private boolean company;
    private String taxNumber;

    public Customer(int id, String name, Address shippingAddress, Address billingAddress, String email, String companyName, boolean company, String taxNumber) {
        this.id = id;
        this.name = name;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.email = email;
        this.companyName = companyName;
        this.company = company;
        this.taxNumber = taxNumber;
    }

    public Customer(String name, Address shippingAddress, Address billingAddress, String email, String companyName, boolean company, String taxNumber) {
        this.name = name;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append(", ");
        sb.append("Name:").append(name).append(", ");
        sb.append("Email: ").append(email).append(System.lineSeparator());
        sb.append("Shipping address: ").append(shippingAddress).append(", ");
        sb.append("Billing address: ").append(billingAddress).append(System.lineSeparator());
        sb.append(company ? "Company name: " + companyName + " " : "");
        sb.append(company ? "Tax number: " + taxNumber + System.lineSeparator() : "");
        return sb.toString();
    }

    public Map<String, String> getData() {
        Map<String, String> data = new TreeMap<>();
        data.put("name", name);
        data.put("email",email);
        data.put("company_name",companyName);
        data.put("company",company ? "1" : "0");
        data.put("tax_number",taxNumber);
        return data;
    }

    public void updateData(Map<String, String> data) {
        if (data.get("name").length() > 0) {
            name = data.get("name");
        }
        if (data.get("email").length() > 0) {
            email = data.get("email");
        }
        if (data.get("company").equals("1")) {
            company = true;
            if (data.get("company_name").length() > 0) {
                companyName = data.get("company_name");
            }
            if (data.get("tax_number").length() > 0) {
                taxNumber = data.get("tax_number");
            }
        } else {
            company = false;
            taxNumber = null;
            companyName = null;
        }
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return id == customer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Customer o) {
        return id - o.getId();
    }
}
