package hu.progmasters.webshop.domain;

public class Customer {

    private final int id;
    private String name;
    private Address address;
    private int discount;
    private String email;
    private boolean regular_customer;
    private boolean company;

    public Customer(int id, String name, Address address, int discount, String email, boolean regular_customer, boolean company) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.discount = discount;
        this.email = email;
        this.regular_customer = regular_customer;
        this.company = company;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
        return regular_customer;
    }

    public void setRegular_customer(boolean regular_customer) {
        this.regular_customer = regular_customer;
    }

    public boolean isCompany() {
        return company;
    }

    public void setCompany(boolean company) {
        this.company = company;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append(", ");
        sb.append("Name:").append(name).append(", ");
        sb.append("Email: ").append(email).append(System.lineSeparator());
        sb.append("Shipping address: ").append(address.getShippingAddress()).append(", ");
        sb.append("Billing address: ").append(address.getBillingAddress()).append(System.lineSeparator());
        sb.append("Discount: ").append(discount).append("% ").append(", ");
        sb.append("Regular customer: ").append(regular_customer ? "yes" : "no").append(", ");
        sb.append(company ? "Tax number: " + address.getTaxNumber() + System.lineSeparator() : "");
        return sb.toString();
    }
}
