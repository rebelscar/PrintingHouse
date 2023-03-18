package com.citb408.printing.solid;

import com.citb408.utilities.LimitObserver;

public class PrinterSettings {
    private static final int MAX_PAPER_QUANTITY = 1400;
    private static final int PAGES_PER_MINUTE = 9000; // makes 150 pages perSecond
    private static LimitObserver discount = new LimitObserver(5, 10);

    private PrinterSettings() { }

    public static int getMaxPaperQuantity() {
        return MAX_PAPER_QUANTITY;
    }

    public static int getPagesPerMinute() {
        return PAGES_PER_MINUTE;
    }

    public static LimitObserver getDiscount() {
        return discount;
    }

    public static double getDiscountPercentage() {
        return discount.getPercentage();
    }

    public static int getDiscountLimit() {
        return discount.getLimit();
    }

    public static void setDiscount(LimitObserver discount) {
        PrinterSettings.discount = discount;
    }

}
