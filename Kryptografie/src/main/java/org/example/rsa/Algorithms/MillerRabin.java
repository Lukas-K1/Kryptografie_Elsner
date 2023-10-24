package org.example.rsa.Algorithms;

import java.math.BigInteger;

public class MillerRabin {
    // This function is called for all k trials.
    // It returns false if n is composite and
    // returns false if n is probably prime.
    // d is an odd number such that d*2<sup>r</sup>
    // = n-1 for some r >= 1
    static Boolean isPrim(BigInteger n, BigInteger random) throws Exception {
        if (ExtendedEuclidean.gcd(n, random).compareTo(BigInteger.ONE) > 0) {
            return false;
        }

        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            return false;
        }

        BigInteger j = n.subtract(BigInteger.ONE);
        BigInteger exponent = n.subtract(BigInteger.ONE);

        int r = 0;
        boolean isPrim = false;

        while (exponent.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            exponent = exponent.divide(BigInteger.TWO);
            r += 1;
        }

        BigInteger x = FastExponantiation.exponentiation(random, exponent, n);
        if (x.equals(BigInteger.ONE) || x.equals(j)) {
            isPrim = true;
        } else {
            r -= 1;
            exponent = exponent.multiply(BigInteger.TWO);
            x = x.multiply(x);
            while (r >= 1 && !isPrim && exponent.compareTo(j) < 0) {
                if (x.mod(n).equals(j)) {
                    isPrim = true;
                }
                r -= 1;
                x = x.multiply(x);
            }
        }
        return isPrim;
    }

    // It returns false if n is composite
    // and returns true if n is probably
    // prime. k is an input parameter that
    // determines accuracy level. Higher
    // value of k indicates more accuracy.
    public static Boolean isPrime(BigInteger probablyPrime, int k) throws Exception {
        boolean isPrime = true;
        // Corner cases
        //n <= 1 || n == 4
        if (probablyPrime.compareTo(BigInteger.ONE) <= 0 || probablyPrime.equals(BigInteger.valueOf(4))) {
            return false;
        }

        if (probablyPrime.compareTo(BigInteger.valueOf(5)) <= 0) {
            return true;
        }
        // Iterate given number of 'k' times TODO
        for (int i = 0; i < k; i++) {
            isPrime = isPrim(probablyPrime, Utilities.calculateRandom(probablyPrime.bitLength()));
            if (isPrime) {
                break;
            }
        }
        return isPrime;
    }
}