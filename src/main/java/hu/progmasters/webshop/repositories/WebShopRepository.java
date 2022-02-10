package hu.progmasters.webshop.repositories;

public class WebShopRepository extends Repository {

    public WebShopRepository() {
        createTable();
    }

    private void createTable() {

        String shippingPriceTable = "CREATE TABLE IF NOT EXISTS shipping_price("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "price INT NOT NULL,"
                + "amount_min INT NOT NULL,"
                + "amount_max INT NOT NULL,"
                + "shipping_method INT NOT NULL);";

        execute(shippingPriceTable);
    }
}
