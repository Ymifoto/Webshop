package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.domain.Customer;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.ShoppingCart;
import hu.progmasters.webshop.handlers.OutputHandler;
import hu.progmasters.webshop.repositories.CheckoutRepository;
import hu.progmasters.webshop.ui.menuoptions.CheckoutMenuOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CheckoutMenu extends Menu {

    private String shippingMethod;
    private int shippingCost;
    private int shippingMethodId;
    private int paymentMethodId;
    private String paymentMethod;
    private int generalTotal;
    private final ShoppingCart shoppingCart;
    private final CheckoutRepository checkoutRepository = new CheckoutRepository();

    public CheckoutMenu(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void menuOptions() {
        CheckoutMenuOptions option;
        do {
            checkoutInformation();
            shippingMethod = checkoutRepository.getShippingMethodName(shippingMethodId);
            option = (CheckoutMenuOptions) getMenu(CheckoutMenuOptions.values());
            switch (option) {
                case SHIPPING_METHOD:
                    chooseShippingMethod();
                    break;
                case PAYMENT:
                    choosePaymentMethod();
                    break;
                case FINALIZE:
                    if (shoppingCart.getCustomer() == null) {
                        OutputHandler.outputRed("Not logged in");
                    } else if (shoppingCart.getProductList().size() == 0) {
                        OutputHandler.outputRed("No products in cart");
                    } else if (shippingMethodId == 0) {
                        OutputHandler.outputRed("No shipping method selected!");
                    } else if (paymentMethodId == 0) {
                        OutputHandler.outputRed("No payment method selected!");
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
        order.put("order_total", String.valueOf(generalTotal + shippingCost));
        order.put("payment_method",String.valueOf(paymentMethodId));
        checkoutRepository.saveOrder(order, getOrderedProductsId());
        shoppingCart.getProductList().clear();
    }

    private void checkoutInformation() {
        generalTotal = getGeneralTotal();
        Customer customer = shoppingCart.getCustomer();
        OutputHandler.outputYellow(customer != null ? customer.toString() : "Not logged in");
        OutputHandler.outputYellow("Total products price: " + generalTotal);
        OutputHandler.outputYellow(shippingMethodId != 0 ? shippingMethod + " " + shippingCost : "Not selected shipping method");
        OutputHandler.outputYellow(paymentMethodId != 0 ? paymentMethod : "Not selected payment method");
        if (shoppingCart.getProductList().size() > 0) {
            shoppingCart.getProductList().forEach(p -> OutputHandler.outputCyan(p.toString()));
        }
    }

    private void chooseShippingMethod() {
        Map<Integer, Integer> shippingMethods = checkoutRepository.getShippingMethods(generalTotal);
        System.out.print("Choose shipping method: ");
        shippingMethodId = inputHandler.getInputNumber();
        shippingMethod = checkoutRepository.getShippingMethodName(shippingMethodId);
        shippingCost = shippingMethods.get(shippingMethodId);
    }

    private void choosePaymentMethod() {
        Map<Integer, String> paymentMethods = checkoutRepository.getPaymentMethods();
        paymentMethods.forEach((key, value) -> OutputHandler.outputYellow(key + ". " + value));
        do {
            System.out.print("Choose payment method: ");
            paymentMethodId = inputHandler.getInputNumber();

        } while (paymentMethodId < 0 || paymentMethodId > paymentMethods.size());
        paymentMethod = paymentMethods.get(paymentMethodId);
    }

    private int getGeneralTotal() {
        return shoppingCart.getProductList().stream().mapToInt(Product::getPrice).sum();
    }

    private List<Integer> getOrderedProductsId() {
        List<Integer> orderedProducts = new ArrayList<>();
        shoppingCart.getProductList().forEach(p -> orderedProducts.add(p.getId()));
        return orderedProducts;
    }
}
