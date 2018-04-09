package com.uwm.wmii.student.michal.itmproj.utils;

import java.math.BigDecimal;

/**
 * Created by Michał on 09.04.2018.
 */

public class FunkcjePomocnicze {
    public static BigDecimal truncateDecimal(double x, int numberofDecimals)
    {
        if ( x > 0) {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
        } else {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
        }
    }
}
