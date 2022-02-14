package hu.progmasters.webshop.domain;

import hu.progmasters.webshop.handlers.LogHandler;
import hu.progmasters.webshop.ui.MainMenu;

public class WebShop {

    MainMenu mainMenu = new MainMenu();

    public WebShop() {
        LogHandler.addLog("Start program");
    }

    public void startMenu() {
        mainMenu.menuOptions();
    }
}
