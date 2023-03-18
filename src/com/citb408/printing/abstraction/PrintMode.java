package com.citb408.printing.abstraction;

public enum PrintMode {
    COLORLESS(2), COLORED(4);

    double multiplier;

    PrintMode(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }
}
