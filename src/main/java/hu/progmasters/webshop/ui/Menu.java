package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.ShoppingCart;
import hu.progmasters.webshop.handlers.InputHandler;
import hu.progmasters.webshop.handlers.OutputHandler;
import hu.progmasters.webshop.ui.menuoptions.MenuOption;

public abstract class Menu {

    protected final InputHandler inputHandler = new InputHandler();

    protected MenuOption getMenu(MenuOption[] menuOptions) {
        int option;
        int counter = 1;
        for (MenuOption menuOption : menuOptions) {
            OutputHandler.outputYellow(counter + ". " + menuOption.getName());
            counter++;
        }
        do {
            System.out.print("Choose an option: ");
            option = inputHandler.getInputNumber();

        } while (option < 1 || option > menuOptions.length);
        return menuOptions[option - 1];
    }

    protected boolean yesOrNo(String question) {
        String answer;
        do {
            System.out.print(question);
            answer = inputHandler.getInputString().toLowerCase();
            if (!answer.equals("yes") && !answer.equals("no")) {
                System.out.println("Wrong answer!");
            }
        } while (!answer.equals("yes") && !answer.equals("no"));
        return answer.equals("yes");
    }

    protected boolean optionChecker(int option, int max) {
        return option > 0 && option <= max;
    }


    public void addProductToCart(ShoppingCart shoppingCart, Product product) {
        if (product.isInStock()) {
            shoppingCart.addProduct(product);
        } else {
            OutputHandler.outputRed("Product out of stock!");
        }
    }
}
