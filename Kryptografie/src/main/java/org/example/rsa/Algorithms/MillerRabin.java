package org.example.rsa.Algorithms;

import java.math.BigInteger;

public class MillerRabin {

    /**
     * Miller Rabin primality check
     * @param probablyPrime
     * @param k
     * @param n
     * @param m
     * @return false if probably prime
     * @throws Exception
     */
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

        for (int i = 0; i < k; i++) {

            n = n.add(BigInteger.valueOf(i));
            BigInteger x = Utilities.getRandomBigInteger(BigInteger.TWO, exponent, n, m);
            BigInteger y = FastExponentiation.exponentiation(x, j, probablyPrime);

            /**
            *the false fist negation in the following commented if statement was the culprit leading to the abysmal performance in the demonstration
            *with this fix it increases the performance to a level that the generation of boh key pairs (Alice, bob) takes around 2.5 seconds with bitlength = 8192 and k = 100 trials
            */
            //if (!y.equals(BigInteger.ONE) || y.equals(probablyPrime.subtract(BigInteger.ONE))) {
            if (y.equals(BigInteger.ONE) || y.equals(probablyPrime.subtract(BigInteger.ONE))) {
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
