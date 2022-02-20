package hu.progmasters.webshop.ui.menuoptions;

public enum CheckoutMenuOptions implements MenuOption {
    FINALIZE("Finalize order"), SHIPPING_METHOD("Change shipping method"), PAYMENT("Change payment method"), BACK("Back");

    private final String name;

    CheckoutMenuOptions(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
