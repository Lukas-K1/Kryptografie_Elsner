package Kryptografie.src.main.java.org.example.rsa.Algorithms;

import java.math.BigInteger;

public class ExtendedEuclidean {
    /**
     * calculates gcd and parameters x, y used in mod inverse
     * @param a
     * @param b
     * @return
     */
    public static ExtendedEuclidResult extendedGCD(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            return new ExtendedEuclidResult(a, BigInteger.ONE, BigInteger.ZERO);
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
        if (a.mod(b).equals(BigInteger.ZERO)) {
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
        if (result.gcd.equals(BigInteger.ONE)) {
            return result.x.mod(phi);
        } else {
            return BigInteger.ZERO;
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
