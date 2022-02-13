package hu.progmasters.webshop.domain;

import hu.progmasters.webshop.handlers.LogHandler;
import hu.progmasters.webshop.ui.menuoptions.MainMenu;

public class WebShop {

    MainMenu mainMenu = new MainMenu();

    public WebShop() {
        LogHandler.create();
        LogHandler.addLog("Start program");
        LogHandler.writeLog();
    }

    public void startMenu() {
        mainMenu.menuOptions();
    }
}
