package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.ShoppingCart;
import hu.progmasters.webshop.handlers.InputHandler;
import hu.progmasters.webshop.handlers.OutputHandler;
import hu.progmasters.webshop.ui.menuoptions.MenuOption;

public abstract class Menu {

    protected static final ShoppingCart shoppingCart = new ShoppingCart();


    protected MenuOption getMenu(MenuOption[] menuOptions) {
        int option;
        int counter = 1;
        for (MenuOption menuOption : menuOptions) {
            OutputHandler.outputYellow(counter + ". " + menuOption.getName());
            counter++;
        }
        do {
            System.out.print("Choose an option: ");
            option = InputHandler.getInputNumber();

        } while (option < 1 || option > menuOptions.length);
        return menuOptions[option - 1];
    }

    protected boolean yesOrNo(String question) {
        String answer;
        do {
            System.out.print(question);
            answer = InputHandler.getInputString().toLowerCase();
            if (!answer.equals("y") && !answer.equals("n")) {
                System.out.println("Wrong choice!");
            }
        } while (!answer.equals("y") && !answer.equals("n"));
        return answer.equals("y");
    }

    public void addProductToCart(Product product) {
        if (product.isInStock()) {
            shoppingCart.addProduct(product);
        } else {
            OutputHandler.outputRed("Product out of stock!");
        }
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
}
