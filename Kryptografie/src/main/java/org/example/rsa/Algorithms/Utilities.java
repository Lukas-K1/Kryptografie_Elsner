package org.example.rsa.Algorithms;

import org.example.rsa.PairTypes.PrivateKey;
import org.example.rsa.PairTypes.PublicKey;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Optional;

public class Utilities {
    static BigInteger[] smallPrimes;

    public static HashSet<BigInteger> calculateRandoms(BigInteger a, BigInteger b, BigInteger n, BigInteger m) {
        BigDecimal aValue = new BigDecimal(a);
        BigDecimal bValue = new BigDecimal(b);
        BigDecimal nValue = new BigDecimal(n);
        BigDecimal mValue = new BigDecimal(m);
        MathContext context = MathContext.DECIMAL128;

        BigDecimal bSubA = bValue.subtract(aValue).add(BigDecimal.ONE);
        HashSet<BigInteger> randomNumbers = new HashSet<BigInteger>();

        for (long i = 1; i <= n.intValue(); i++) {
            while (mValue.sqrt(context).remainder(BigDecimal.ONE).equals(BigDecimal.ZERO)) {
                mValue = mValue.add(BigDecimal.ONE);
            }

            BigDecimal nsquareMod = mValue.sqrt(context).multiply(nValue).remainder(BigDecimal.ONE);
            BigDecimal floor = nsquareMod.multiply(bSubA);
            BigDecimal sn = aValue.add(floor);
            BigInteger randomNumber = sn.toBigInteger();
            randomNumbers.add(randomNumber);
        }
        return randomNumbers;
    }

    // based on it security script page 97
    public static BigInteger calculateRandom(BigInteger a, BigInteger b, BigInteger n, BigInteger m) throws Exception {
        BigInteger random = calculateRandoms(a, b, n, m)
                .stream()
                .findFirst()
                .orElseThrow(() -> new Exception("keine Zahl generiert"));
        return random;
    }

    public static BigInteger generateRandom(BigInteger m, BigInteger n, BigInteger a, BigInteger b, int millerRabinTrials) throws Exception {
        BigInteger probablyPrime;

        while (true) {
            probablyPrime = getRandomBigInteger(a, b, n, m).setBit(0);
            boolean hasPrimeDivisor;
            //TODO check against smaller primes

            for (BigInteger smallerPrime : smallPrimes) {
                if (probablyPrime.equals(smallerPrime)) {
                    return probablyPrime;
                }
                if (probablyPrime.mod(smallerPrime).equals(BigInteger.ZERO)) {
                    hasPrimeDivisor = true;
                    break;
                }
            }
            if (MillerRabin.isPrime(probablyPrime, millerRabinTrials, n, m)) {
                return probablyPrime;
            }
            n.add(BigInteger.ONE);
        }
    }

    public static BigInteger getRandomBigInteger(BigInteger a, BigInteger b, BigInteger n, BigInteger m) {
        BigDecimal aValue = new BigDecimal(a);
        BigDecimal bValue = new BigDecimal(b);
        BigDecimal nValue = new BigDecimal(n);
        BigDecimal mValue = new BigDecimal(m);
        MathContext context = MathContext.DECIMAL128;

        BigDecimal bSubA = bValue.subtract(aValue).add(BigDecimal.ONE);

        while (mValue.sqrt(context).remainder(BigDecimal.ONE).equals(BigDecimal.ZERO)) {
            mValue = mValue.add(BigDecimal.ONE);
        }

        BigDecimal nsquareMod = mValue.sqrt(context).multiply(nValue).remainder(BigDecimal.ONE);
        BigDecimal floor = nsquareMod.multiply(bSubA);
        BigDecimal sn = aValue.add(floor);
        return sn.toBigInteger();
    }

    public static String hash256(String message, PrivateKey privateKey) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        final byte[] hashbytes = digest.digest(message.getBytes());
        BigInteger hashedInteger = new BigInteger(1, hashbytes);
        BigInteger hashedMessage = FastExponentiation.exponentiation(hashedInteger, privateKey.getKey(), privateKey.getN());
        return hashedMessage.toString(16);
    }

    public static boolean isSignatureValid(String message, String signature, PublicKey publicKey) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        final byte[] hashbytes = digest.digest(message.getBytes());
        BigInteger hashedInteger = new BigInteger(1, hashbytes);
        BigInteger decryptedSignature = FastExponentiation.exponentiation(hashedInteger, publicKey.getKey(), publicKey.getN());
        return decryptedSignature.toString(16).equals(signature);
    }
}
