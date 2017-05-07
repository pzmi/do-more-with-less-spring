package io.github.pzmi.fibonacci.core;

import java.math.BigDecimal;

public class Fibonacci {
    public static BigDecimal calculateNth(long n) {
        BigDecimal result = new BigDecimal(n);
        if (n < 2) {
            return result;
        }

        BigDecimal n1 = new BigDecimal(0);
        BigDecimal n2 = new BigDecimal(1);
        n--;
        while (n > 0) {
            result = n1.add(n2);
            n1 = n2;
            n2 = result;
            n--;
        }

        return result;
    }
}
