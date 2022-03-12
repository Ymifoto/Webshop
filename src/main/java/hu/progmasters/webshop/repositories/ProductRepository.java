package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.DatabaseConfig;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.Tax;

import java.sql.*;
import java.util.*;

public class ProductRepository extends Repository {

    private static final String TABLE = "products";

    public ProductRepository() {
        createTable();
    }

    public Product getProductById(int id) {
        String sql = "SELECT * FROM products AS p JOIN product_types AS pt ON pt.id = p.product_type WHERE p.id = ?;";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return createProduct(result);
            }
        } catch (SQLException e) {
            System.out.println("Not find product!");
        }
        return null;
    }

    public List<Product> productSearch(String keyword) {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM products AS p JOIN product_types AS pt ON pt.id = p.product_type " +
                "WHERE name LIKE ? " +
                "OR vendor LIKE ? " +
                "OR product_type_name LIKE ? " +
                "OR description LIKE ?;";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, keyword);
            preparedStatement.setString(3, keyword);
            preparedStatement.setString(4, keyword);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                productList.add(createProduct(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<Product> getStockOrDiscountProducts(String option) {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM products AS p JOIN product_types AS pt ON pt.id = p.product_type WHERE " + option + " = 1;";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)
        ) {
            while (result.next()) {
                productList.add(createProduct(result));
            }
            return productList;
        } catch (SQLException e) {
            System.out.println("Can't open database!");
            return productList;
        }
    }

    public void addProduct(Map<String, String> data) {
        insert(TABLE, data);
        updateCategoriesTable();
    }

    public void updateProduct(Product product) {
        Map<String, String> productData = product.getData();
        checkProductType(productData);
        update(TABLE, product.getId(), productData);
        updateCategoriesTable();
    }

    public void addProductToCategory(int productId, int categoryId) {
        Map<String, String> data = new TreeMap<>();
        data.put("category_id", String.valueOf(categoryId));
        update(TABLE, productId, data);
        updateCategoriesTable();
    }

    public Map<Integer, String> getProductTypes() {
        Map<Integer, String> productTypes = new TreeMap<>();
        String sql = "SELECT * FROM product_types;";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {
            while (result.next()) {
                productTypes.put(result.getInt("id"), result.getString("product_type_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productTypes;
    }

    public int getProductTypeByName(String productTypeName) {
        String sql = "SELECT id FROM product_types WHERE  product_type_name LIKE  '" + productTypeName + "';";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {
            if (result.next()) {
                return result.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void checkProductType(Map<String, String> productData) {
        int productTypeId = getProductTypeByName(productData.get("product_type"));

        if (productTypeId != 0) {
            productData.replace("product_type",String.valueOf(productTypeId));
        } else {
            productData.remove("product_type");
        }
    }

    private Product createProduct(ResultSet result) throws SQLException {
        return new Product(result.getInt("id")
                , result.getString("name")
                , result.getString("vendor")
                , result.getInt("price")
                , result.getInt("sale_price")
                , result.getString("description")
                , result.getString("product_type_name")
                , Tax.valueOf(result.getString("tax"))
                , result.getBoolean("in_stock"));
    }

    private void createTable() {
        String vendors = "CREATE TABLE IF NOT EXISTS vendors("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "vendor_name VARCHAR(20) NOT NULL UNIQUE);";

        String product_types = "CREATE TABLE IF NOT EXISTS product_types("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "product_type_name VARCHAR(40) NOT NULL UNIQUE);";

        String productsTable = "CREATE TABLE IF NOT EXISTS products("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "name VARCHAR(100) NOT NULL,"
                + "vendor VARCHAR(20) NOT NULL,"
                + "price INT UNSIGNED NOT NULL,"
                + "sale_price INT UNSIGNED DEFAULT 0,"
                + "description VARCHAR(255),"
                + "product_type INT UNSIGNED NOT NULL,"
                + "tax VARCHAR(10) NOT NULL,"
                + "category_id INT UNSIGNED,"
                + "on_sale BOOLEAN NOT NULL DEFAULT 0,"
                + "in_stock BOOLEAN NOT NULL DEFAULT 1, "
                + "FOREIGN KEY (product_type) REFERENCES product_types(id));";

        execute(vendors);
        execute(product_types);
        execute(productsTable);
    }
}
