package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.repositories.CheckoutRepository;
import hu.progmasters.webshop.services.CheckOutService;
import hu.progmasters.webshop.ui.menuoptions.CheckoutMenuOptions;

public class CheckoutMenu extends Menu {

    private final CheckOutService checkOutService = new CheckOutService(new CheckoutRepository());

    public void menuOptions() {
        CheckoutMenuOptions option;
        do {
            checkOutService.checkoutInformation(shoppingCart);
            option = (CheckoutMenuOptions) getMenu(CheckoutMenuOptions.values());
            switch (option) {
                case SHIPPING_METHOD:
                    checkOutService.chooseShippingMethod();
                    break;
                case PAYMENT:
                    checkOutService.choosePaymentMethod();
                    break;
                case REMOVE:
                    checkOutService.removeProduct(shoppingCart);
                    break;
                case FINALIZE:
                    if (checkOutService.finalizeOrder(shoppingCart)) {
                        option = CheckoutMenuOptions.BACK;
                    }
                    break;
                case BACK:
            }
        } while (option != CheckoutMenuOptions.BACK);
    }
}
