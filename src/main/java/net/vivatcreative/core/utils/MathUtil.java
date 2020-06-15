package net.vivatcreative.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {

    /**
     * Rounds a double to a specified number of floating points
     *
     * @param value  is the double to be rounded
     * @param places is the amount of numbers after the comma the rounding should be
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
