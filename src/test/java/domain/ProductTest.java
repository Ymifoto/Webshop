package domain;

import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.Tax;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {


    Product product;

    @BeforeEach
    void createProduct() {
        product = new Product(1,"Test product","Teszt",10000,5000,"Description","Test product", Tax.AFA,true);
    }

    @Test
    void testSalePrice() {
        assertEquals(5000,product.getPrice());
        Map<String,String> data = product.getData();
        data.put("sale_price","0");
        product.updateData(data);
        assertEquals(10000,product.getPrice());
    }
}
