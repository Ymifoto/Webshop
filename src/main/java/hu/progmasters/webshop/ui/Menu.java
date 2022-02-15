package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.handlers.InputHandler;
import hu.progmasters.webshop.ui.menuoptions.MenuOption;

public abstract class Menu {

    protected final InputHandler inputHandler = new InputHandler();

    protected MenuOption getMenu(MenuOption[] menuOptions) {
        int counter = 1;
        for (MenuOption menuOption : menuOptions) {
            System.out.println(counter + ". " + menuOption);
            counter++;
        }
        System.out.print("Choose an option: ");
        return menuOptions[inputHandler.getInputNumber() - 1];
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
        return answer.equals("yes") ? true : false;
    }
}