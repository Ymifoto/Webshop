package hu.progmasters.webshop.domain;

import hu.progmasters.webshop.handlers.LogHandler;
import hu.progmasters.webshop.repositories.*;

public class WebShop {

    private final WebShopRepository webShopRepository = new WebShopRepository();
    private final ProductRepository productRepository = new ProductRepository(webShopRepository);
    private final CustomerRepository customerRepository = new CustomerRepository();
    private final CategoryRepository categoryRepository = new CategoryRepository(webShopRepository);
    private final OrderRepository orderRepository = new OrderRepository();

    public WebShop() {
        LogHandler.addLog("Start program");
        LogHandler.writeLog();
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

}
