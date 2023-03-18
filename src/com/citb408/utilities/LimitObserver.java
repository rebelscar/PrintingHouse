package com.citb408.utilities;

// A class in which, if the limit is exceeded an action is taken.
public class LimitObserver implements Limit, Percentage {
    private int limit;
    private double percentage;

    public LimitObserver(int limit, double percentage) {
        setLimit(limit);
        setPercentage(percentage);
    }

    @Override
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = Check.limit(limit);
    }

    @Override
    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = Check.percentage(percentage);
    }
}
