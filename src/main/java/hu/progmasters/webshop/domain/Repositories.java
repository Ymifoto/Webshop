package hu.progmasters.webshop.domain;

import hu.progmasters.webshop.handlers.InputHandler;
import hu.progmasters.webshop.handlers.LogHandler;
import hu.progmasters.webshop.repositories.*;

public abstract class Repositories {

    protected final ProductRepository productRepository = new ProductRepository();
    protected final CustomerRepository customerRepository = new CustomerRepository();
    protected final CategoryRepository categoryRepository = new CategoryRepository();
    protected final OrderRepository orderRepository = new OrderRepository();
    protected final InputHandler inputHandler = new InputHandler();
    protected ShoppingCart shoppingCart;

    public Repositories() {
        LogHandler.addLog("Start program");
        LogHandler.writeLog();
    }

    protected void setCustomer() {
        customerRepository.getAllCustomer().forEach(System.out::println);
        Customer customer;
        do {
        System.out.print("Give customer id: ");
        int customerId = inputHandler.getInputNumber();
        customer = customerRepository.getCustomerById(customerId);
        } while (customer == null);
        shoppingCart = new ShoppingCart(customer);
        System.out.println("Customer selected");
    }
}
