package hu.progmasters.webshop.domain;

import hu.progmasters.webshop.repositories.*;

public class WebShop {

    private final ProductRepository productRepository = new ProductRepository();
    private final CustomerRepository customerRepository = new CustomerRepository();
    private final CategoryRepository categoryRepository = new CategoryRepository();
    private final OrderRepository orderRepository = new OrderRepository();
    private final WebShopRepository webShopRepository = new WebShopRepository();

    public WebShop() {

    }
}
