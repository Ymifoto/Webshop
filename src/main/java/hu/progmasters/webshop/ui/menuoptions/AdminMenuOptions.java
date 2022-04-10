package hu.progmasters.webshop.ui.menuoptions;

public enum AdminMenuOptions implements MenuOption {

    ORDERS("Orders"), NEW_PRODUCT("Add new product"), UPDATE_PRODUCT("Update product"), ADD_CATEGORY("Add product to category"), NEW_CATEGORY("Add new category"), UPDATE_CATEGORY("Update category"), CUSTOMER("Customers"), CREATE_TABLES("Create tables"), LOAD_DATA("Load data"), DROP_TABLES("Delete all table"), BACK("Back");

    private final String name;

    AdminMenuOptions(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
