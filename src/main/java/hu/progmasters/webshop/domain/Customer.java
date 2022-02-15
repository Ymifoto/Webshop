package hu.progmasters.webshop.domain;

import java.util.Map;
import java.util.TreeMap;

public class Customer {

    private final int id;
    private String name;
    private String shippingAddress;
    private String billingAddress;
    private String email;
    private String companyName;
    private boolean company;
    private String taxNumber;

    public Customer(int id, String name, String shippingAddress, String billingAddress, String email, String companyName, boolean company, String taxNumber) {
        this.id = id;
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
        data.put("shipping_address", shippingAddress);
        data.put("billing_address", billingAddress);
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
        if (data.get("shipping_address").length() > 0) {
            shippingAddress = data.get("shipping_address");
        }
        if (data.containsKey("billing_address") && data.get("billing_address").length() > 0) {
            billingAddress = data.get("billing_address");
        } else {
            billingAddress = shippingAddress;
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
}
