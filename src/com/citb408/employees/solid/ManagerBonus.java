package com.citb408.employees.solid;

import com.citb408.utilities.LimitObserver;

public class ManagerBonus {
    private static LimitObserver bonus = new LimitObserver(100, 10);

    private ManagerBonus() { }

    public static LimitObserver getBonus() {
        return bonus;
    }

    public static void setBonus(LimitObserver bonus) {
        ManagerBonus.bonus = bonus;
    }

    public static double getPercentage() {
        return bonus.getPercentage();
    }

    public static int getLimit() {
        return bonus.getLimit();
    }

    public static void setLimit(int limit) {
        ManagerBonus.bonus.setLimit(limit);
    }
}
