package org.example;

import org.example.rsa.Algorithms.Blockchiffre;
import org.example.rsa.Handler.RSAHandler;
import org.example.rsa.PairTypes.PrivateKey;
import org.example.rsa.PairTypes.PublicKey;
import org.example.rsa.PairTypes.RSAKeyPair;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) throws Exception {
        //System.out.println("Hello world!");
        //BigInteger x = ExtendedEuclidean.getModInverse(BigInteger.valueOf(5), BigInteger.valueOf(48));
        String text = "MATHEMATIK IST SPANNEND!";
        int numberLength = 258;
        int millerRabinTrials = 10;
        org.example.rsa.Handler.RSAHandler rsaHandler = new RSAHandler();
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
//        BigInteger n = keys.getPublicKey().getN();
        BigInteger n = BigInteger.valueOf(Long.parseLong("91569306435939"));
        BigInteger d = BigInteger.valueOf(Long.parseLong("577322589362687"));
        BigInteger e = BigInteger.valueOf(Long.parseLong("15485863"));
        System.out.println(keys.getPublicKey().getN());
        System.out.println(keys.getPrivateKey().getN());
        System.out.println(keys.getPublicKey().getKeyE());
        System.out.println(keys.getPrivateKey().getKeyD());
        //int blocklength = Blockchiffre.calcBlockLength(numberLength/2);
        int blocklength = Blockchiffre.calculateBlockLength(n);
        RSAHandler.setN(n);
        System.out.println(blocklength);
//        PublicKey publicKey = new PublicKey(e, n);
        PublicKey publicKey = keys.getPublicKey();
//        PrivateKey privateKey = new PrivateKey(d, n);
        PrivateKey privateKey = keys.getPrivateKey();
//        RSAHandler.setBlockLength();
//        PairCipherBlockLength encryptedText = RSAHandler.encryptMessage(text,publicKey);
//        String decryptedText = RSAHandler.decryptMessage(encryptedText, privateKey);
//        System.out.println(encryptedText.getCipher());
//        System.out.println("Entschlüsselter Text");
//        System.out.println(decryptedText);
        //ArrayList<BigInteger> testList = Blockchiffre.unicodeToBigIntList("Hallo wie geht es di", 4);
        //System.out.println(testList);
        //System.out.println(Blockchiffre.bigIntListToUnicode(testList,4));
        String signature = RSAHandler.signatureForMessage(text, privateKey);
        System.out.println(signature);
        boolean validSignature = RSAHandler.validSignature(text, signature, publicKey);
        System.out.println(validSignature);
    }
}