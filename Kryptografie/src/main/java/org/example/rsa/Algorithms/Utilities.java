package org.example.rsa.Algorithms;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;

public class Utilities {

    public static HashSet<BigInteger> calculateRandoms(int lengthOfNumber, int numberAmount, int m) {
        BigDecimal boundLow = BigDecimal.valueOf(2).pow(lengthOfNumber - 1);
        BigDecimal boundHigh = BigDecimal.valueOf(2).pow(lengthOfNumber).subtract(BigDecimal.ONE);

        BigDecimal mValue = BigDecimal.valueOf(m);

        HashSet<BigInteger> randomNumbers = new HashSet<BigInteger>();

        for (long i = 1; i <= numberAmount; i++) {
            BigInteger randomNumber = boundLow.add((mValue.sqrt(MathContext.DECIMAL128)
                    .multiply(BigDecimal.valueOf(i)).remainder(BigDecimal.ONE))
                    .multiply(boundHigh.subtract(boundLow).add(BigDecimal.ONE))).toBigInteger();
            randomNumbers.add(randomNumber);
        }
        return randomNumbers;
    }

    // based on it security script page 97
    public static BigInteger calculateRandom(int lengthOfNumber, int m) throws Exception {
        Optional<BigInteger> random = calculateRandoms(lengthOfNumber, 1, m)
                .stream().findFirst();
        if (!random.isPresent()) {
            throw new Exception("keine Zahl generiert");
        }
        return random.get();
    }

    public static BigInteger getRandomBigInteger(BigInteger minimum, BigInteger maximum, int n, int m) {
        int power = maximum.intValue();
        BigDecimal nValue = new BigDecimal(BigInteger.valueOf(n));
        BigDecimal mValue = new BigDecimal(BigInteger.valueOf(m));

        while (mValue.sqrt(new MathContext(power * 2)).pow(2).equals(m) || mValue.compareTo(BigDecimal.valueOf(2)) <= 0) {
            mValue = mValue.add(BigDecimal.ONE);
        }

        BigDecimal part1 = mValue.sqrt(new MathContext(power*2)).multiply(nValue).remainder(BigDecimal.ONE);
        BigDecimal part2 = new BigDecimal(maximum.add(BigInteger.ONE).subtract(minimum));
        BigDecimal sn = new BigDecimal(minimum).add(part1.multiply(part2));
        return sn.toBigInteger();
    }
}
