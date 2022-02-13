package hu.progmasters.webshop.domain;

import hu.progmasters.webshop.handlers.LogHandler;
import hu.progmasters.webshop.ui.menuoptions.*;

public class WebShop extends Repositories {

    public WebShop() {
        LogHandler.create();
        LogHandler.addLog("Start program");
        LogHandler.writeLog();
    }

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
        MainMenu option;
        do {
            option = (MainMenu) getMenu(MainMenu.values());
            switch (option) {
                case SET_CUSTOMER:
                    setCustomer();
                    break;
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
            }
        } while (option != MainMenu.QUIT);
    }

    private void productMenuOptions() {
        ProductsMenu option;
        do {
            option = (ProductsMenu) getMenu(ProductsMenu.values());
            switch (option) {
                case ADD_NEW:
                    System.out.println("Add product");
                    break;
                case UPDATE:
                    System.out.println("Update product");
                    break;
                case ONSALE:
                    productRepository.getStockOrDiscountProducts("on_sale").forEach(System.out::println);
                    break;
                case INSTOCK:
                    productRepository.getStockOrDiscountProducts("in_stock").forEach(System.out::println);
                    break;
                case SEARCH:
                    System.out.println("Search products");
                    break;
                case BACK:
                    mainMenuOptions();
                    break;
            }
        } while (option != ProductsMenu.BACK);
    }

    private void customerMenuOptions() {
        CustomersMenu option;
        do {
            option = (CustomersMenu) getMenu(CustomersMenu.values());
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
        } while (option != CustomersMenu.BACK);
    }

    private void ordersMenuOptions() {
        OrdersMenu option;
        do {
            option = (OrdersMenu) getMenu(OrdersMenu.values());
            switch (option) {
                case NEW_ORDER:
                    System.out.println("New order");
                case ALL_ORDERS:
                    orderRepository.getAllOrders().forEach(System.out::println);
                    break;
                case SEARCH:
                    System.out.println("Search customer");
                    break;
                case BACK:
                    mainMenuOptions();
                    break;
            }
        } while (option != OrdersMenu.BACK);
    }
}
