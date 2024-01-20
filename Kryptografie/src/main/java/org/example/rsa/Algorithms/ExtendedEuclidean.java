package org.example.rsa.Algorithms;

import java.math.BigInteger;

public class ExtendedEuclidean {
    static BigInteger zero = BigInteger.ZERO;
    static BigInteger one = BigInteger.ONE;
    static BigInteger two = BigInteger.TWO;
    /**
     * calculates gcd and parameters x, y used in mod inverse
     * @param a
     * @param b
     * @return
     */
    public static ExtendedEuclidResult extendedGCD(BigInteger a, BigInteger b) {
        if (b.equals(zero)) {
            return new ExtendedEuclidResult(a, one, zero);
        } else {
            ExtendedEuclidResult result = extendedGCD(b, a.mod(b));
            BigInteger x = result.y;
            BigInteger y = result.x.subtract((a.divide(b)).multiply(result.y));
            return new ExtendedEuclidResult(result.gcd, x, y);
        }
    }

    /**
     * calculates greatest common divisor
     * @param a
     * @param b
     * @return greatest common divisor
     */
    public static BigInteger gcd(BigInteger a, BigInteger b) {
        if (a.mod(b).equals(zero)) {
            return b;
        } else {
            return gcd(b, a.mod(b));
        }
    }

    /**
     * calculates mod inverse of first parameter
     * @param e
     * @param phi
     * @return
     */
    public static BigInteger getModInverse(BigInteger e, BigInteger phi){
        ExtendedEuclidResult result = extendedGCD(e, phi);
        if (result.gcd.equals(one)) {
            return result.x.mod(phi);
        } else {
            return zero;
        }
    }

    /**
     * custom data type for holding extended gcd information
     * did not want to use an array
     */
    public static class ExtendedEuclidResult {
        public BigInteger gcd;
        public BigInteger x;
        public BigInteger y;

        public ExtendedEuclidResult(BigInteger gcd, BigInteger x, BigInteger y) {
            this.gcd = gcd;
            this.x = x;
            this.y = y;
        }
    }
}
