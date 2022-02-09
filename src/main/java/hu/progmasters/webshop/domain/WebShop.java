package hu.progmasters.webshop.domain;

import hu.progmasters.webshop.repositories.*;

public class WebShop {

    ProductRepository productRepository = new ProductRepository();
    CustomerRepository customerRepository = new CustomerRepository();
    CategoryRepository categoryRepository = new CategoryRepository();
    OrderRepository orderRepository = new OrderRepository();
    WebShopRepository webShopRepository = new WebShopRepository();

    public WebShop() {

    }
}
