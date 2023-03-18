package com.citb408.utilities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyHandler implements Serializable {
    public static final long serialVersionUID = 12345L;
    private volatile BigDecimal income;
    private volatile BigDecimal expenses;
    private volatile BigDecimal profit;

    public MoneyHandler() {
        this(BigDecimal.ZERO);
    }

    public MoneyHandler(BigDecimal profit) {
        this.income   = BigDecimal.ZERO;
        this.expenses = BigDecimal.ZERO;
        this.profit   = profit;
    }

    public synchronized BigDecimal getIncome() {
        return income.setScale(2, RoundingMode.HALF_DOWN);
    }

    public synchronized void addIncome(double amount) {
        income = income.add(BigDecimal.valueOf(amount));
    }

    public synchronized BigDecimal getExpenses() {
        return expenses.setScale(2, RoundingMode.HALF_DOWN);
    }

    public synchronized void addExpenses(double amount) {
        expenses = expenses.add(BigDecimal.valueOf(amount));
    }

    public synchronized BigDecimal getProfit() {
        calculateProfit();
        return profit.setScale(2, RoundingMode.HALF_DOWN);
    }

    private synchronized void calculateProfit() {
        profit = income.subtract(expenses);
    }

    @Override
    public String toString() {
        return "{Income = " + getIncome() +
                ", Expenses = " + getExpenses() +
                ", Profit = " + getProfit() + "}";
    }
}
