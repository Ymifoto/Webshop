package hu.progmasters.webshop.domain;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Customer implements Comparable<Customer> {

    private final Integer id;
    private String name;
    private final Address shippingAddress;
    private Address billingAddress;
    private String email;
    private String companyName;
    private boolean company;
    private String taxNumber;
    private boolean sameAddress;

    public Customer(Integer id, String name, Address shippingAddress, String email, String companyName, boolean company, String taxNumber, boolean sameAddress) {
        this.id = id;
        this.name = name;
        this.shippingAddress = shippingAddress;
        this.email = email;
        this.companyName = companyName;
        this.company = company;
        this.taxNumber = taxNumber;
        this.sameAddress = sameAddress;
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

    public boolean isSameAddress() {
        return sameAddress;
    }

    public void setSameAddress(boolean sameAddress) {
        this.sameAddress = sameAddress;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append(", ");
        sb.append("Email: ").append(email).append(", ");
        sb.append(shippingAddress).append(", ");
        if (!billingAddress.equals(shippingAddress)) {
            sb.append(billingAddress);
        }
        if (company) {
            sb.append(", Company name: " + companyName);
            sb.append(", Tax number: " + taxNumber);
        }
        return sb.toString();
    }

    public Map<String, String> getData() {
        Map<String, String> data = new TreeMap<>();
        data.put("name", name);
        data.put("email", email);
        data.put("company_name", companyName);
        data.put("company", company ? "1" : "0");
        data.put("same_address", sameAddress ? "1" : "0");
        data.put("tax_number", taxNumber);
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

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompany(boolean company) {
        this.company = company;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
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
