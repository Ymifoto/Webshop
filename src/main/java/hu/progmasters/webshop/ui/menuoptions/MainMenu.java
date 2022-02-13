package hu.progmasters.webshop.ui.menuoptions;

import hu.progmasters.webshop.domain.Customer;
import hu.progmasters.webshop.domain.ShoppingCart;
import hu.progmasters.webshop.ui.CustomerMenu;
import hu.progmasters.webshop.ui.Menu;
import hu.progmasters.webshop.ui.OrderMenu;
import hu.progmasters.webshop.ui.ProductsMenu;

public class MainMenu implements Menu {

    private final ProductsMenu productsMenu = new ProductsMenu();
    private final CustomerMenu customerMenu = new CustomerMenu();
    private final OrderMenu orderMenu = new OrderMenu();
    private final ShoppingCart shoppingCart = new ShoppingCart();


    public void menuOptions() {
        Customer customer = shoppingCart.getCustomer();
        System.out.println(customer != null ? "Selected customer: " + customer.getName() + " " + customer.getEmail() : "Not selected customer");
        MainMenuOptions option;
        do {
            option = (MainMenuOptions) getMenu(MainMenuOptions.values());
            switch (option) {
                case SET_CUSTOMER:
                    setCustomer();
                    break;
                case PRODUCTS:
                    productsMenu.menuOptions();
                    break;
                case ORDERS:
                    orderMenu.menuOptions();
                    break;
                case CUSTOMERS:
                    customerMenu.menuOptions();
                    break;
                case QUIT:
            }
        } while (option != MainMenuOptions.QUIT);
    }

    protected void setCustomer() {
        customerMenu.getCustomerRepository().getAllCustomer().forEach(System.out::println);
        Customer customer;
        do {
            System.out.print("Give customer id: ");
            int customerId = inputHandler.getInputNumber();
            customer = customerMenu.getCustomerRepository().getCustomerById(customerId);
        } while (customer == null);
        shoppingCart.setCustomer(customer);
        System.out.println("Customer selected");
    }
}
