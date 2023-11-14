package org.example.rsa.Handler;

import org.example.rsa.Algorithms.*;
import org.example.rsa.PairTypes.PairCipherBlockLength;
import org.example.rsa.PairTypes.PrivateKey;
import org.example.rsa.PairTypes.PublicKey;
import org.example.rsa.PairTypes.RSAKeyPair;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * class to manage all keys
 * their input
 * aswell as generating keys and the modulus
 */
public class RSAHandler {

    public static int millerRabinTrials;
    public int primeNumberLength = 128;

    private static BigInteger _m;
    private static BigInteger _p;
    private static BigInteger _q;
    private static BigInteger _n;
    private static BigInteger _phi;

    public void setMillerRabinTrials(int millerRabinTrials) {
        this.millerRabinTrials = millerRabinTrials;
    }

    public void setPrimeNumberLength(int primeNumberLength) {
        this.primeNumberLength = primeNumberLength;
    }

    public void setM(int m) {
        this._m = BigInteger.valueOf(m);
    }

    private RSAKeyPair generateRSAKeyPair() throws Exception {
        BigInteger p = calculateP(primeNumberLength);
        BigInteger q = calculateQ(primeNumberLength);

        BigInteger n = calculateN(p, q);
        BigInteger phi = calculatePhiN(p, q);

        BigInteger e = calculateE(phi);

        BigInteger d;

        try {
            d = ExtendedEuclidean.getModInverse(e, phi);
        } catch (Exception exception) {
            System.out.println(exception.getMessage() + "\n" + "private key could not be generated");
            return generateRSAKeyPair();
        }

        PublicKey publicKey = new PublicKey(e, n);
        PrivateKey privateKey = new PrivateKey(d, n);
        return new RSAKeyPair(publicKey, privateKey);
    }

    public PairCipherBlockLength encryptMessage(String message, PublicKey publicKey) throws Exception {
        return Blockchiffre.encryptMessage(message, publicKey);
    }

    public String decryptMessage(PairCipherBlockLength encryptedMessage, PrivateKey privateKey) throws Exception {
        return Blockchiffre.decryptMessage(encryptedMessage, privateKey);
    }

    public String signatureForMessage(String message) throws Exception {
        return Utilities.hash256(message, generateRSAKeyPair().getPrivateKey());
    }

    public boolean validSignature(String hashedMessage, String signature) throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        final byte[] hashbytes = digest.digest(hashedMessage.getBytes());
        BigInteger hashedInteger = new BigInteger(1, hashbytes);
        BigInteger signatureInteger = new BigInteger(signature, 16);

        BigInteger decryptedSignature = FastExponentiation.exponentiation(signatureInteger, generateRSAKeyPair().getPublicKey().getKey(), generateRSAKeyPair().getPublicKey().getN());
        return decryptedSignature.equals(hashedInteger);
    }

    private static BigInteger calculateN(BigInteger p, BigInteger q) {
        return _n = p.multiply(q);
    }

    private static BigInteger calculateP(int primeNumberLength) throws Exception {
        BigInteger possibleP;
        BigInteger a = BigInteger.TWO.pow((primeNumberLength / 2) - 1);
        BigInteger b = BigInteger.TWO.pow(primeNumberLength / 2);
        do {
            possibleP = Utilities.generateRandom(_m, BigInteger.ONE, a, b, millerRabinTrials);
        } while (!possibleP.equals(_q));
        return _p = possibleP;
    }

    private static BigInteger calculateQ(int primeNumberLength) throws Exception {
        BigInteger possibleQ;
        BigInteger a = BigInteger.TWO.pow((primeNumberLength / 2) - 1);
        BigInteger b = BigInteger.TWO.pow(primeNumberLength / 2);
        do {
            possibleQ = Utilities.generateRandom(_m, BigInteger.ONE, a, b, millerRabinTrials);
        } while (!possibleQ.equals(_p));
        return _q = possibleQ;
    }

    private static BigInteger calculateE(BigInteger phi) throws Exception {
        BigInteger e;

        BigInteger lowerBoundE = BigInteger.TWO;
        BigInteger upperBoundE = phi.subtract(BigInteger.ONE);

        do {
            e = Utilities.generateRandom(_m, _n, lowerBoundE, upperBoundE, millerRabinTrials);
        }
        while (!ExtendedEuclidean.gcd(e, phi).equals(BigInteger.ONE) || e.compareTo(phi) >= 0);
        return e;
    }

    private static BigInteger calculatePhiN(BigInteger p, BigInteger q) {
        return _phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
    }

    /**
     * generates private key
     *
     * @param e
     * @param phi
     * @return
     * @throws Exception
     * @deprecated
     */
    private BigInteger generateD(BigInteger e, BigInteger phi) throws Exception {
        e = e.mod(phi);
        BigInteger d = generateDHelper(phi, e);
        return d.mod(e);
    }

    private BigInteger generateDHelper(BigInteger phi, BigInteger e) throws Exception {
        if (!phi.mod(e).equals(BigInteger.ZERO)) {
            return generateDHelper(e, phi.mod(e))
                    .multiply(phi)
                    .subtract(BigInteger.ONE)
                    .divide(e.multiply(BigInteger.valueOf(-1)));
        }
        if (e.equals(BigInteger.ONE)) {
            return BigInteger.ONE;
        } else {
            throw new Exception("values are not coprime");
        }
    }
}
