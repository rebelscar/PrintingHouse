package com.citb408.utilities;


public final class Check {

    private Check() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Utility class and cannot be instantiated");
    }

    public static double percentage(double percent) {
        if (percent < 0)
            return 0;
        else if (percent > 100)
            return 100;
        else
            return percent;
    }

    // I've decided a limit would be of type int, that way I can put an
    // abstraction above them. I don't think  1880.30 is a good idea anywhere
    // as a limit. It's better to round it in this particular case at least.
    public static int limit(int limit) {
        return Math.max(limit, 0);
    }

    public static int positive(int number) { return Math.max(number, 1); }

}
