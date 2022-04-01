package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.domain.Customer;
import hu.progmasters.webshop.handlers.LogHandler;
import hu.progmasters.webshop.handlers.OutputHandler;
import hu.progmasters.webshop.ui.menuoptions.MainMenuOptions;

import java.util.Optional;

public class MainMenu extends Menu {

    private final ProductsMenu productsMenu = new ProductsMenu();
    private final CustomerMenu customerMenu = new CustomerMenu();
    private final CategoryMenu categoryMenu = new CategoryMenu();
    private final CheckoutMenu checkoutMenu = new CheckoutMenu();
    private final AdminMenu adminMenu = new AdminMenu();

    public void menuOptions() {
        Optional<Customer> customer;
        MainMenuOptions option;
        do {
            customer = Optional.ofNullable(shoppingCart.getCustomer());
            OutputHandler.outputGreen(customer.map(value -> "Logged in: " + value.getName() + " " + value.getEmail()).orElse("You are not logged in"));
            option = (MainMenuOptions) getMenu(MainMenuOptions.values());
            switch (option) {
                case LOGIN:
                    setCustomer();
                    break;
                case REGISTER:
                    int id = customerMenu.addNewUser();
                    shoppingCart.setCustomer(customerMenu.getCustomerById(id).orElse(null));
                    break;
                case PRODUCTS:
                    productsMenu.menuOptions();
                    break;
                case CATEGORIES:
                    categoryMenu.menuOptions();
                    break;
                case CHECKOUT:
                    checkoutMenu.menuOptions();
                    break;
                case LOGOUT:
                    shoppingCart.setCustomer(null);
                    break;
                case ADMIN:
                    adminMenu.menuOptions();
                    break;
                case QUIT:
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
