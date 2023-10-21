package org.example.Algorithms;

import java.math.BigInteger;

public class ExtendedEuclidean
{
    public static ExtendedGCDResult extendedGCD(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            return new ExtendedGCDResult(a, BigInteger.ONE, BigInteger.ZERO);
        } else {
            ExtendedGCDResult result = extendedGCD(b, a.mod(b));
            BigInteger x = result.y;
            BigInteger y = result.x.subtract((a.divide(b)).multiply(result.y));
            return new ExtendedGCDResult(result.gcd, x, y);
        }
    }

    public static class ExtendedGCDResult {
        public BigInteger gcd;
        public BigInteger x;
        public BigInteger y;

        public ExtendedGCDResult(BigInteger gcd, BigInteger x, BigInteger y) {
            this.gcd = gcd;
            this.x = x;
            this.y = y;
        }
    }
}
