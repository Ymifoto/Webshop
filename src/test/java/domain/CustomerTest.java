package domain;

import hu.progmasters.webshop.domain.Address;
import hu.progmasters.webshop.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Map<String,String> customerData = new TreeMap<>();
    private Map<String,String> addressData = new TreeMap<>();



    @BeforeEach
    void setCustomer() {
        customer = getCustomer();
    }


    @Test
    void checkCustomerDifferentAddress() {
        assertFalse(customer.isSameAddress());
        assertNotEquals(customer.getShippingAddress(), customer.getBillingAddress());
    }

    @Test
    void checkCustomerUpdate() {
        addressData.put("city","Debrecen");
        customer.getShippingAddress().updateData(addressData);
        assertEquals("Debrecen",customer.getShippingAddress().getCity());
    }

    @Test
    void checkCustomerNull() {
        addressData.put("city",null);
        customer.getShippingAddress().updateData(addressData);
        assertNotEquals(null,customer.getShippingAddress().getCity());
    }

    @Test
    void compareCustomersTest() {
        Customer customer1 = getCustomer();
        Customer customer2 = getCustomer();
        assertEquals(customer1,customer2);
    }

    private Customer getCustomer() {
        shippingAddress = new Address(0,1,1000,"Budapest","Gyár utca 2.", false);
        billingAddress = new Address(0,1,5000,"Szeged","Gyár utca 2.", true);
        customer = new Customer(1,"Jhon Doe",shippingAddress,"jhon_doe@gmail.com","SzélKasza Bt.",true,"111111-1-11", false);
        customer.setBillingAddress(billingAddress);
        List.of("zip","city","street").forEach(v -> addressData.put(v,null));
        List.of("name","email","company","company_name","tax_number").forEach(v -> customerData.put(v,null));
        return customer;
    }
}
