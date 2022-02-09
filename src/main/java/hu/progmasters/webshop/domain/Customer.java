package hu.progmasters.webshop.domain;

public class Customer {

    private final int id;
    private String name;
    private String shipping_address;
    private String billing_address;
    private int discount;
    private String email;
    private boolean regular_customer;
    private boolean company;
    private String tax_number;

    public Customer(int id, String name, String shipping_address, String billing_address, int discount, String email, boolean regular_customer, boolean company, String tax_number) {
        this.id = id;
        this.name = name;
        this.shipping_address = shipping_address;
        this.billing_address = billing_address;
        this.discount = discount;
        this.email = email;
        this.regular_customer = regular_customer;
        this.company = company;
        this.tax_number = tax_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public String getBilling_address() {
        return billing_address;
    }

    public void setBilling_address(String billing_address) {
        this.billing_address = billing_address;
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

    public String getTax_number() {
        return tax_number;
    }

    public void setTax_number(String tax_number) {
        this.tax_number = tax_number;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append(System.lineSeparator());
        sb.append("Name:").append(name).append(System.lineSeparator());
        sb.append("Email: ").append(email).append(System.lineSeparator());
        sb.append("Shipping address: ").append(shipping_address).append(System.lineSeparator());
        sb.append("Billing address: ").append(billing_address).append(System.lineSeparator());
        sb.append("Discount: ").append(discount).append("% ").append(System.lineSeparator());
        sb.append("Regular customer: ").append(regular_customer ? "yes" : "no").append(System.lineSeparator());
        sb.append(company ? "Tax number: " + tax_number + System.lineSeparator() : "");
        return sb.toString();
    }
}
