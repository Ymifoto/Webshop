package hu.progmasters.webshop.services;

import hu.progmasters.webshop.domain.Category;
import hu.progmasters.webshop.handlers.InputHandler;
import hu.progmasters.webshop.handlers.OutputHandler;

public class CategoryService extends Services {

    public void getCategoryList() {
        System.out.println("Categories:");
        OutputHandler.printMapStringKey(categoryRepository.getCategoryList(),"ID" ,"Categories" );
    }


    public void selectCategory() {
        System.out.print("Give category ID: ");
        Category category = categoryRepository.getCategroyById(InputHandler.getInputNumber());
        System.out.println(category.getName() != null ? category.getName() + " " + category.getProducts().size() + " products" : "Not found category");
        category.getProducts().forEach(System.out::println);
    }
}
