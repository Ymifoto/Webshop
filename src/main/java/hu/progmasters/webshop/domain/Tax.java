package hu.progmasters.webshop.domain;

public enum Tax {

    AFA(0.27),FIVE(0.05),ZERO(0);

    private final double amount;

    Tax(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}
