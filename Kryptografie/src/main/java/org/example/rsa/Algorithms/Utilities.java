package org.example.rsa.Algorithms;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;

public class Utilities {

    public static HashSet<BigInteger> calculateRandoms(int lengthOfNumber, int numberAmount) {
        BigDecimal boundLow = BigDecimal.valueOf(2).pow(lengthOfNumber - 1);
        BigDecimal boundHigh = BigDecimal.valueOf(2).pow(lengthOfNumber).subtract(BigDecimal.ONE);
        double randomHelper = Math.random();
        BigDecimal m;

        do {
            m = BigDecimal.valueOf(randomHelper).multiply(BigDecimal.valueOf(lengthOfNumber * 2));
        }
        while (m.sqrt(MathContext.DECIMAL128).remainder(BigDecimal.ONE).equals(BigDecimal.ZERO));

        HashSet<BigInteger> randomNumbers = new HashSet<BigInteger>();

        for (long i = 1; i <= numberAmount; i++) {
            BigInteger randomNumber = boundLow.add((m.sqrt(MathContext.DECIMAL128)
                    .multiply(BigDecimal.valueOf(i)).remainder(BigDecimal.ONE))
                    .multiply(boundHigh.subtract(boundLow).add(BigDecimal.ONE))).toBigInteger();
            randomNumbers.add(randomNumber);
        }
        return randomNumbers;
    }

    // based on it security script page 97
    public static BigInteger calculateRandom(int lengthOfNumber) throws Exception {
        Optional<BigInteger> random = calculateRandoms(lengthOfNumber, 1)
                .stream().findFirst();
        if (!random.isPresent()) {
            throw new Exception("keine Zahl generiert");
        }
        return random.get();
    }

    public static BigInteger getRandomBigInteger(BigInteger minimum, BigInteger maximum) {
        int power = maximum.intValue();
        BigDecimal n = new BigDecimal(new BigInteger(power * 10 / 3, new Random()));
        BigDecimal m = new BigDecimal(new BigInteger(power * 2, new Random()));

        while (m.sqrt(new MathContext(power * 2)).pow(2).equals(m) || m.compareTo(BigDecimal.valueOf(2)) <= 0) {
            m = m.add(BigDecimal.ONE);
        }

        BigDecimal part1 = m.sqrt(new MathContext(power*2)).multiply(n).remainder(BigDecimal.ONE);
        BigDecimal part2 = new BigDecimal(maximum.add(BigInteger.ONE).subtract(minimum));
        BigDecimal sn = new BigDecimal(minimum).add(part1.multiply(part2));
        return sn.toBigInteger();
    }
}
