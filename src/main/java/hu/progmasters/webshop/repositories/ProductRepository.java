package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.domain.Product;

import java.sql.*;
import java.util.*;

public class ProductRepository extends Repository {

    private static final String TABLE = "products";

    public Product getProductById(int id) {
        String sql = "SELECT p.id,name,vendor_name,price,sale_price,description,in_stock,product_type_name FROM products AS p " +
                "JOIN product_types AS pt ON pt.id = p.product_type " +
                "JOIN vendors AS v ON v.id = p.vendor " +
                "WHERE p.id = ?;";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return createProduct(result);
            }
        } catch (SQLException e) {
            System.out.println("Not found product!");
        }
        return null;
    }

    public List<Product> productSearch(String keyword) {
        keyword = "%" + keyword + "%";
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT p.id,name,vendor_name,price,sale_price,description,in_stock,product_type_name FROM products AS p " +
                "JOIN product_types AS pt ON pt.id = p.product_type " +
                "JOIN vendors AS v ON v.id = p.vendor " +
                "WHERE name LIKE ? " +
                "OR vendor_name LIKE ? " +
                "OR product_type_name LIKE ? " +
                "OR description LIKE ?;";
        try (Connection connection = getConnection();
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
        String sql = "SELECT p.id,name,vendor_name,price,sale_price,description,in_stock,product_type_name FROM products AS p " +
                "JOIN product_types AS pt ON pt.id = p.product_type " +
                "JOIN vendors AS v ON v.id = p.vendor " +
                "WHERE " + option + " = 1;";
        try (Connection connection = getConnection();
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

    public int addProduct(Map<String, Object> data) {
        int id = insert(TABLE, data);
        updateCategoriesTable();
        return id;
    }

    public void updateProduct(Product product) {
        Map<String, Object> productData = product.getData();
        checkData(productData, "product_type", getValueIdByName((String) productData.get("product_type"), "product_types", "product_type_name"));
        checkData(productData, "vendor", getValueIdByName((String) productData.get("vendor"), "vendors", "vendor_name"));
        update(TABLE, product.getId(), productData);
        updateCategoriesTable();
    }

    public void addProductToCategory(int productId, int categoryId) {
        Map<String, Object> data = new TreeMap<>();
        data.put("category_id", String.valueOf(categoryId));
        update(TABLE, productId, data);
        updateCategoriesTable();
    }

    public List<String> getProductTypes() {
        List<String> productTypes = new ArrayList<>();
        String sql = "SELECT * FROM product_types;";
        try (Connection connection = getConnection();
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
        try (Connection connection = getConnection();
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

    private void checkData(Map<String, Object> productData, String key, int value) {
        if (value != 0) {
            productData.replace(key, String.valueOf(value));
        } else {
            productData.remove(key);
        }
    }
}
