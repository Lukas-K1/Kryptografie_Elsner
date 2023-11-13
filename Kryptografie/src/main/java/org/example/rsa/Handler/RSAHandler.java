package org.example.rsa.Handler;

import org.example.rsa.Algorithms.Blockchiffre;
import org.example.rsa.Algorithms.ExtendedEuclidean;
import org.example.rsa.Algorithms.MillerRabin;
import org.example.rsa.Algorithms.Utilities;
import org.example.rsa.PairTypes.PairCipherBlockLength;
import org.example.rsa.PairTypes.PrivateKey;
import org.example.rsa.PairTypes.PublicKey;
import org.example.rsa.PairTypes.RSAKeyPair;

import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;

/**
 * class to manage all keys
 * their input
 * aswell as generating keys and the modulus
 */
public class RSAHandler {

    public int millerRabinTrials = MillerRabin.MILLER_RABIN_TRIALS;
    public int primeNumberLength = 128;

    private int _m;

    public void setMillerRabinTrials(int millerRabinTrials) {
        this.millerRabinTrials = millerRabinTrials;
    }

    public void setPrimeNumberLength(int primeNumberLength) {
        this.primeNumberLength = primeNumberLength;
    }

    public void setM(int m) {
        this._m = m;
    }

    private RSAKeyPair generateRSAKeyPair() throws Exception {
        BigInteger p = Utilities.calculateRandom(primeNumberLength, millerRabinTrials);
        BigInteger q = Utilities.calculateRandom(primeNumberLength, millerRabinTrials);

        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        BigInteger e;

        do {
            e = Utilities.calculateRandom(primeNumberLength, _m);
        }
        while (!ExtendedEuclidean.gcd(e, phi).equals(BigInteger.ONE));

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

    public PairCipherBlockLength encryptMessage(String message, PublicKey publicKey, int blockLength) throws Exception {
        return Blockchiffre.encryptMessage(message, publicKey, blockLength);
    }

    public String decryptMessage(PairCipherBlockLength encryptedMessage, PrivateKey privateKey) throws Exception {
        return Blockchiffre.decryptMessage(encryptedMessage, privateKey);
    }

    public String signatureForMessage(String message) {
        return Utilities.hash256(message);
    }

    public String decryptHashedMessage(String hashedMessage, PrivateKey privateKey) {
        String message = "";

        try {
            message = Blockchiffre.decryptMessage(new PairCipherBlockLength(hashedMessage, 1), privateKey);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n" + "hash could not be decrypted");
        }
        return message;
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
