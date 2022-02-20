package hu.progmasters.webshop.ui.menuoptions;

public enum CategoryMenuOptions implements MenuOption {
    ADD_NEW("Add new category"), LIST("List categories"), SELECT("Select category"), ADD_TO_CART("Add to cart"), UPDATE("Update category"), BACK("Back");

    private final String name;

    CategoryMenuOptions(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
