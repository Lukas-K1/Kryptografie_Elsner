package Kryptografie.src.main.java.org.example.rsa.Handler;

import Kryptografie.src.main.java.org.example.rsa.PairTypes.RSAKeyPair;
import Kryptografie.src.main.java.org.example.rsa.PairTypes.RSAKeys;

public class RSAUser {
    private String _name;
    private String _password;
    private RSAKeyPair _keyPair;

    public RSAUser(RSAKeyPair keys) {
        _keyPair = keys;
    }

    public RSAUser(){
        
    }



    public RSAKeyPair getKeyPair() {
        return _keyPair;
    }

    public void setKeyPair(RSAKeyPair keyPair) {
        _keyPair = keyPair;
    }
}
