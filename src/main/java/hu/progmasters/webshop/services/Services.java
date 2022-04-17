package hu.progmasters.webshop.services;

import hu.progmasters.webshop.checkers.Checker;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.ShoppingCart;
import hu.progmasters.webshop.handlers.InputHandler;
import hu.progmasters.webshop.handlers.OutputHandler;
import hu.progmasters.webshop.repositories.*;

public abstract class Services {

    protected ProductRepository productRepository;
    protected CategoryRepository categoryRepository;
    protected CustomerRepository customerRepository;
    protected OrderRepository orderRepository;
    protected CheckoutRepository checkoutRepository;
    protected AdminRepository adminRepository;

    public void addToCart(ShoppingCart shoppingCart) {
        System.out.print("Give a product id: ");
        addProductToCart(productRepository.getProductById(InputHandler.getInputNumber()), shoppingCart);
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

    protected void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    protected void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    protected void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    protected void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    protected void setCheckoutRepository(CheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }

    protected void setAdminRepository(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
}
