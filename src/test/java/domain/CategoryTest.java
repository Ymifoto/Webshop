package domain;

import hu.progmasters.webshop.domain.Category;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.Tax;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {

    private Category category;
    private final Product product = new Product(1,"Test Product","Test",100,50,"Test product", "Test", Tax.AFA, true );

    @BeforeEach
    void createCategory() {
        category = new Category();
        category.setId(1);
        category.setName("Category");
        category.setDescription("Description");
    }

    @Test
    void categoryGetDataTest() {
        assertEquals(3, category.getData().size());
        assertEquals("1", category.getData().get("id"));
        assertEquals("Category", category.getData().get("category_name"));
        assertEquals("Description", category.getData().get("category_desc"));
    }

    @Test
    void categoryUpdateDataTest() {
        Map<String,String> data = new HashMap<>();
        data.put("category_name","Test category");
        data.put("category_desc","Test description");
        category.updateData(data);

        assertEquals("1", category.getData().get("id"));
        assertEquals("Test category", category.getData().get("category_name"));
        assertEquals("Test description", category.getData().get("category_desc"));
    }

    @Test
    void categoryAttributesTest() {
        assertEquals(1, category.getId());
        category.setId(2);
        assertEquals(2, category.getId());

        assertEquals("Category", category.getName());
        category.setName("New Category");
        assertEquals("New Category", category.getName());
    }

    @Test
    void productsInCategoryTest() {
        assertEquals(0, category.getProducts().size());
        category.getProducts().add(product);
        assertEquals(1, category.getProducts().size());
    }

}
