package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.domain.Category;
import hu.progmasters.webshop.domain.ShoppingCart;
import hu.progmasters.webshop.repositories.CategoryRepository;
import hu.progmasters.webshop.repositories.ProductRepository;
import hu.progmasters.webshop.ui.menuoptions.CategoryMenuOptions;

import java.util.Map;
import java.util.TreeMap;

public class CategoryMenu extends Menu {

    private final CategoryRepository categoryRepository = new CategoryRepository();
    private final ProductRepository productRepository;
    private final ShoppingCart shoppingCart;

    public CategoryMenu(ProductRepository productRepository, ShoppingCart shoppingCart) {
        this.productRepository = productRepository;
        this.shoppingCart = shoppingCart;
    }

    public void menuOptions() {
        CategoryMenuOptions option;
        Category category;
        do {
            option = (CategoryMenuOptions) getMenu(CategoryMenuOptions.values());
            switch (option) {
                case ADD_NEW:
                    System.out.println("Add category");
                    categoryRepository.addCategory(getCategoryData());
                    break;
                case LIST:
                    System.out.println("Categories:");
                    categoryRepository.getCategoryList();
                    break;
                case SELECT:
                    System.out.print("Give category ID: ");
                    category = categoryRepository.getCategroyById(inputHandler.getInputNumber());
                    System.out.println(category.getName() != null ? category.getName() + " " + category.getProducts().size() + " products" : "Not found category");
                    category.getProducts().forEach(System.out::println);
                    break;
                case ADD_TO_CART:
                    System.out.print("Give a product id: ");
                    addProductToCart(shoppingCart, productRepository.getProductById(inputHandler.getInputNumber()));
                    break;
                case UPDATE:
                    System.out.print("Give category ID: ");
                    category = categoryRepository.getCategroyById(inputHandler.getInputNumber());
                    System.out.println(category);
                    category.updateData(getCategoryData());
                    categoryRepository.updateCategory(category);
                    break;
                case BACK:
            }
        } while (option != CategoryMenuOptions.BACK);
    }

    private Map<String, String> getCategoryData() {
        Map<String, String> categoryData = new TreeMap<>();
        System.out.print("Give a category name: ");
        categoryData.put("category_name", inputHandler.getInputString());
        System.out.print("Give a short description: ");
        categoryData.put("category_desc", inputHandler.getInputString());
        return categoryData;
    }
}
