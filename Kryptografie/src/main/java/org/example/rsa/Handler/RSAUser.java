package org.example.rsa.Handler;

import org.example.rsa.PairTypes.*;

public class RSAUser {
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
