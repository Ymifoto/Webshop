package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.handlers.InputHandler;

public interface Menu {

    InputHandler inputHandler = new InputHandler();

    default Menu getMenu(Menu[] menuOptions) {
        int counter = 1;
        for (Menu menuOption : menuOptions) {
            System.out.println(counter + ". " + menuOption);
            counter++;
        }
        System.out.print("Choose an option: ");
        return menuOptions[inputHandler.getInputNumber() - 1];
    }
}
