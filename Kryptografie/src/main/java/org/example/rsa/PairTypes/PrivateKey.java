package org.example.rsa.PairTypes;

import java.math.BigInteger;

public class PrivateKey extends RSAKeys{

    public PrivateKey(BigInteger d, BigInteger n) {
        super(d, n);
    }

    public BigInteger getKeyD() {
        return this.getKey();
    }

    @Override
    public String toString() {
        return "PrivateKey{" +
                "d=" + this.getKeyD() +
                ", n=" + this.getN() +
                '}';
    }
}
