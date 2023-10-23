package org.example.rsa.Algorithms;

import java.math.BigInteger;

public class FastExponantiation {
    public static BigInteger exponentiation(BigInteger baseValue, BigInteger exponent, BigInteger modulo) {
        if (exponent.equals(BigInteger.ZERO)) {
            return BigInteger.ONE;
        }

        if (exponent.equals(BigInteger.ONE)) {
            return baseValue;
        }

        BigInteger x = exponentiation(baseValue, exponent.divide(BigInteger.TWO), modulo);


        // if exponent is even value
        if (exponent.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            return x.pow(2).mod(modulo);
        }

        // if exponent is odd value
        else {
            return x.pow(2).multiply(baseValue).mod(modulo);
        }
    }

}
