package hu.progmasters.webshop.domain;

import java.util.Map;
import java.util.TreeMap;

public class Address {

    private int id;
    private int customerId;
    private int zip;
    private String city;
    private String street;
    private boolean billingAddress;


    public Address() {
    }

    public Address(int id, int customerId, int zip, String city, String street, boolean billingAddress) {
        this.id = id;
        this.customerId = customerId;
        this.zip = zip;
        this.city = city;
        this.street = street;
        this.billingAddress = billingAddress;
    }

    public Address copy() {
        return new Address(id, customerId, zip, city, street, billingAddress);
    }

    public void updateData(Map<String, String> data) {
        if (data.get("zip").length() > 0) {
            zip = Integer.parseInt(data.get("zip"));
        }
        if (data.get("city").length() > 0) {
            city = data.get("city");
        }
        if (data.get("street").length() > 0) {
            street = data.get("street");
        }
    }


    public Map<String, String> getData() {
        Map<String, String> data = new TreeMap<>();
        data.put("zip", String.valueOf(zip));
        data.put("city", city);
        data.put("street", street);
        data.put("customer_id", String.valueOf(customerId));
        data.put("billing_address",billingAddress ? "1" : "0");
        return data;
    }

    public Address setZip(int zip) {
        this.zip = zip;
        return this;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public Address setStreet(String street) {
        this.street = street;
        return this;
    }

    public Address setId(int id) {
        this.id = id;
        return this;
    }

    public Address setCustomerId(int customerId) {
        this.customerId = customerId;
        return this;
    }

    public Address setBillingAddress(boolean billingAddress) {
        this.billingAddress = billingAddress;
        return this;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        String address = zip + " " + city + ", " + street;
        return billingAddress ? "Billing address: " + address : "Shipping address: " + address;
    }
}
