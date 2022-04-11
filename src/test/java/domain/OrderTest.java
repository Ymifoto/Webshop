package domain;

import hu.progmasters.webshop.domain.Address;
import hu.progmasters.webshop.domain.Customer;
import hu.progmasters.webshop.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {

    private Order order;

    @BeforeEach
    void createOrder() {
        order = new Order(1,getCustomer(),"Home delivery", "Credit card",1600, 99000, new Date(), false);
    }

    @Test
    void getOrderAttributes() {
       assertEquals(1, order.getId());
       assertTrue(order.toString().length()>0);
    }

    private Customer getCustomer() {
        Address shippingAddress = new Address(0, 1, 1000, "Budapest", "Gyár utca 2.", false);
        Address billingAddress = new Address(0, 1, 5000, "Szeged", "Gyár utca 2.", true);
        Customer customer = new Customer(1,"Jhon Doe",shippingAddress,"jhon_doe@gmail.com","SzélKasza Bt.",true,"111111-1-11", false);
        customer.setBillingAddress(billingAddress);
        return customer;
    }



}
