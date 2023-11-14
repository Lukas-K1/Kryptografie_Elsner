package org.example;

import org.example.rsa.Algorithms.ExtendedEuclidean;
import org.example.rsa.Handler.RSAHandler;

import java.math.BigInteger;
import java.util.Timer;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");
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
        int numberLength = 128;
        int millerRabinTrials = 20;
        RSAHandler rsaHandler = new RSAHandler();
        rsaHandler.setPrimeNumberLength(numberLength);
        rsaHandler.setMillerRabinTrials(millerRabinTrials);
        rsaHandler.setM(20);
        long start = System.currentTimeMillis();
        //RSAHandler.generateRandomPrimes();
        BigInteger p = RSAHandler.calculateP(numberLength);
        long durationp = System.currentTimeMillis() - start;
        System.out.println(durationp);
        BigInteger q = RSAHandler.calculateQ(numberLength);
        long durationq = System.currentTimeMillis() - start;
        System.out.println(durationq);
        BigInteger n = RSAHandler.calculateN(p, q);
        long durationn = System.currentTimeMillis() - start;
        System.out.println(durationn);
        BigInteger phi = RSAHandler.calculatePhiN(p, q);
        long durationphi = System.currentTimeMillis() - start;
        System.out.println(durationphi);
        BigInteger e = RSAHandler.calculateE(phi);
        long duratione = System.currentTimeMillis() - start;
        System.out.println(duratione);
        BigInteger d = ExtendedEuclidean.getModInverse(e, phi);
        long durationd = System.currentTimeMillis() - start;
        System.out.println(durationd);

        long duration = System.currentTimeMillis() - start;
        System.out.println(duration);
    }
}