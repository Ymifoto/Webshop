package hu.progmasters.webshop.chechkers;

import hu.progmasters.webshop.handlers.OutputHandler;

public class EmailChecker implements Checker {


    public boolean check(String input) {

        int firsAtsign = input.indexOf("@");
        int lastAtsign = input.indexOf("@");
        int lastDot = input.lastIndexOf(".");

        if (firsAtsign == lastAtsign && firsAtsign >= 1 && lastDot > firsAtsign && lastDot < input.length()) {
            return true;
        }
        OutputHandler.outputRed("Wrong email address!");
        return false;
    }
}
