package hu.progmasters.webshop.repositories;

public class ProductRepository implements Repository {

    public ProductRepository() {
        createTable();
    }

    private void createTable() {
        String productsTable = "CREATE TABLE IF NOT EXISTS products("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "name VARCHAR(100) NOT NULL,"
                + "price INT NOT NULL,"
                + "sale_price INT NOT NULL DEFAULT 0,"
                + "description VARCHAR(255),"
                + "active BOOLEAN DEFAULT 1,"
                + "shipping_price INT NOT NULL,"
                + "product_type INT NOT NULL,"
                + "tax VARCHAR(10) NOT NULL,"
                + "on_sale BOOLEAN NOT NULL DEFAULT 0,"
                + "FOREIGN KEY (product_type) REFERENCES product_types(id));";

        String productTypesTable = "CREATE TABLE IF NOT EXISTS product_types("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "name VARCHAR(20) NOT NULL UNIQUE);";

        execute(productTypesTable);
        execute(productsTable);
    }
}
