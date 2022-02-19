package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.domain.Customer;
import hu.progmasters.webshop.domain.ShoppingCart;
import hu.progmasters.webshop.handlers.LogHandler;
import hu.progmasters.webshop.ui.menuoptions.MainMenuOptions;

import java.util.Optional;

public class MainMenu extends Menu {

    private final ShoppingCart shoppingCart = new ShoppingCart();
    private final ProductsMenu productsMenu = new ProductsMenu(shoppingCart);
    private final CustomerMenu customerMenu = new CustomerMenu();
    private final CategoryMenu categoryMenu = new CategoryMenu(productsMenu.getProductRepository(), shoppingCart);
    private final OrderMenu orderMenu = new OrderMenu();
    private final CheckoutMenu checkout = new CheckoutMenu(shoppingCart);

    public void menuOptions() {
        Optional<Customer> customer;
        MainMenuOptions option;
        do {
            customer = Optional.ofNullable(shoppingCart.getCustomer());
            System.out.println(customer.map(value -> "Logged in: " + value.getName() + " " + value.getEmail()).orElse("Not selected customer"));
            option = (MainMenuOptions) getMenu(MainMenuOptions.values());
            switch (option) {
                case LOGIN:
                    setCustomer();
                    break;
                case REGISTER:
                    int id = customerMenu.addNewUser();
                    shoppingCart.setCustomer(customerMenu.getCustomerById(id).get());
                    break;
                case PRODUCTS:
                    productsMenu.menuOptions();
                    break;
                case CUSTOMERS:
                    customerMenu.menuOptions();
                    break;
                case CATEGORIES:
                    categoryMenu.menuOptions();
                    break;
                case CHECKOUT:
                    checkout.menuOptions();
                    break;
                case ORDERS:
                    orderMenu.menuOptions();
                    break;
                case LOGOUT:
                    shoppingCart.setCustomer(null);
                    break;
                case QUIT:
                    LogHandler.writeLog();
            }
        } while (option != MainMenuOptions.QUIT);
    }

    protected void setCustomer() {
        Optional<Customer> customer;
        System.out.print("Give your email address: ");
        String email = inputHandler.getInputString();
        customer = customerMenu.getCustomerByEmail(email);
        if (customer.isPresent()) {
            shoppingCart.setCustomer(customer.get());
            LogHandler.addLog("Customer logged in: " + customer.get().getId() + ", " + customer.get().getName() + ", " + customer.get().getEmail());
            System.out.println("Logged in");
        } else {
            System.out.println("Not found customer");
        }
    }
}
