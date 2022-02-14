package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.repositories.CategoryRepository;
import hu.progmasters.webshop.ui.menuoptions.CategoryMenuOptions;

public class CategoryMenu extends Menu {

    private final CategoryRepository categoryRepository = new CategoryRepository();

    public void menuOptions() {
        CategoryMenuOptions option;
        do {
            option = (CategoryMenuOptions) getMenu(CategoryMenuOptions.values());
            switch (option) {
                case ADD_NEW:
                    System.out.println("Add customer");
                    break;
                case LIST:
                    System.out.println("Categories:");
                    categoryRepository.getCategoryList();
                    break;
                case SELECT:
                    System.out.println("Update customer");
                    break;
                case UPDATE:
                    break;
                case BACK:
            }
        } while (option != CategoryMenuOptions.BACK);
    }
}
