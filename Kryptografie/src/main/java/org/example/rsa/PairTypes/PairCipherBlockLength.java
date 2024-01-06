package org.example.rsa.PairTypes;

import java.math.BigInteger;

public class PairCipherBlockLength extends BasePair<String, Integer> {
    public PairCipherBlockLength(String cipher, Integer blockLength) {
        super(cipher, blockLength);
    }

    public String getCipher() {
        return this.getFirst();
    }

    public Integer getBlockLength() {
        return this.getSecond();
    }

    @Override
    public String toString() {
        return "PairCipherBlockLength{" +
                "cipher='" + this.getCipher() + '\'' +
                ", blockLength=" + this.getBlockLength() +
                '}';
    }
}
