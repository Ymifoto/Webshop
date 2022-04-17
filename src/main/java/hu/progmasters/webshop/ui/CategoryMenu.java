package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.repositories.CategoryRepository;
import hu.progmasters.webshop.services.CategoryService;
import hu.progmasters.webshop.ui.menuoptions.CategoryMenuOptions;

public class CategoryMenu extends Menu {

    private final CategoryService categoryService = new CategoryService(new CategoryRepository());

    public void menuOptions() {
        CategoryMenuOptions option;
        do {
            option = (CategoryMenuOptions) getMenu(CategoryMenuOptions.values());
            switch (option) {
                case LIST:
                    categoryService.getCategoryList();
                    break;
                case SELECT:
                    categoryService.selectCategory();
                    break;
                case ADD_TO_CART:
                    categoryService.addToCart(shoppingCart);
                    break;
                case BACK:
            }
        } while (option != CategoryMenuOptions.BACK);
    }
}
