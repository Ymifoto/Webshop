package hu.progmasters.webshop.checkers;

import hu.progmasters.webshop.handlers.OutputHandler;

public class ZipCodeChecker implements Checker {

    @Override
    public boolean check(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            OutputHandler.outputRed("It's not a valid zip code!");
            return false;
        }
    }
}
