package com.citb408.publications.abstraction;

public enum PaperType implements Cost {
    NEWSPAPER(0.01), REGULAR(0.02), GLOSSY(0.05);

    private final double cost;

    PaperType(double cost) {
        this.cost = cost;
    }

    @Override
    public double getCost() {
        return cost;
    }
}
