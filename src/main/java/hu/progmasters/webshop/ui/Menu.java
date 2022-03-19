package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.chechkers.Checker;
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
            if (!answer.equals("y") && !answer.equals("n")) {
                System.out.println("Wrong choice!");
            }
        } while (!answer.equals("y") && !answer.equals("n"));
        return answer.equals("y");
    }

    public void addProductToCart(ShoppingCart shoppingCart, Product product) {
        if (product.isInStock()) {
            shoppingCart.addProduct(product);
        } else {
            OutputHandler.outputRed("Product out of stock!");
        }
    }

    protected String checkInputData(Checker checker, String oldData, String question) {
        String data;
        do {
            System.out.print(oldData != null ? "(" + oldData + ") " : "");
            System.out.print(question);
            data = inputHandler.getInputString();
        } while (!checker.check(data));
        return data;
    }
}
