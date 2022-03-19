package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.domain.Category;
import hu.progmasters.webshop.domain.ShoppingCart;
import hu.progmasters.webshop.repositories.CategoryRepository;
import hu.progmasters.webshop.repositories.ProductRepository;
import hu.progmasters.webshop.ui.menuoptions.CategoryMenuOptions;

public class CategoryMenu extends Menu {

    private final CategoryRepository categoryRepository = CategoryRepository.getRepository();
    private final ProductRepository productRepository = ProductRepository.getRepository();
    private final ShoppingCart shoppingCart;

    public CategoryMenu(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void menuOptions() {
        CategoryMenuOptions option;
        Category category;
        do {
            option = (CategoryMenuOptions) getMenu(CategoryMenuOptions.values());
            switch (option) {
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
                case BACK:
            }
        } while (option != CategoryMenuOptions.BACK);
    }
}
