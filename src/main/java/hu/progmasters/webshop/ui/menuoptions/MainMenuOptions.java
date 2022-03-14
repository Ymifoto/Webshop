package hu.progmasters.webshop.ui.menuoptions;

public enum MainMenuOptions implements MenuOption {
    LOGIN("Login"), REGISTER("Register"), PRODUCTS("Product menu"), CATEGORIES("Category menu"), CUSTOMERS("Customer menu"), ORDERS("Orders menu"), CHECKOUT("Checkout"), LOGOUT("Logout"), ADMIN("Admin menu"), QUIT("Quit");

    private final String name;

    MainMenuOptions(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
