package com.example.sell.utils;

public class MathUtil {

    private static final Double MONEY_RANGE = 0.01;

    public static Boolean equals(Double d1, Double d2) {
        Double result = Math.abs(d1 - d1);
        if (result < MONEY_RANGE) {
            return true;
        }
        return false;
    }

}
