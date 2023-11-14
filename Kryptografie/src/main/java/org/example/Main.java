package org.example;

import org.example.rsa.Algorithms.Blockchiffre;
import org.example.rsa.Algorithms.ExtendedEuclidean;
import org.example.rsa.Handler.RSAHandler;
import org.example.rsa.PairTypes.PairCipherBlockLength;
import org.example.rsa.PairTypes.RSAKeyPair;

import java.math.BigInteger;
import java.util.Timer;

public class Main {
    public static void main(String[] args) throws Exception {
        //System.out.println("Hello world!");
        //BigInteger x = ExtendedEuclidean.getModInverse(BigInteger.valueOf(5), BigInteger.valueOf(48));
        String text = "das ist ein nicht wirklich sinnvoller text, aber er ist lang genug um die blocklänge zu testen. ich hoffe das funktioniert";
        int numberLength = 1024;
        int millerRabinTrials = 100;
        RSAHandler rsaHandler = new RSAHandler();
        rsaHandler.setPrimeNumberLength(numberLength);
        rsaHandler.setMillerRabinTrials(millerRabinTrials);
        rsaHandler.setM(500);
        rsaHandler.setNumberLengthPQ(numberLength);
        long start = System.nanoTime();
        RSAHandler.generateRandomPrimes();

        long durationNano = System.nanoTime() - start;
        long duration = durationNano / 1000000;
        System.out.println(duration);//currently between 11000 and 14000
        RSAKeyPair keys = RSAHandler.generateRSAKeyPair();
        BigInteger n = keys.getPublicKey().getN();
        //int blocklength = Blockchiffre.calcBlockLength(numberLength/2);
        int blocklength = Blockchiffre.calculateBlockLength(n);
        RSAHandler.setBlockLength(blocklength);
        PairCipherBlockLength encryptedText = RSAHandler.encryptMessage(text,keys.getPublicKey());
        String decryptedText = RSAHandler.decryptMessage(encryptedText, keys.getPrivateKey());
        System.out.println(encryptedText.getCipher());
        //System.out.println(decryptedText);
    }
}