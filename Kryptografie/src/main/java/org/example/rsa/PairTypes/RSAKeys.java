package Kryptografie.src.main.java.org.example.rsa.PairTypes;

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
}
