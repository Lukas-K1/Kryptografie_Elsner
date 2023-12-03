package org.example.rsa.PairTypes;

import java.math.BigInteger;

public class RSAKeys extends BasePair<BigInteger, BigInteger> {
    public RSAKeys(BigInteger exponent, BigInteger n) { // n is the modulo
        super(exponent, n);
    }

    public BigInteger getKey() {
        return this.getFirst();
    }

    public BigInteger getN() {
        return this.getSecond();
    }

    @Override
    public String toString() {
        return "RSAKeys{" +
                "key=" + this.getKey() +
                ", n=" + this.getN() +
                '}';
    }
}
