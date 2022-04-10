package repositories;

import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.ShoppingCart;
import hu.progmasters.webshop.repositories.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class OrderRepositoryTest {


    private static final AdminRepository adminRepository = AdminRepository.getRepository();
    private static final OrderRepository orderRepository = OrderRepository.getRepository();
    private static final CustomerRepository customerRepository = CustomerRepository.getRepository();
    private static final ProductRepository productRepository = ProductRepository.getRepository();
    private static final CheckoutRepository checkoutRepository = CheckoutRepository.getRepository();
    private static final ShoppingCart shoppingCart = new ShoppingCart();

    @BeforeAll
    public static void initDataBase() {
        Repository.setTestMode(true);
        adminRepository.createTables();
        adminRepository.loadTestData();
    }

    @Test
    public void getInProgressOrdersTest() {
        int id = saveTestOrder();
        assertEquals(1, orderRepository.getInProgressOrders().stream().filter(o -> o.getId() == id).count());
    }

    @Test
    public void setOrderShippedDoneTest() {
        int id = saveTestOrder();
        orderRepository.setOrderShippedDone(id);
        assertEquals(0, orderRepository.getInProgressOrders().stream().filter(o -> o.getId() == id).count());
    }

    @Test
    public void orderSearchTest() {
        saveTestOrder();
        assertFalse(orderRepository.orderSearch("jhon_doe@gmail.com").isEmpty());
    }

    @Test
    public void getAllOrdersTest() {
        saveTestOrder();
        assertFalse(orderRepository.getAllOrders().isEmpty());
    }

    private int saveTestOrder() {
        setUpShoppingCart();
        int cartTotal = shoppingCart.getProductList().stream().mapToInt(Product::getPrice).sum();
        int shippingCost = checkoutRepository.getShippingCostById(2,cartTotal);
        List<Integer> orderedProducts = shoppingCart.getProductList().stream().map(Product::getId).collect(Collectors.toList());
        Map<String, String> order = new TreeMap<>();
        order.put("customer_id", String.valueOf(shoppingCart.getCustomer().getId()));
        order.put("shipping_method", "2");
        order.put("shipping_cost", String.valueOf(shippingCost));
        order.put("order_total", String.valueOf(cartTotal + shippingCost));
        order.put("payment_method", "1");
        return checkoutRepository.saveOrder(order, shoppingCart.getProductList());
    }

    private static void setUpShoppingCart() {
        shoppingCart.getProductList().clear();
        shoppingCart.setCustomer(customerRepository.getCustomerById(1).get());
        shoppingCart.getProductList().add(productRepository.getProductById(1));
        shoppingCart.getProductList().add(productRepository.getProductById(3));
        shoppingCart.getProductList().add(productRepository.getProductById(25));
    }

}
