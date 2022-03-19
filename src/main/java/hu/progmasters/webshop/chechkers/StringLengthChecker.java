package hu.progmasters.webshop.chechkers;

public class StringLengthChecker implements Checker {

    @Override
    public boolean check(String input) {
        return input.length() > 0;
    }
}
