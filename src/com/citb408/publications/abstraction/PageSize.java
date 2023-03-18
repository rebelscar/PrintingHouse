package com.citb408.publications.abstraction;

public enum PageSize implements Cost {
    A1(0.01), A2(0.02), A3(0.03), A4(0.04), A5(0.05);

    private final double cost;

    PageSize(double cost) {
        this.cost = cost;
    }

    @Override
    public double getCost() {
        return cost;
    }
}
