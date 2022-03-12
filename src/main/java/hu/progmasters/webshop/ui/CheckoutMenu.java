package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.domain.Customer;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.ShoppingCart;
import hu.progmasters.webshop.handlers.OutputHandler;
import hu.progmasters.webshop.repositories.CheckoutRepository;
import hu.progmasters.webshop.ui.menuoptions.CheckoutMenuOptions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
                case REMOVE:
                    System.out.print("Give a product ID: ");
                    removeProduct(inputHandler.getInputNumber());
                    break;
                case BACK:

            }
        } while (option != CheckoutMenuOptions.BACK);
    }

    private void finalizeOrder() {
        Map<String, String> order = new TreeMap<>();
        order.put("customer_id", String.valueOf(shoppingCart.getCustomer().getId()));
        order.put("shipping_method", String.valueOf(shippingMethodId));
        order.put("shipping_cost", String.valueOf(shippingCost));
        order.put("order_total", String.valueOf(generalTotal + shippingCost));
        order.put("payment_method", String.valueOf(paymentMethodId));
        int id = checkoutRepository.saveOrder(order, getOrderedProductsId());
        saveOrderToFile(id);
        shoppingCart.getProductList().clear();
    }


    private void saveOrderToFile(int id) {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
        Path path = Path.of("src/main/java/hu/progmasters/webshop/orders/order-" + id + "-" + dateTime + ".txt");
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            bufferedWriter.write("Order ID: " + id + " " + "Order date: " + dateTime);
            bufferedWriter.newLine();
            bufferedWriter.write("Shipping method: " + shippingMethod + " " + "Payment method: " + paymentMethod);
            bufferedWriter.newLine();
            bufferedWriter.write("Customer: " + shoppingCart.getCustomer().getName() + " " + shoppingCart.getCustomer().getEmail());
            bufferedWriter.newLine();
            for (Product product : shoppingCart.getProductList()) {
                bufferedWriter.write(product.getId() + " " + product.getName() + " " + product.getPrice());
                bufferedWriter.newLine();
            }
            bufferedWriter.write("General total: " + generalTotal + " Shipping cost: " + shippingCost + " Order total: " + (generalTotal + shippingCost));

        } catch (IOException e) {
            OutputHandler.outputRed("Can write file! " + e.getMessage());
        }
    }

    private void checkoutInformation() {
        generalTotal = getGeneralTotal();
        Customer customer = shoppingCart.getCustomer();
        OutputHandler.outputYellow(customer != null ? customer.toString() : "Not logged in");
        OutputHandler.outputYellow("Total products price: " + generalTotal);
        OutputHandler.outputYellow(shippingMethodId != 0 ? shippingMethod + " " + shippingCost : "Not selected shipping method");
        OutputHandler.outputYellow(paymentMethodId != 0 ? paymentMethod : "Not selected payment method");
        getCart().forEach((k,v) -> System.out.println(v.size() + "x " + v.get(0).getName() + " [" + k + "]"));
    }

    private void chooseShippingMethod() {
        Map<Integer, Integer> shippingMethods = checkoutRepository.getShippingMethods(generalTotal);
        if (shippingMethods.size() > 0) {
            System.out.print("Choose shipping method: ");
            shippingMethodId = inputHandler.getInputNumber();
            shippingMethod = checkoutRepository.getShippingMethodName(shippingMethodId);
            shippingCost = shippingMethods.get(shippingMethodId);
        } else {
            OutputHandler.outputRed("No products in cart");
        }
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

    private void removeProduct(int id) {
        Optional<Product> product = shoppingCart.getProductList().stream().filter(p -> p.getId() == id).findFirst();
        product.ifPresent(value -> shoppingCart.getProductList().remove(value));
    }

    private Map<Integer,List<Product>> getCart() {
        return shoppingCart.getProductList().stream()
                .collect(Collectors.groupingBy(Product::getId));
    }
}
