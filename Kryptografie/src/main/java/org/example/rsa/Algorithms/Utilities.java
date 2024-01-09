package org.example.rsa.Algorithms;



import org.example.rsa.PairTypes.PrivateKey;
import org.example.rsa.PairTypes.PublicKey;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;

public class Utilities {

    private static BigInteger _countN = BigInteger.ONE;

    public static BigInteger getCountN() {
        return _countN;
    }

    public static BigInteger setCountN(BigInteger countN) {
        return _countN = countN;
    }

    /**
     * primes up to 1000 to check against
     */
    private static final BigInteger[] smallPrimes = {
            BigInteger.valueOf(2),
            BigInteger.valueOf(3),
            BigInteger.valueOf(5),
            BigInteger.valueOf(7),
            BigInteger.valueOf(11),
            BigInteger.valueOf(13),
            BigInteger.valueOf(17),
            BigInteger.valueOf(19),
            BigInteger.valueOf(23),
            BigInteger.valueOf(29),
            BigInteger.valueOf(31),
            BigInteger.valueOf(37),
            BigInteger.valueOf(41),
            BigInteger.valueOf(43),
            BigInteger.valueOf(47),
            BigInteger.valueOf(53),
            BigInteger.valueOf(59),
            BigInteger.valueOf(61),
            BigInteger.valueOf(67),
            BigInteger.valueOf(71),
            BigInteger.valueOf(73),
            BigInteger.valueOf(79),
            BigInteger.valueOf(83),
            BigInteger.valueOf(89),
            BigInteger.valueOf(97),
            BigInteger.valueOf(101),
            BigInteger.valueOf(103),
            BigInteger.valueOf(107),
            BigInteger.valueOf(109),
            BigInteger.valueOf(113),
            BigInteger.valueOf(127),
            BigInteger.valueOf(131),
            BigInteger.valueOf(137),
            BigInteger.valueOf(139),
            BigInteger.valueOf(149),
            BigInteger.valueOf(151),
            BigInteger.valueOf(157),
            BigInteger.valueOf(163),
            BigInteger.valueOf(167),
            BigInteger.valueOf(173),
            BigInteger.valueOf(179),
            BigInteger.valueOf(181),
            BigInteger.valueOf(191),
            BigInteger.valueOf(193),
            BigInteger.valueOf(197),
            BigInteger.valueOf(199),
            BigInteger.valueOf(211),
            BigInteger.valueOf(223),
            BigInteger.valueOf(227),
            BigInteger.valueOf(229),
            BigInteger.valueOf(233),
            BigInteger.valueOf(239),
            BigInteger.valueOf(241),
            BigInteger.valueOf(251),
            BigInteger.valueOf(257),
            BigInteger.valueOf(263),
            BigInteger.valueOf(269),
            BigInteger.valueOf(271),
            BigInteger.valueOf(277),
            BigInteger.valueOf(281),
            BigInteger.valueOf(283),
            BigInteger.valueOf(293),
            BigInteger.valueOf(307),
            BigInteger.valueOf(311),
            BigInteger.valueOf(313),
            BigInteger.valueOf(317),
            BigInteger.valueOf(331),
            BigInteger.valueOf(337),
            BigInteger.valueOf(347),
            BigInteger.valueOf(349),
            BigInteger.valueOf(353),
            BigInteger.valueOf(359),
            BigInteger.valueOf(367),
            BigInteger.valueOf(373),
            BigInteger.valueOf(379),
            BigInteger.valueOf(383),
            BigInteger.valueOf(389),
            BigInteger.valueOf(397),
            BigInteger.valueOf(401),
            BigInteger.valueOf(409),
            BigInteger.valueOf(419),
            BigInteger.valueOf(421),
            BigInteger.valueOf(431),
            BigInteger.valueOf(433),
            BigInteger.valueOf(439),
            BigInteger.valueOf(443),
            BigInteger.valueOf(449),
            BigInteger.valueOf(457),
            BigInteger.valueOf(461),
            BigInteger.valueOf(463),
            BigInteger.valueOf(467),
            BigInteger.valueOf(479),
            BigInteger.valueOf(487),
            BigInteger.valueOf(491),
            BigInteger.valueOf(499),
            BigInteger.valueOf(503),
            BigInteger.valueOf(509),
            BigInteger.valueOf(521),
            BigInteger.valueOf(523),
            BigInteger.valueOf(541),
            BigInteger.valueOf(547),
            BigInteger.valueOf(557),
            BigInteger.valueOf(563),
            BigInteger.valueOf(569),
            BigInteger.valueOf(571),
            BigInteger.valueOf(577),
            BigInteger.valueOf(587),
            BigInteger.valueOf(593),
            BigInteger.valueOf(599),
            BigInteger.valueOf(601),
            BigInteger.valueOf(607),
            BigInteger.valueOf(613),
            BigInteger.valueOf(617),
            BigInteger.valueOf(619),
            BigInteger.valueOf(631),
            BigInteger.valueOf(641),
            BigInteger.valueOf(643),
            BigInteger.valueOf(647),
            BigInteger.valueOf(653),
            BigInteger.valueOf(659),
            BigInteger.valueOf(661),
            BigInteger.valueOf(673),
            BigInteger.valueOf(677),
            BigInteger.valueOf(683),
            BigInteger.valueOf(691),
            BigInteger.valueOf(701),
            BigInteger.valueOf(709),
            BigInteger.valueOf(719),
            BigInteger.valueOf(727),
            BigInteger.valueOf(733),
            BigInteger.valueOf(739),
            BigInteger.valueOf(743),
            BigInteger.valueOf(751),
            BigInteger.valueOf(757),
            BigInteger.valueOf(761),
            BigInteger.valueOf(769),
            BigInteger.valueOf(773),
            BigInteger.valueOf(787),
            BigInteger.valueOf(797),
            BigInteger.valueOf(809),
            BigInteger.valueOf(811),
            BigInteger.valueOf(821),
            BigInteger.valueOf(823),
            BigInteger.valueOf(827),
            BigInteger.valueOf(829),
            BigInteger.valueOf(839),
            BigInteger.valueOf(853),
            BigInteger.valueOf(857),
            BigInteger.valueOf(859),
            BigInteger.valueOf(863),
            BigInteger.valueOf(877),
            BigInteger.valueOf(881),
            BigInteger.valueOf(883),
            BigInteger.valueOf(887),
            BigInteger.valueOf(907),
            BigInteger.valueOf(911),
            BigInteger.valueOf(919),
            BigInteger.valueOf(929),
            BigInteger.valueOf(937),
            BigInteger.valueOf(941),
            BigInteger.valueOf(947),
            BigInteger.valueOf(953),
            BigInteger.valueOf(967),
            BigInteger.valueOf(971),
            BigInteger.valueOf(977),
            BigInteger.valueOf(983),
            BigInteger.valueOf(991),
            BigInteger.valueOf(997)
    };

    /**
     * first random number impl; generates set of numbers
     * @param a
     * @param b
     * @param n
     * @param m
     * @return
     */
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

    /**
     * returns random number with primality check using Miller-Rabin
     * @param m
     * @param n
     * @param a
     * @param b
     * @param millerRabinTrials
     * @return random biginteger which is probably prime
     * @throws Exception
     */
    public static BigInteger generateRandomPrime(BigInteger m, BigInteger n, BigInteger a, BigInteger b, int millerRabinTrials) throws Exception {
        BigInteger probablyPrime;
        n = getCountN();
        while (true) {
            probablyPrime = getRandomBigInteger(a, b, n, m).setBit(0);
            boolean hasPrimeDivisor = false;

            for (BigInteger smallerPrime : smallPrimes) {
                if (probablyPrime.equals(smallerPrime)) {
                    return probablyPrime;
                }
                if (probablyPrime.mod(smallerPrime).equals(BigInteger.ZERO)) {
                    hasPrimeDivisor = true;
                    break;
                }
            }
            if (hasPrimeDivisor) {
                n = n.add(BigInteger.ONE);
                continue;
            }
            if (!MillerRabin.isPrime(probablyPrime, millerRabinTrials, n, m)) {
                break;
            }
            n = n.add(BigInteger.ONE);
        }
        setCountN(n.add(BigInteger.ONE));
        return probablyPrime;
    }

    /**
     * returns random biginteger
     * based on it security script page 97
     * @param a
     * @param b
     * @param n
     * @param m
     * @return random biginteger
     */
    public static BigInteger getRandomBigInteger(BigInteger a, BigInteger b, BigInteger n, BigInteger m) {
        BigDecimal aValue = new BigDecimal(a);
        BigDecimal bValue = new BigDecimal(b);
        BigDecimal nValue = new BigDecimal(n);
        BigDecimal mValue = new BigDecimal(m);

        BigDecimal bSubA = bValue.subtract(aValue).add(BigDecimal.ONE);
        int contextPrecision = bSubA.precision() - bSubA.scale();
        MathContext context = new MathContext(contextPrecision);

        while (mValue.sqrt(context).remainder(BigDecimal.ONE).equals(BigDecimal.ZERO) || mValue.sqrt(context).pow(2).equals(mValue)) {
            mValue = mValue.add(BigDecimal.ONE);
        }

        BigDecimal nsquareMod = mValue.sqrt(context).multiply(nValue).remainder(BigDecimal.ONE);
        BigDecimal floor = nsquareMod.multiply(bSubA);
        BigDecimal sn = aValue.add(floor);
        return sn.toBigInteger();
    }

    /**
     * hash function for signature
     * @param message
     * @param privateKey
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String hash256(String message, PrivateKey privateKey) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        final byte[] hashbytes = digest.digest(message.getBytes(StandardCharsets.UTF_8));
        BigInteger hashedInteger = new BigInteger(1, hashbytes);
        BigInteger hashedMessage = FastExponentiation.exponentiation(hashedInteger, privateKey.getKey(), privateKey.getN());
        return hashedMessage.toString(16);
    }

    /**
     * signature validity check
     * @param message
     * @param signature
     * @param publicKey
     * @return true if identical
     * @throws NoSuchAlgorithmException
     */
    public static boolean isSignatureValid(String message, String signature, PublicKey publicKey) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        final byte[] hashbytes = digest.digest(message.getBytes(StandardCharsets.UTF_8));
        BigInteger hashedInteger = new BigInteger(1, hashbytes);
        BigInteger signatureInteger = new BigInteger(signature, 16);

        BigInteger decryptedSignature = FastExponentiation.exponentiation(signatureInteger, publicKey.getKey(), publicKey.getN());
        return decryptedSignature.equals(hashedInteger);
    }
}
