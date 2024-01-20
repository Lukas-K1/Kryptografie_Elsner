package org.example.rsa.Algorithms;

import java.math.BigInteger;

public class FastExponentiation {
    static BigInteger zero = BigInteger.ZERO;
    static BigInteger one = BigInteger.ONE;
    static BigInteger two = BigInteger.TWO;
    /**
     * alternative fast exponentiation
     * @param baseValue
     * @param exponent
     * @param modulo
     * @return
     */
    public static BigInteger exponentiation(BigInteger baseValue, BigInteger exponent, BigInteger modulo) {
        BigInteger x = one;

        while (exponent.compareTo(zero) > 0) {
            if (exponent.testBit(0)){
                x = x.multiply(baseValue).mod(modulo);
            }
            baseValue = baseValue.multiply(baseValue).mod(modulo);
//            exponent = exponent.divide(two);
            exponent = exponent.shiftRight(1);
        }
        return x.mod(modulo);
    }
}
