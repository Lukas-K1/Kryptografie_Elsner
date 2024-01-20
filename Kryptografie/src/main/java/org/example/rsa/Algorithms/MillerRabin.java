package org.example.rsa.Algorithms;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class MillerRabin {

    static BigInteger zero = BigInteger.ZERO;
    static BigInteger one = BigInteger.ONE;
    static BigInteger two = BigInteger.TWO;

    /**
     * Miller Rabin primality check
     *
     * @param probablyPrime
     * @param k
     * @param n
     * @param m
     * @return false if probably prime
     * @throws Exception
     */
    public static Boolean isPrime(BigInteger probablyPrime, int k, BigInteger n, BigInteger m) throws Exception {
        if (probablyPrime.equals(one)) {
            return false;
        }
        if (probablyPrime.equals(two)) {
            return true;
        }
        if (!probablyPrime.testBit(0)) {
            return false;
        }

        BigInteger j = probablyPrime.subtract(one);
        BigInteger exponent = probablyPrime.subtract(two);

        int s = 0;
//        while (j.mod(two).equals(zero)) {
//            j = j.divide(two);
//            s++;
//        }
        int zeros = j.getLowestSetBit();
        j = j.shiftRight(zeros);
        s = zeros;

        for (int i = 0; i < k; i++) {

            n = n.add(BigInteger.valueOf(i));
            BigInteger x = Utilities.getRandomBigInteger(two, exponent, n, m);
            BigInteger y = FastExponentiation.exponentiation(x, j, probablyPrime);

            if (y.equals(one) || y.equals(probablyPrime.subtract(one))) {
                continue;
            }
            int r;
            for (r = 1; r < s; r++) {
                y = FastExponentiation.exponentiation(y, two, probablyPrime);
                if (y.equals(one)) {
                    return false;
                }
                if (y.equals(probablyPrime.subtract(one))) {
                    break;
                }
            }
            if (r == s) {
                return false;
            }
        }
        return true;
    }

    public static boolean parallelisPrime(BigInteger probablyPrime, int k, BigInteger n, BigInteger m) throws Exception {
        if (probablyPrime.equals(one)) {
            return false;
        }
        if (probablyPrime.equals(two)) {
            return true;
        }
        if (!probablyPrime.testBit(0)) {
            return false;
        }

        int s = probablyPrime.subtract(one).getLowestSetBit();
        BigInteger finalD = probablyPrime.subtract(one).shiftRight(s);
        BigInteger j = probablyPrime.subtract(one);
        BigInteger exponent = probablyPrime.subtract(two);

        ForkJoinPool pool = new ForkJoinPool();
        List<Callable<Boolean>> tasks = IntStream.range(0, k)
                .mapToObj(i -> (Callable<Boolean>) () -> {
                    BigInteger newN = n.add(BigInteger.valueOf(i));
                    BigInteger x = Utilities.getRandomBigInteger(two, exponent, newN, m);
                    BigInteger y = FastExponentiation.exponentiation(x, finalD, probablyPrime);

                    if (y.equals(one) || y.equals(j)) {
                        return false;
                    }

                    for (int r = 1; r < s; r++) {
                        y = FastExponentiation.exponentiation(y, two, probablyPrime);
                        if (y.equals(j)) return false;
                    }
                    return true;
                })
                .toList();

        try {
            return pool.invokeAny(tasks);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        finally {
            pool.shutdown();
        }
    }
}