package hu.progmasters.webshop.ui.menuoptions;

public enum AdminMenuOptions implements MenuOption {

    CREATE_TABLES("Create tables"),LOAD_DATA("Load data"),DELETE_DATA("Delete all data"),BACK("Back");

    private final String name;

    AdminMenuOptions(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
