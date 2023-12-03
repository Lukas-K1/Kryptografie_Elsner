package Kryptografie.src.main.java.org.example.rsa.PairTypes;

public class RSAKeyPair {
    private final PublicKey _publicKey;
    private final PrivateKey _privateKey;

    public RSAKeyPair(PublicKey publicKey, PrivateKey privateKey) {
        _publicKey = publicKey;
        _privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return _publicKey;
    }

    public PrivateKey getPrivateKey() {
        return _privateKey;
    }

    @Override
    public String toString() {
        return "RSAKeyPair{" +
                "publicKey=" + _publicKey +
                ", privateKey=" + _privateKey +
                '}';
    }
}
