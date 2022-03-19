package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.domain.DatabaseConfig;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.Tax;

import java.sql.*;
import java.util.*;

public class ProductRepository extends Repository {

    public static final ProductRepository PRODUCT_REPOSITORY = new ProductRepository();
    private static final String TABLE = "products";

    private ProductRepository() {
    }

    public static ProductRepository getRepository() {
        return PRODUCT_REPOSITORY;
    }

    public Product getProductById(int id) {
        String sql = "SELECT * FROM products AS p " +
                "JOIN product_types AS pt ON pt.id = p.product_type " +
                "JOIN vendors AS v ON v.id = p.vendor " +
                "WHERE p.id = ?;";
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
        String sql = "SELECT * FROM products AS p " +
                "JOIN product_types AS pt ON pt.id = p.product_type " +
                "JOIN vendors AS v ON v.id = p.vendor " +
                "WHERE name LIKE ? " +
                "OR vendor_name LIKE ? " +
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
        String sql = "SELECT * FROM products AS p " +
                "JOIN product_types AS pt ON pt.id = p.product_type " +
                "JOIN vendors AS v ON v.id = p.vendor " +
                "WHERE " + option + " = 1;";
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
        checkData(productData, "product_type", getValueIdByName(productData.get("product_type"), "product_types", "product_type_name"));
        checkData(productData, "vendor", getValueIdByName(productData.get("vendor"), "vendors", "vendor_name"));
        update(TABLE, product.getId(), productData);
        updateCategoriesTable();
    }

    public void addProductToCategory(int productId, int categoryId) {
        Map<String, String> data = new TreeMap<>();
        data.put("category_id", String.valueOf(categoryId));
        update(TABLE, productId, data);
        updateCategoriesTable();
    }

    public List<String> getProductTypes() {
        List<String> productTypes = new ArrayList<>();
        String sql = "SELECT * FROM product_types;";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {
            while (result.next()) {
                productTypes.add(result.getString("product_type_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.sort(productTypes);
        return productTypes;
    }

    private int getValueIdByName(String valueName, String table, String column) {
        String sql = "SELECT id FROM " + table + " WHERE " + column + " LIKE  '" + valueName + "';";
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

    private void checkData(Map<String, String> productData, String key, int value) {
        if (value != 0) {
            productData.replace(key, String.valueOf(value));
        } else {
            productData.remove(key);
        }
    }

    private Product createProduct(ResultSet result) throws SQLException {
        return new Product(result.getInt("id")
                , result.getString("name")
                , result.getString("vendor_name")
                , result.getInt("price")
                , result.getInt("sale_price")
                , result.getString("description")
                , result.getString("product_type_name")
                , Tax.valueOf(result.getString("tax"))
                , result.getBoolean("in_stock"));
    }
}
