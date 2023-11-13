package org.example.rsa.Handler;

import org.example.rsa.Algorithms.*;
import org.example.rsa.PairTypes.PairCipherBlockLength;
import org.example.rsa.PairTypes.PrivateKey;
import org.example.rsa.PairTypes.PublicKey;
import org.example.rsa.PairTypes.RSAKeyPair;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;

/**
 * class to manage all keys
 * their input
 * aswell as generating keys and the modulus
 */
public class RSAHandler {

    public int millerRabinTrials = MillerRabin.MILLER_RABIN_TRIALS;
    public int primeNumberLength = 128;

    private BigInteger _m;
    private BigInteger _a;
    private BigInteger _b;
    private BigInteger _n;

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
        BigInteger p = Utilities.generateRandom(_m, _n, _a, _b, millerRabinTrials);
        BigInteger q = Utilities.generateRandom(_m, _n, _a, _b, millerRabinTrials);

        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        BigInteger e;

        do {
            e = Utilities.generateRandom(_m, _n, _a, _b, millerRabinTrials);
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
