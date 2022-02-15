package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.domain.Customer;
import hu.progmasters.webshop.domain.ShoppingCart;
import hu.progmasters.webshop.handlers.LogHandler;
import hu.progmasters.webshop.ui.menuoptions.MainMenuOptions;

public class MainMenu extends Menu {

    private final ProductsMenu productsMenu = new ProductsMenu();
    private final CustomerMenu customerMenu = new CustomerMenu();
    private final CategoryMenu categoryMenu = new CategoryMenu();
    private final OrderMenu orderMenu = new OrderMenu();
    private final ShoppingCart shoppingCart = new ShoppingCart();


    public void menuOptions() {
        Customer customer;
        MainMenuOptions option;
        do {
            customer = shoppingCart.getCustomer();
            System.out.println(customer != null ? "Logged in: " + customer.getName() + " " + customer.getEmail() : "Not selected customer");
            option = (MainMenuOptions) getMenu(MainMenuOptions.values());
            switch (option) {
                case LOGIN:
                    setCustomer();
                    break;
                case REGISTER:
                    int id = customerMenu.addNewUser();
                    customer = customerMenu.getCustomerById(id);
                    if (customer != null) {
                        shoppingCart.setCustomer(customerMenu.getCustomerById(id));
                    }
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
        Customer customer;
        do {
            System.out.print("Give your email address: ");
            String email = inputHandler.getInputString();
            customer = customerMenu.getCustomerByEmail(email);
        } while (customer == null);
        shoppingCart.setCustomer(customer);
        System.out.println("Customer selected");
    }
}
