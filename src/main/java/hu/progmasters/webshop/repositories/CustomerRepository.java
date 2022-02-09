package hu.progmasters.webshop.repositories;

public class CustomerRepository implements Repository {

    public CustomerRepository() {
        createTable();
    }

    private void createTable() {

        String sql = "CREATE TABLE IF NOT EXISTS customers("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "name VARCHAR(30) NOT NULL,"
                + "shipping_address VARCHAR(100) NOT NULL,"
                + "billing_address VARCHAR(100),"
                + "email VARCHAR(20) NOT NULL,"
                + "regular_costumer BOOLEAN NOT NULL DEFAULT 0,"
                + "discount INT NOT NULL DEFAULT 0,"
                + "company BOOLEAN NOT NULL DEFAULT 0);";

        execute(sql);
    }
}
