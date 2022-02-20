package hu.progmasters.webshop.ui.menuoptions;

public enum ProductsMenuOptions implements MenuOption {
    ADD_NEW("New product"), UPDATE("Update product"), ON_SALE("On sale products"), IN_STOCK("Products in stock"), ADD_TO_CATEGORY("Add to category"), SEARCH("Search product"), ADD_TO_CART("Add to cart"), BACK("Back");

    private final String name;

    ProductsMenuOptions(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
