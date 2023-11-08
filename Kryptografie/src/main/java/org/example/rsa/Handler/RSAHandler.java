package org.example.rsa.Handler;

import org.example.rsa.Algorithms.ExtendedEuclidean;
import org.example.rsa.Algorithms.MillerRabin;
import org.example.rsa.Algorithms.Utilities;
import org.example.rsa.PairTypes.PrivateKey;
import org.example.rsa.PairTypes.PublicKey;
import org.example.rsa.PairTypes.RSAKeyPair;

import java.math.BigInteger;

/**
 * class to manage all keys
 * their input
 * aswell as generating keys and the modulus
 */
public class RSAHandler {

    public int millerRabinTrials = MillerRabin.MILLER_RABIN_TRIALS;
    public int primeNumberLength = 256;

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
            d = generateD(e, phi);
        } catch (Exception exception) {
            throw new Exception("could not generate d");
        }

        PublicKey publicKey = new PublicKey(e, n);
        PrivateKey privateKey = new PrivateKey(d, n);
        return new RSAKeyPair(publicKey, privateKey);
    }

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
