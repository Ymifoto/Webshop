package hu.progmasters.webshop.ui.menuoptions;

public enum CustomersMenuOptions implements MenuOption {
    ADD_NEW("New cutromer"), UPDATE("Update customer"), SEARCH("Search customer"), SEARCH_BYID("Search customer by ID"), ALL_CUSTOMER("List all customer"), BACK("Back");

    private final String name;

    CustomersMenuOptions(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
