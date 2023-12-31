package org.example.rsa.PairTypes;

import java.math.BigInteger;

public class PublicKey extends RSAKeys {

    public PublicKey(BigInteger e, BigInteger n) {
        super(e, n);
    }

    public BigInteger getKeyE() {
        return this.getKey();
    }

    @Override
    public String toString() {
        return "e=" + this.getKeyE() + "\n\n n=" + this.getN();
    }
}
