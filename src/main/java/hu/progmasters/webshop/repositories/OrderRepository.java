package hu.progmasters.webshop.repositories;

public class OrderRepository implements Repository {

    public OrderRepository() {
        createTable();
    }

    private void createTable() {

        String paymentMethodsTable = "CREATE TABLE IF NOT EXISTS payment_methods("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "name VARCHAR(10) NOT NULL);";

        String shippingMethodsTable = "CREATE TABLE IF NOT EXISTS shipping_methods("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "name VARCHAR(10) NOT NULL);";

        String ordersTable = "CREATE TABLE IF NOT EXISTS orders("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "customer_id INT NOT NULL,"
                + "payment_method INT NOT NULL,"
                + "shipping_method INT NOT NULL,"
                + "order_total INT NOT NULL,"
                + "shipping_cost INT NOT NULL,"
                + "general_total INT NOT NULL,"
                + "order_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY (customer_id) REFERENCES customers(id),"
                + "FOREIGN KEY (payment_method) REFERENCES payment_methods(id),"
                + "FOREIGN KEY (shipping_method) REFERENCES shipping_methods(id));";

        String orderedProductsTable = "CREATE TABLE IF NOT EXISTS ordered_products("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "product_id INT NOT NULL,"
                + "order_id INT NOT NULL,"
                + "costumer_id INT NOT NULL,"
                + "FOREIGN KEY (product_id) REFERENCES products(id),"
                + "FOREIGN KEY (order_id) REFERENCES orders(id),"
                + "FOREIGN KEY (costumer_id) REFERENCES customers(id));";

        execute(paymentMethodsTable);
        execute(shippingMethodsTable);
        execute(ordersTable);
        execute(orderedProductsTable);
    }


}
