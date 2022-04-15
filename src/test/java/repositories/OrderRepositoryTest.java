package repositories;

import hu.progmasters.webshop.domain.Order;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.ShoppingCart;
import hu.progmasters.webshop.repositories.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

class OrderRepositoryTest {

    private static final AdminRepository adminRepository = new AdminRepository();
    private static final OrderRepository orderRepository = new OrderRepository();
    private static final CustomerRepository customerRepository = new CustomerRepository();
    private static final ProductRepository productRepository = new ProductRepository();
    private static final CheckoutRepository checkoutRepository = new CheckoutRepository();
    private static final ShoppingCart shoppingCart = new ShoppingCart();

    @BeforeAll
    static void initDataBase() {
        Repository.setTestMode(true);
        if (!Repository.isTestDatabaseCreated()) {
            adminRepository.createTables();
            adminRepository.loadTestData();
            Repository.setTestDatabaseCreated(true);
        }
    }

    @Test
    void getInProgressOrdersTest() {
        int id = saveTestOrder();
        List<Order> orderList = orderRepository.getInProgressOrders();
        assertThat(orderList)
                .isNotEmpty()
                .extracting(Order::getId)
                .contains(id);
    }

    @Test
    void setOrderShippedDoneTest() {
        int id = saveTestOrder();
        orderRepository.setOrderShipped(id);
        List<Order> orderList = orderRepository.getInProgressOrders();
        assertThat(orderList)
                .isNotEmpty()
                .extracting(Order::getId)
                .doesNotContain(id);
    }

    @Test
    void orderSearchTest() {
        int id = saveTestOrder();
        assertFalse(orderRepository.orderSearch(String.valueOf(id)).isEmpty());
    }

    @Test
    void getAllOrdersTest() {
        int id = saveTestOrder();
        assertFalse(orderRepository.getAllOrders().isEmpty());
    }

    private int saveTestOrder() {
        setUpShoppingCart();
        int cartTotal = shoppingCart.getProductList().stream().mapToInt(Product::getPrice).sum();
        int shippingCost = checkoutRepository.getShippingCostById(2, cartTotal);
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
