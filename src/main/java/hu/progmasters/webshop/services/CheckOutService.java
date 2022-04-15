package hu.progmasters.webshop.services;

import hu.progmasters.webshop.domain.Customer;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.ShoppingCart;
import hu.progmasters.webshop.handlers.InputHandler;
import hu.progmasters.webshop.handlers.OutputHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CheckOutService extends Services {

    private String shippingMethod;
    private int shippingCost;
    private int shippingMethodId;
    private int paymentMethodId;
    private String paymentMethod;
    private int generalTotal;


    public void chooseShippingMethod() {
        Map<Integer, Integer> shippingMethods = checkoutRepository.getShippingMethods(generalTotal);
        if (shippingMethods.size() > 0) {
            do {
                System.out.print("Choose shipping method: ");
                shippingMethodId = InputHandler.getInputNumber();
            } while (shippingMethodId <= 0 || shippingMethodId > shippingMethods.size());
            shippingMethod = checkoutRepository.getShippingMethodName(shippingMethodId);
            shippingCost = shippingMethods.get(shippingMethodId);

        } else {
            OutputHandler.outputRed("No products in cart");
        }
    }

    public void choosePaymentMethod() {
        Map<Integer, String> paymentMethods = checkoutRepository.getPaymentMethods();
        paymentMethods.forEach((key, value) -> OutputHandler.outputYellow(key + ". " + value));
        do {
            System.out.print("Choose payment method: ");
            paymentMethodId = InputHandler.getInputNumber();

        } while (paymentMethodId < 0 || paymentMethodId > paymentMethods.size());
        paymentMethod = paymentMethods.get(paymentMethodId);
    }

    public void removeProduct(ShoppingCart shoppingCart) {
        System.out.print("Give a product ID: ");
        int id = InputHandler.getInputNumber();
        Optional<Product> product = shoppingCart.getProductList().stream().filter(p -> p.getId() == id).findFirst();
        product.ifPresent(value -> shoppingCart.getProductList().remove(value));
    }

    public void checkoutInformation(ShoppingCart shoppingCart) {
        generalTotal = getGeneralTotal(shoppingCart);
        refreshShippingMethodCost();
        Customer customer = shoppingCart.getCustomer();
        OutputHandler.outputYellow(customer != null ? customer.toString() : "Not logged in");
        OutputHandler.outputYellow("Total price of products: " + generalTotal);
        OutputHandler.outputYellow(shippingMethodId != 0 ? shippingMethod + " " + shippingCost : "Not selected shipping method");
        OutputHandler.outputYellow(paymentMethodId != 0 ? paymentMethod : "Not selected payment method");
        getCart(shoppingCart).forEach((k, v) -> System.out.println(v.size() + "x " + v.get(0).getName() + " [" + k + "]"));
    }

    public boolean finalizeOrder(ShoppingCart shoppingCart) {
        if (shoppingCart.getCustomer() == null) {
            OutputHandler.outputRed("Not logged in");
            return false;
        } else if (shoppingCart.getProductList().isEmpty()) {
            OutputHandler.outputRed("No products in cart");
            return false;
        } else if (shippingMethodId == 0) {
            OutputHandler.outputRed("No shipping method selected!");
            return false;
        } else if (paymentMethodId == 0) {
            OutputHandler.outputRed("No payment method selected!");
            return false;
        }
        saveOrder(shoppingCart);
        return true;
    }

    private void saveOrder(ShoppingCart shoppingCart) {
        Map<String, String> order = new TreeMap<>();
        order.put("customer_id", String.valueOf(shoppingCart.getCustomer().getId()));
        order.put("shipping_method", String.valueOf(shippingMethodId));
        order.put("shipping_cost", String.valueOf(shippingCost));
        order.put("order_total", String.valueOf(generalTotal + shippingCost));
        order.put("payment_method", String.valueOf(paymentMethodId));
        int id = checkoutRepository.saveOrder(order, shoppingCart.getProductList());
        saveOrderToFile(id, shoppingCart);
        shoppingCart.getProductList().clear();
        shippingMethodId = 0;
        shippingMethod = null;

    }

    private int getGeneralTotal(ShoppingCart shoppingCart) {
        return shoppingCart.getProductList().stream().mapToInt(Product::getPrice).sum();
    }

    private void refreshShippingMethodCost() {
        if (shippingMethodId > 0) {
            shippingCost = checkoutRepository.getShippingCostById(shippingMethodId, generalTotal);
        }
    }

    private Map<Integer, List<Product>> getCart(ShoppingCart shoppingCart) {
        return shoppingCart.getProductList().stream()
                .collect(Collectors.groupingBy(Product::getId));
    }

    private void saveOrderToFile(int id, ShoppingCart shoppingCart) {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
        Path path = Path.of("src/main/java/hu/progmasters/webshop/orders/order-" + id + "-" + dateTime + ".txt");
        try (PrintWriter printWriter = new PrintWriter(Files.newBufferedWriter(path))) {
            printWriter.println("Order ID: " + id + " " + "Order date: " + dateTime);
            printWriter.println("Shipping method: " + shippingMethod + " " + "Payment method: " + paymentMethod);
            printWriter.println("Customer: " + shoppingCart.getCustomer().getName() + " " + shoppingCart.getCustomer().getEmail());
            for (Product product : shoppingCart.getProductList()) {
                printWriter.println(product.getId() + " " + product.getName() + " " + product.getPrice());
            }
            printWriter.println("General total: " + generalTotal + " Shipping cost: " + shippingCost + " Order total: " + (generalTotal + shippingCost));
        } catch (IOException e) {
            OutputHandler.outputRed("Can write file! " + e.getMessage());
        }
    }
}
