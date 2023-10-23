package org.example.rsa.Algorithms;

import java.math.BigInteger;

public class MillerRabin {
    // This function is called for all k trials.
    // It returns false if n is composite and
    // returns false if n is probably prime.
    // d is an odd number such that d*2<sup>r</sup>
    // = n-1 for some r >= 1
    static Boolean millerTest(BigInteger d, BigInteger n) {

        // Pick a random number in [2..n-2]
        // Corner cases make sure that n > 4
        //new random number generator TODO
        BigInteger random = Utilities.calculateRandom(n, BigInteger.TEN, BigInteger.TEN, d); // BigInteger.TEN nur Platzhalter
        // ich muss an der RandomNumber Generierung sicher noch was dafür anpassen TODO
        BigInteger a = (random.mod(n.subtract(BigInteger.valueOf(4)))).add(BigInteger.TWO);

        // Compute a^d % n
        BigInteger x = FastExponantiation.exponentiation(a, d, n);

        if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE))){
            return true;
        }

        // Keep squaring x while one of the
        // following doesn't happen
        // (i) d does not reach n-1
        // (ii) (x^2) % n is not 1
        // (iii) (x^2) % n is not n-1
        while (!d.equals(n.subtract(BigInteger.ONE))) {
            x = x.pow(2).mod(n);
            d = d.multiply(BigInteger.TWO);

            if (x.equals(BigInteger.ONE)){
                return false;
            }
            if (x.equals(n.subtract(BigInteger.ONE))){
                return true;
            }
        }
        // Return composite
        return false;
    }

    // It returns false if n is composite
    // and returns true if n is probably
    // prime. k is an input parameter that
    // determines accuracy level. Higher
    // value of k indicates more accuracy.
    static Boolean isPrime(BigInteger n, BigInteger k) {

        // Corner cases
        //n <= 1 || n == 4
        if (n.compareTo(BigInteger.ONE) <= 0 || n.equals(BigInteger.valueOf(4))) {
            return false;
        }
        //n <= 3
        if (n.compareTo(BigInteger.valueOf(3)) <= 0){
            return true;
        }

        // Find r such that n = 2^d * r + 1
        // for some r >= 1
        BigInteger d = n.subtract(BigInteger.ONE);

        while (d.mod(BigInteger.TWO).equals(BigInteger.TWO)) {
            d = d.divide(BigInteger.TWO);
        }

        // Iterate given number of 'k' times TODO
        while (!k.equals(BigInteger.ZERO)) {
            if (!millerTest(d, n)) {
                return false;
            }
        }

        return true;
    }
}