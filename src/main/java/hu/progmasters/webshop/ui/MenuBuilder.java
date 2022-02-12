package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.handlers.InputHandler;
import hu.progmasters.webshop.ui.menuoptions.*;

public class MenuBuilder {

    InputHandler inputHandler = new InputHandler();


    public void startMenu() {
        mainMenuOptions();
    }

    private Menu getMenu(Menu[] menuOptions) {
        int counter = 1;
        for (Menu menuOption : menuOptions) {
            System.out.println(counter + ". " + menuOption);
            counter++;
        }
        System.out.print("Choose an option: ");
        return menuOptions[inputHandler.getInputNumber() - 1];
    }

    private void mainMenuOptions() {
        MainMenu option = (MainMenu) getMenu(MainMenu.values());
        switch (option) {
            case PRODUCTS:
                productMenuOptions();
                break;
            case ORDERS:
                ordersMenuOptions();
                break;
            case CUSTOMERS:
                customerMenuOptions();
                break;
            case QUIT:
                break;
        }
    }

    private void productMenuOptions() {
        ProductsMenu option = (ProductsMenu) getMenu(ProductsMenu.values());
        switch (option) {
            case ADD_NEW:
                System.out.println("Add product");
                break;
            case UPDATE:
                System.out.println("Update product");
                break;
            case ONSALE:
                System.out.println("On sale products");
            case INSTOCK:
                System.out.println("Products in stock");
                break;
            case SEARCH:
                System.out.println("Search products");
                break;
            case BACK:
                mainMenuOptions();
                break;
        }
    }

    private void customerMenuOptions() {
        CustomersMenu option = (CustomersMenu) getMenu(CustomersMenu.values());
        switch (option) {
            case ADD_NEW:
                System.out.println("Add customer");
                break;
            case UPDATE:
                System.out.println("Update customer");
                break;
            case SEARCH:
                System.out.println("Search customer");
                break;
            case BACK:
                mainMenuOptions();
                break;

        }
    }

    private void ordersMenuOptions() {
        OrdersMenu option = (OrdersMenu) getMenu(OrdersMenu.values());
        switch (option) {
            case NEW_ORDER:
                System.out.println("New order");
            case ALL_ORDERS:
                System.out.println("All orders");
            case SEARCH:
                System.out.println("Search customer");
                break;
            case BACK:
                mainMenuOptions();
                break;
        }
    }
}
