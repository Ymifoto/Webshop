package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.DatabaseConfig;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.Tax;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductRepository extends Repository {

    private final List<Product> productList = new ArrayList<>();
    private static final String TABLE = "products";

    public ProductRepository() {
        createTable();
    }

    private void createTable() {

        String vendors = "CREATE TABLE IF NOT EXISTS vendors("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "vendor_name VARCHAR(20) NOT NULL UNIQUE);";

        String product_types = "CREATE TABLE IF NOT EXISTS product_types("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "product_type_name VARCHAR(20) NOT NULL UNIQUE);";



        String productsTable = "CREATE TABLE IF NOT EXISTS products("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "name VARCHAR(100) NOT NULL,"
                + "vendor VARCHAR(20) NOT NULL,"
                + "price INT NOT NULL,"
                + "sale_price INT NOT NULL DEFAULT 0,"
                + "description VARCHAR(255),"
                + "active BOOLEAN DEFAULT 1,"
                + "shipping_price INT NOT NULL,"
                + "product_type VARCHAR(20) NOT NULL,"
                + "tax VARCHAR(10) NOT NULL,"
                + "on_sale BOOLEAN NOT NULL DEFAULT 0,"
                + "in_stock BOOLEAN NOT NULL DEFAULT 1);";

        execute(productsTable);
    }

    public Product getProductById(int id) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "SELECT * FROM customers WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return new Product(result.getInt("id")
                        , result.getString("name")
                        , result.getString("vendor")
                        , result.getInt("price")
                        , result.getInt("sale_price")
                        , result.getString("description")
                        , result.getString("product_type")
                        , Tax.valueOf(result.getString("tax"))
                        , result.getBoolean("on_sale")
                        , result.getBoolean("in_stock"));
            }
        } catch (SQLException e) {
            System.out.println("Not find customer!");
        }
        return null;
    }

    public List<Product> getAllProduct() {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "SELECT * FROM products;";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                productList.add(new Product(result.getInt("id")
                        , result.getString("name")
                        , result.getString("vendor")
                        , result.getInt("price")
                        , result.getInt("sale_price")
                        , result.getString("description")
                        , result.getString("product_type")
                        , Tax.valueOf(result.getString("tax"))
                        , result.getBoolean("on_sale")
                        , result.getBoolean("in_stock")));
            }
            return productList;
        } catch (SQLException e) {
            System.out.println("Can't open database!");
            return Collections.emptyList();
        }
    }
}
