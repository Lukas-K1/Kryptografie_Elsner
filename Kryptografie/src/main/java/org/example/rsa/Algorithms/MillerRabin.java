package org.example.rsa.Algorithms;

import java.math.BigInteger;

public class MillerRabin {

    public static Boolean isPrime(BigInteger probablyPrime, int k, BigInteger n, BigInteger m) throws Exception {
        if (probablyPrime.equals(BigInteger.ONE)) {
            return false;
        }
        if (probablyPrime.equals(BigInteger.TWO)) {
            return true;
        }
        if (probablyPrime.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            return false;
        }

        BigInteger j = probablyPrime.subtract(BigInteger.ONE);
        BigInteger exponent = probablyPrime.subtract(BigInteger.TWO);

        int s = 0;
        while (j.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            j = j.divide(BigInteger.TWO);
            s++;
        }

        // Iterate given number of 'k' times TODO
        for (int i = 0; i < k; i++) {

            n = n.add(BigInteger.valueOf(i));
            BigInteger x = Utilities.getRandomBigInteger(BigInteger.TWO, exponent, n, m);
            BigInteger y = FastExponentiation.exponentiation(x, j, probablyPrime);

            if (!y.equals(BigInteger.ONE) || y.equals(probablyPrime.subtract(BigInteger.ONE))) {
                continue;
            }
            int r;
            for (r = 1; r < s; r++) {
                y = FastExponentiation.exponentiation(y, BigInteger.TWO, probablyPrime);
                if (y.equals(BigInteger.ONE)) {
                    return false;
                }
                if (y.equals(probablyPrime.subtract(BigInteger.ONE))) {
                    break;
                }
            }
            if (r == s) {
                return false;
            }
        }
        return true;
    }
}