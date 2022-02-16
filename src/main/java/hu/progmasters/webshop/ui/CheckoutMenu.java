package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.domain.Customer;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.ShoppingCart;
import hu.progmasters.webshop.handlers.OutputHandler;
import hu.progmasters.webshop.ui.menuoptions.CheckoutMenuOptions;
import hu.progmasters.webshop.ui.menuoptions.CheckoutRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CheckoutMenu extends Menu {

    private String shippingMethod;
    private int shippingCost;
    private int shippingMethodId;
    private int orderTotal;
    private final ShoppingCart shoppingCart;
    private final CheckoutRepository checkoutRepository = new CheckoutRepository();

    public CheckoutMenu(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void menuOptions() {
        CheckoutMenuOptions option;
        do {
            checkoutInformation();
            orderTotal = getOrderTotal() + shippingCost;
            shippingMethod = checkoutRepository.getShippingMethodName(shippingMethodId);
            option = (CheckoutMenuOptions) getMenu(CheckoutMenuOptions.values());
            switch (option) {
                case SHIPPING_METHOD:
                    chooseShippingMethod();
                    break;
                case FINALIZE:
                    if (shoppingCart.getCustomer() == null) {
                        OutputHandler.outputRed("Not logged in");
                    }
                    if (shoppingCart.getProductList().size() == 0) {
                        OutputHandler.outputRed("No products in cart");
                    } else {
                        finalizeOrder();
                    }
                    break;
                case BACK:
                    break;
            }
        } while (option != CheckoutMenuOptions.BACK);
    }

    private void finalizeOrder() {
        Map<String, String> order = new TreeMap<>();
        order.put("customer_id", String.valueOf(shoppingCart.getCustomer().getId()));
        order.put("shipping_method", String.valueOf(shippingMethodId));
        order.put("shipping_cost", String.valueOf(shippingCost));
        checkoutRepository.saveOrder(order, getOrderedProductsId());
    }

    private void checkoutInformation() {
        Customer customer = shoppingCart.getCustomer();
        OutputHandler.outputYellow(customer != null ? customer.toString() : "Not logged in");
        if (shoppingCart.getProductList().size() > 0) {
            shoppingCart.getProductList().forEach(p -> OutputHandler.outputCyan(p.toString()));
            System.out.print(shippingMethodId != 0 ? shippingMethod + " " + shippingCost : "");
        }
    }

    private void chooseShippingMethod() {
        Map<Integer, Integer> shippingMethods = checkoutRepository.getShippingMethods(orderTotal);
        System.out.print("Choose shipping method: ");
        shippingMethodId = inputHandler.getInputNumber();
        shippingCost = shippingMethods.get(shippingMethodId);
    }

    private int getOrderTotal() {
        return shoppingCart.getProductList().stream().mapToInt(Product::getPrice).sum();
    }

    private List<Integer> getOrderedProductsId() {
        List<Integer> orderedProducts = new ArrayList<>();
        shoppingCart.getProductList().forEach(p -> orderedProducts.add(p.getId()));
        return orderedProducts;
    }
}
