package hu.progmasters.webshop.ui.menuoptions;

public enum ProductsMenuOptions implements MenuOption {
    ON_SALE("On sale products"), IN_STOCK("Products in stock"), SEARCH("Search product"), LIST_PRODUCT_TYPES("List product types"), ADD_TO_CART("Add to cart"), BACK("Back");

    private final String name;

    ProductsMenuOptions(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
