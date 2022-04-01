package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.domain.Category;
import hu.progmasters.webshop.handlers.OutputHandler;
import hu.progmasters.webshop.repositories.CategoryRepository;
import hu.progmasters.webshop.repositories.ProductRepository;
import hu.progmasters.webshop.ui.menuoptions.CategoryMenuOptions;

public class CategoryMenu extends Menu {

    private final CategoryRepository categoryRepository = CategoryRepository.getRepository();
    private final ProductRepository productRepository = ProductRepository.getRepository();

    public void menuOptions() {
        CategoryMenuOptions option;
        Category category;
        do {
            option = (CategoryMenuOptions) getMenu(CategoryMenuOptions.values());
            switch (option) {
                case LIST:
                    System.out.println("Categories:");
                    OutputHandler.printMapStringKey(categoryRepository.getCategoryList(),"ID" ,"Categories" );
                    break;
                case SELECT:
                    System.out.print("Give category ID: ");
                    category = categoryRepository.getCategroyById(inputHandler.getInputNumber());
                    System.out.println(category.getName() != null ? category.getName() + " " + category.getProducts().size() + " products" : "Not found category");
                    category.getProducts().forEach(System.out::println);
                    break;
                case ADD_TO_CART:
                    System.out.print("Give a product id: ");
                    addProductToCart(productRepository.getProductById(inputHandler.getInputNumber()));
                    break;
                case BACK:
            }
        } while (option != CategoryMenuOptions.BACK);
    }
}
