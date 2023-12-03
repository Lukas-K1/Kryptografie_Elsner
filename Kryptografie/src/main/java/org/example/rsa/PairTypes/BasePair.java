package org.example.rsa.PairTypes;

import java.math.BigInteger;

public class BasePair<T0, T1> {

    private T0 _first;
    private T1 _second;
    public BasePair(T0 publicKey, T1 privateKey) {
        _first = publicKey;
        _second = privateKey;
    }

    public T0 getFirst() {
        return _first;
    }

    public T1 getSecond() {
        return _second;
    }
}
