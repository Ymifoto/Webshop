package hu.progmasters.webshop.ui.menuoptions;

public enum OrdersMenuOptions implements MenuOption {
    IN_PROGRESS_ORDERS("Orders in progress"), ALL_ORDERS("All orders"), SEARCH("Search order"), SET_SHIPPED("Close order"), BACK("Back");

    private final String name;

    OrdersMenuOptions(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
