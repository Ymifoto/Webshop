package ui;

import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.Tax;
import hu.progmasters.webshop.ui.Menu;
import hu.progmasters.webshop.ui.ProductsMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MenuTest {

    private Menu menu;

    @BeforeEach
    void init() {
        menu = new ProductsMenu();
    }

    @Test
    void addProductToCartTest() {
        assertTrue(menu.getShoppingCart().getProductList().isEmpty());
        menu.addProductToCart(new Product(1,"Test product","Teszt",10000,5000,"Description","Test product", Tax.AFA,true));
        assertFalse(menu.getShoppingCart().getProductList().isEmpty());
    }
}
