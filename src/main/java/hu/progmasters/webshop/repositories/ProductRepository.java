package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.DatabaseConfig;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.Tax;

import java.sql.*;
import java.util.*;

public class ProductRepository extends Repository {

    private static final String TABLE = "products";
    private final WebShopRepository webShopRepository;

    public ProductRepository(WebShopRepository webShopRepository) {
        this.webShopRepository = webShopRepository;
        createTable();
    }

    public Product getProductById(int id) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "SELECT * FROM products WHERE id = ?;";
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
            System.out.println("Not find product!");
        }
        return null;
    }

    public List<Product> getAllProduct() {
        List<Product> productList = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "SELECT * FROM products;";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            getProductList(result, productList);

            return productList;
        } catch (SQLException e) {
            System.out.println("Can't open database!");
            return productList;
        }
    }

    public List<Product> getDiscountProducts() {
        List<Product> productList = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "SELECT * FROM products WHERE on_sale = 1;";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            getProductList(result, productList);

            return productList;
        } catch (SQLException e) {
            System.out.println("Can't open database!");
            return productList;
        }
    }

    public void addProduct(Map<String, String> data) {
        insert(TABLE, data);
        webShopRepository.updateCategoriesTable();
    }

    public void updateProduct(Product product) {
        update(TABLE, product.getId(), product.getData());
        webShopRepository.updateCategoriesTable();
    }

    public void addProductToCategory(int productId, int categoryId) {
        Map<String, String> data = new TreeMap<>();
        data.put("category_id", String.valueOf(categoryId));
        update(TABLE, productId, data);
        webShopRepository.updateCategoriesTable();

    }

    private void getProductList(ResultSet result, List<Product> productList) throws SQLException {
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
                + "sale_price INT DEFAULT 0,"
                + "description VARCHAR(255),"
                + "product_type VARCHAR(50) NOT NULL,"
                + "tax VARCHAR(10) NOT NULL,"
                + "category_id INT,"
                + "on_sale BOOLEAN NOT NULL DEFAULT 0,"
                + "in_stock BOOLEAN NOT NULL DEFAULT 1);";

        execute(productsTable);
    }
}
