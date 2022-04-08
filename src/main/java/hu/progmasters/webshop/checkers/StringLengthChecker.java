package hu.progmasters.webshop.checkers;

import hu.progmasters.webshop.handlers.OutputHandler;

public class StringLengthChecker implements Checker {

    @Override
    public boolean check(String input) {

        if (input.length() <= 0) {
            OutputHandler.outputRed("Required field");
            return false;
        }
        return true;
    }
}
