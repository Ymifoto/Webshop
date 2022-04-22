package hu.progmasters.webshop.services;

import hu.progmasters.webshop.checkers.Checker;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.ShoppingCart;
import hu.progmasters.webshop.handlers.InputHandler;
import hu.progmasters.webshop.handlers.OutputHandler;
import hu.progmasters.webshop.repositories.*;

public abstract class Services {

    protected static ProductRepository productRepository;
    protected static CategoryRepository categoryRepository;
    protected static CustomerRepository customerRepository;
    protected static OrderRepository orderRepository;
    protected static CheckoutRepository checkoutRepository;
    protected static AdminRepository adminRepository;

    public void addToCart(ShoppingCart shoppingCart) {
        System.out.print("Give a product id: ");
        Product product = productRepository.getProductById(InputHandler.getInputNumber());
        if (product != null) {
            addProductToCart(product, shoppingCart);
        }
        System.out.println("Cant find product");
    }

    protected void addProductToCart(Product product, ShoppingCart shoppingCart) {
        if (product.isInStock()) {
            shoppingCart.addProduct(product);
        } else {
            OutputHandler.outputRed("Product out of stock!");
        }
    }

    protected String checkInputData(Checker checker, String oldData, String question) {
        String data;
        do {
            System.out.print(oldData != null ? "(" + oldData + ") " : "");
            System.out.print(question);
            data = InputHandler.getInputString();
        } while (!checker.check(data));
        return data;
    }

    protected boolean yesOrNo(String question) {
        String answer;
        do {
            System.out.print(question);
            answer = InputHandler.getInputString().toLowerCase();
            if (!answer.equals("y") && !answer.equals("n")) {
                System.out.println("Wrong choice!");
            }
        } while (!answer.equals("y") && !answer.equals("n"));
        return answer.equals("y");
    }

    public static void setProductRepository(ProductRepository productRepository) {
        Services.productRepository = productRepository;
    }

    public static void setCategoryRepository(CategoryRepository categoryRepository) {
        Services.categoryRepository = categoryRepository;
    }

    public static void setCustomerRepository(CustomerRepository customerRepository) {
        Services.customerRepository = customerRepository;
    }

    public static void setOrderRepository(OrderRepository orderRepository) {
        Services.orderRepository = orderRepository;
    }

    public static void setCheckoutRepository(CheckoutRepository checkoutRepository) {
        Services.checkoutRepository = checkoutRepository;
    }

    public static void setAdminRepository(AdminRepository adminRepository) {
        Services.adminRepository = adminRepository;
    }
}
