package org.example;

import org.example.rsa.Algorithms.Blockchiffre;
import org.example.rsa.Algorithms.ExtendedEuclidean;
import org.example.rsa.Handler.RSAHandler;
import org.example.rsa.PairTypes.PairCipherBlockLength;
import org.example.rsa.PairTypes.PrivateKey;
import org.example.rsa.PairTypes.PublicKey;
import org.example.rsa.PairTypes.RSAKeyPair;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Main {
    public static void main(String[] args) throws Exception {
        //System.out.println("Hello world!");
        //BigInteger x = ExtendedEuclidean.getModInverse(BigInteger.valueOf(5), BigInteger.valueOf(48));
        String text = "das ist ein nicht wirklich sinnvoller text, aber er ist lang genug um die blocklänge zu testen. ich hoffe das funktioniert";
        int numberLength = 256;
        int millerRabinTrials = 10000000;
        RSAHandler rsaHandler = new RSAHandler();
        rsaHandler.setPrimeNumberLength(numberLength);
        rsaHandler.setMillerRabinTrials(millerRabinTrials);
        rsaHandler.setM(50);
        rsaHandler.setNumberLengthPQ(numberLength);
        long start = System.nanoTime();
        RSAHandler.generateRandomPrimes();

        long durationNano = System.nanoTime() - start;
        long duration = durationNano / 1000000;
        System.out.println(duration);//currently between 11000 and 14000
        //int blocklength = Blockchiffre.calcBlockLength(numberLength/2);
        /*
        BigInteger n = BigInteger.valueOf(791569306435939L);
        BigInteger e = BigInteger.valueOf(15485863);
        BigInteger d = BigInteger.valueOf(577322589362687L);

        System.out.println(n);
        System.out.println(e);
        System.out.println(d);
        int blocklength = Blockchiffre.calculateBlockLength(n);
        RSAHandler.setBlockLength(blocklength);
        RSAKeyPair keys = new RSAKeyPair(new PublicKey(e, n), new PrivateKey(d, n));
        */


        RSAKeyPair keys = RSAHandler.generateRSAKeyPair();
        BigInteger n = keys.getPublicKey().getN();
        System.out.println(keys.getPublicKey().getN());
        System.out.println(keys.getPrivateKey().getN());
        System.out.println(keys.getPublicKey().getKeyE());
        System.out.println(keys.getPrivateKey().getKeyD());
        //int blocklength = Blockchiffre.calcBlockLength(numberLength/2);
        int blocklength = Blockchiffre.calculateBlockLength(n);
        RSAHandler.setBlockLength(blocklength);
        PairCipherBlockLength encryptedText = RSAHandler.encryptMessage(text,keys.getPublicKey());
        String decryptedText = RSAHandler.decryptMessage(encryptedText, keys.getPrivateKey());
        System.out.println(encryptedText.getCipher());
        System.out.println("Entschlüsselter Text");
        System.out.println(decryptedText);
        //ArrayList<BigInteger> testList = Blockchiffre.unicodeToBigIntList("Hallo wie geht es di", 4);
        //System.out.println(testList);
        //System.out.println(Blockchiffre.bigIntListToUnicode(testList,4));
    }
}