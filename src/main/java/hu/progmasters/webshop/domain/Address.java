package hu.progmasters.webshop.domain;

import java.util.Map;
import java.util.Objects;
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

    public Address copyShippingToBilling() {
        return new Address(0,customerId,zip,city,street,true);
    }

    public void updateData(Map<String, String> data) {
        if (data.get("zip") != null && data.get("zip").length() > 0) {
            zip = Integer.parseInt(data.get("zip"));
        }
        if (data.get("city") != null && data.get("city").length() > 0) {
            city = data.get("city");
        }
        if (data.get("street") != null && data.get("street").length() > 0) {
            street = data.get("street");
        }
    }

    public Map<String, String> getData() {
        Map<String, String> data = new TreeMap<>();
        data.put("zip", String.valueOf(zip));
        data.put("city", city);
        data.put("street", street);
        data.put("customer_id", String.valueOf(customerId));
        data.put("billing_address", billingAddress ? "1" : "0");
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

    public void setBillingAddress(boolean billingAddress) {
        this.billingAddress = billingAddress;
    }

    public int getId() {
        return id;
    }

    public int getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    @Override
    public String toString() {
        String address = zip + " " + city + ", " + street;
        return billingAddress ? "Billing address: " + address : "Shipping address: " + address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return zip == address.zip && Objects.equals(city, address.city) && Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zip, city, street);
    }
}
