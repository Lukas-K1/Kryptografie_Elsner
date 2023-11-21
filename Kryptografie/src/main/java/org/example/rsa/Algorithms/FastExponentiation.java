package org.example.rsa.Algorithms;

import java.math.BigInteger;

public class FastExponentiation {
    /**
     * alternative fast exponentiation
     * @param baseValue
     * @param exponent
     * @param modulo
     * @return
     */
    public static BigInteger exponentiation(BigInteger baseValue, BigInteger exponent, BigInteger modulo) {
        BigInteger x = BigInteger.ONE;

        while (exponent.compareTo(BigInteger.ZERO) > 0) {
            if (exponent.mod(BigInteger.TWO).equals(BigInteger.ONE)){
                x = x.multiply(baseValue).mod(modulo);
            }
            baseValue = baseValue.multiply(baseValue).mod(modulo);
            exponent = exponent.divide(BigInteger.TWO);
        }
        return x.mod(modulo);
    }
}
