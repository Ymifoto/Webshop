package hu.progmasters.webshop.domain;

public class Address {

    private String shippingAddress;
    private String billingAddress;
    private String taxNumber;

    public Address(String shippingAddress, String billingAddress, String taxNumber) {
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.taxNumber = taxNumber;
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
}
