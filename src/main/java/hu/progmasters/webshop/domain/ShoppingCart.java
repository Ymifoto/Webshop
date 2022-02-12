package hu.progmasters.webshop.domain;

import hu.progmasters.webshop.DatabaseConfig;
import hu.progmasters.webshop.repositories.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ShoppingCart extends Repository {

    private final Customer customer;
    private final List<Product> productList = new ArrayList<>();
    private int shippingMethod;
    private int shippingCost;
    private int orderId;
    private static final String TABLE = "orders";

    public ShoppingCart(Customer customer) {
        this.customer = customer;
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void finalizeOrder() {
        Map<String, String> data = new TreeMap<>();
        int orderTotal = productList.stream().mapToInt(Product::getPrice).sum();
        getShippingCost(orderTotal);
        data.put("customer_id", String.valueOf(customer.getId()));
        data.put("order_total", String.valueOf(orderTotal));
        data.put("shipping_method",String.valueOf(shippingMethod));
        data.put("shipping_cost",String.valueOf(shippingCost));
        data.put("general_total",String.valueOf(orderTotal + shippingCost));
        orderId = insert(TABLE, data);
        setOrderedProductsTable();
    }

    private void getShippingCost(int orderTotal) {
        String sql = "SELECT * FROM shipping_price WHERE" +
                "amount_min <= " + orderTotal +
                " AND amount_max > " + orderTotal +
                " AND shipping_method = " + shippingMethod;
        try (Connection connection = DatabaseConfig.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            if (result.next()) {
                shippingCost = result.getInt("price");
            }

        } catch (SQLException e) {
            System.out.println("Not find shipping cost");
            e.printStackTrace();
        }
    }

    public void setShippingMethod(int shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public List<Product> getProductList() {
        return productList;
    }

    private void setOrderedProductsTable() {
        Map<String,String> data = new TreeMap<>();
        data.put("order_id",String.valueOf(orderId));
        for (Product product : productList) {
            data.put( "product_id",String.valueOf(product.getId()));
            insert("ordered_products",data);
        }
    }
}
