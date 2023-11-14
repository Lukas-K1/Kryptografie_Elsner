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
        String text = "Mit Punkten einer Kurve kann man rechnen? Dies\n" +
                "mag wohl zunächst in Erstaunen zu versetzen. Doch\n" +
                "denkt man an Ortsvektoren in der analytischen Geometrie, so ist das Rechnen mit Punkten nicht ungewöhnlich. Bei Punkten einer elliptischen Kurve besteht\n" +
                "dieses Rechnen aus der Addition von Punkten, insbesondere der mehrfachen Summation desselben Punktes\n" +
                "(entsprechend der Multiplikation mit einem Skalar).\n" +
                "Anwendung finden die Rechnungen auf elliptischen\n" +
                "Kurven u.a. in der Elliptische-Kurven-Kryptografie\n" +
                "(ECC – Elliptic Curve Cryptography). Zum Beispiel\n" +
                "lässt sich das sogenannte Elgamal-Verschlüsselungsverfahren (vgl. Witten u.a., 2015, Seite 97ff., in diesem\n" +
                "Heft) oder der Diffie-Hellman-Schlüsselaustausch in\n" +
                "einfacher Weise auf elliptische Kurven übertragen. Dieses Prinzip wurde von Victor S. Miller und Neal Koblitz\n" +
                "1986 bzw. 1987 unabhängig voneinander vorgeschlagen\n" +
                "(vgl. Wikipedia – Stichwort ,,Elliptic Curve Cryptography“).";
        int numberLength = 1024;
        int millerRabinTrials = 100;
        RSAHandler rsaHandler = new RSAHandler();
        rsaHandler.setPrimeNumberLength(numberLength);
        rsaHandler.setMillerRabinTrials(millerRabinTrials);
        rsaHandler.setM(512);
        long start = System.nanoTime();
        RSAHandler.generateRandomPrimes();
//        BigInteger p = RSAHandler.calculateP(numberLength);
//        long durationp = System.currentTimeMillis() - start;
//        System.out.println(durationp/1000);
//        BigInteger q = RSAHandler.calculateQ(numberLength);
//        long durationq = System.currentTimeMillis() - start;
//        System.out.println(durationq/1000);
//        BigInteger n = RSAHandler.calculateN(p, q);
//        long durationn = System.currentTimeMillis() - start;
//        System.out.println(durationn/1000);
//        BigInteger phi = RSAHandler.calculatePhiN(p, q);
//        long durationphi = System.currentTimeMillis() - start;
//        System.out.println(durationphi/1000);
//        BigInteger e = RSAHandler.calculateE(phi);
//        long duratione = System.currentTimeMillis() - start;
//        System.out.println(duratione/1000);
//        BigInteger d = ExtendedEuclidean.getModInverse(e, phi);
//        long durationd = System.currentTimeMillis() - start;
//        System.out.println(durationd / 1000);

        long durationNano = System.nanoTime() - start;
        long duration = durationNano / 1000000;
        System.out.println(duration);
        RSAKeyPair keys = RSAHandler.generateRSAKeyPair();
        BigInteger n = keys.getPublicKey().getN();
        int blocklength = Blockchiffre.calculateBlockLength(n);
        RSAHandler.setBlockLength(blocklength);
        PairCipherBlockLength encryptedText = RSAHandler.encryptMessage(text,keys.getPublicKey());
        String decryptedText = RSAHandler.decryptMessage(encryptedText, keys.getPrivateKey());
        System.out.println(encryptedText.getCipher());
        //System.out.println(decryptedText);
    }
}