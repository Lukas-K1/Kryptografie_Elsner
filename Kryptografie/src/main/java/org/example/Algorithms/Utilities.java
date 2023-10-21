package org.example.Algorithms;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class Utilities {
    // based on it security script page 97
    public static BigInteger calculateRandom(BigInteger n, BigInteger a, BigInteger b, BigInteger m) {
        BigDecimal sqrtM = new BigDecimal(m).remainder(BigDecimal.ONE).sqrt(MathContext.DECIMAL128);
        BigDecimal factor = new BigDecimal(n).multiply(sqrtM).multiply(new BigDecimal(b).subtract(new BigDecimal(a)).add(BigDecimal.ONE));
        BigDecimal result = new BigDecimal(a).add(factor).setScale(0, RoundingMode.FLOOR);
        return result.toBigInteger();

    }
}
