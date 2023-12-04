package org.example.rsa.Algorithms;

import org.example.rsa.PairTypes.PairCipherBlockLength;
import org.example.rsa.PairTypes.RSAKeys;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Blockchiffre {
    private static final BigInteger _charSetSize = BigInteger.valueOf(55296);
    private static final String messageFiller = "";

    /**
     * @param message
     * @param rsaKeys
     * @return
     * @throws Exception
     * @deprecated use {@link #encryptMessage(String, RSAKeys, int)} instead
     */
    public static PairCipherBlockLength encryptMessage(String message, RSAKeys rsaKeys) throws Exception {
        int charBlockLength = calculateBlockLength(rsaKeys.getN());
        if (checkBlockLength(charBlockLength, rsaKeys.getN())) {
            throw new Exception("blocklänge nicht passend");
        }
        String filledMessage = fillMessage(message, charBlockLength);
        String cipher = generateCipher(filledMessage, charBlockLength, rsaKeys);
        return new PairCipherBlockLength(cipher, charBlockLength + 1);
    }

    /**
     * encryption of string message
     *
     * @param message
     * @param rsaKeys
     * @param blockLength
     * @return
     * @throws Exception
     */
    public static PairCipherBlockLength encryptMessage(String message, RSAKeys rsaKeys, int blockLength) throws Exception {
        if (!checkBlockLength(blockLength, rsaKeys.getN())) {
            throw new Exception("blocklänge nicht passend");
        }
        String filledMessage = fillMessage(message, blockLength);
        String cipher = generateCipher(filledMessage, blockLength, rsaKeys);
        return new PairCipherBlockLength(cipher, blockLength + 1);
    }

    /**
     * method to call decryption steps
     *
     * @param encryptedMessage
     * @param rsaKeys
     * @return
     * @throws Exception
     */
    public static String decryptMessage(PairCipherBlockLength encryptedMessage, RSAKeys rsaKeys) throws Exception {
        String cipher = encryptedMessage.getCipher();
        Integer blockLength = encryptedMessage.getBlockLength();
        String messageText = "";
        //messageText = decryptCipher(cipher, blockLength, rsaKeys);
        return messageText;
    }

    /**
     * Berechnung Blocklänge nach IT-Security Skript
     * charSetSize = _charSetSize = 55295
     *
     * @param n = p*q
     */
    public static int calculateBlockLength(BigInteger n) {
        int charBlockLength = 1;

        while (_charSetSize.pow(charBlockLength).compareTo(n) < 0) {
            charBlockLength++;
        }

        charBlockLength--;
        return charBlockLength;
    }

    public static int calcBlockLength(int primeNumberLength) {
        double log = (primeNumberLength * (Math.log(2) / Math.log(_charSetSize.doubleValue())));
        return (int) Math.floor(log);
    }

    /**
     * checks correctness of block length based on script
     *
     * @param charBlockLength
     * @param n
     * @return
     */
    private static boolean checkBlockLength(int charBlockLength, BigInteger n) {
        return _charSetSize.pow(charBlockLength).compareTo(n) < 0
                && n.compareTo(_charSetSize.pow(charBlockLength + 1)) < 0;
    }

    /**
     * Auffüllen der Länge des Textes auf eine Länge die ganzzahlig durch die Blocklänge teilbar ist
     */
    private static String fillMessage(String message, int charBlockLength) {
        StringBuilder filledMessage = new StringBuilder(message);
        int messageLengthDifference = charBlockLength - (message.length() % charBlockLength);
        filledMessage.append(messageFiller.repeat(messageLengthDifference));
        return filledMessage.toString();
    }

    /**
     * teilen des Klartextes in Blocke und Erzeugen des Chiffres
     * calls smaller encryption steps
     *
     * @param message         = gesamter Klartext
     * @param charBlockLength = Blocklänge
     * @return Chiffrat
     */
    private static String generateCipher(String message, int charBlockLength, RSAKeys rsaKeys) throws Exception {
        String cipher = "";
        while (message.length() > 0) {
            String messageBlock = message.substring(0, charBlockLength);
            List<Integer> unicodeList = messageToIntList(messageBlock);
            ArrayList<BigInteger> blockList = unicodeToBigIntList(message, charBlockLength, unicodeList);
            ArrayList<BigInteger> cipherList = new ArrayList<>();
            System.out.println("BlockList gen");
            System.out.println(blockList);
            for (BigInteger messageBlockNumber : blockList) {
                cipherList.add(FastExponentiation.exponentiation(messageBlockNumber, rsaKeys.getKey(), rsaKeys.getN()));
            }
            System.out.println("CipherList gen");
            System.out.println(cipherList);
            cipher = cipher.concat(bigIntListToUnicode(cipherList, charBlockLength + 1));
            message = message.substring(charBlockLength);
        }

        return cipher;
    }

    private static String decryptCipher(String cipher, Integer blockLength, RSAKeys rsaKeys) throws Exception {
        String messageText = "";
        while (cipher.length() > 0) {
            String cipherBlock = cipher.substring(0, blockLength);
            List<Integer> unicodeList = messageToIntList(cipherBlock);
            ArrayList<BigInteger> cipherList = unicodeToBigIntList(cipher, blockLength, unicodeList);
            ArrayList<BigInteger> blockList= new ArrayList<>();
            System.out.println("CipherList dec");
            System.out.println(cipherList);
            for (BigInteger cipherNumber: cipherList){
                blockList.add(FastExponentiation.exponentiation(cipherNumber, rsaKeys.getKey(), rsaKeys.getN()));
            }
            System.out.println("BlockList dec");
            System.out.println(blockList);
            messageText =  bigIntListToUnicode(blockList, blockLength - 1);
            cipher = cipher.substring(blockLength);
        }
        return messageText;
    }

    public static ArrayList<BigInteger> unicodeToBigIntList(String unicodeMessage, int blockLength, List<Integer> unicodeList) {
        ArrayList<BigInteger> messageInt = new ArrayList<>();

        int messageLength = unicodeMessage.length();
        for (int i = 0; i < messageLength; i++) {
            BigInteger blockValue = BigInteger.ZERO;

            for (int j = 0; j < blockLength; j++) {
                blockValue = blockValue.add(BigInteger.valueOf(i).multiply(_charSetSize.pow(j)));
            }
            messageInt.add(blockValue);
        }

        return messageInt;
    }

    public static ArrayList<BigInteger> messageToBigIntList(String unicodeMessage, int blockLength, List<Integer> unicodeList) {
        ArrayList<BigInteger> messageInt = new ArrayList<>();

        int messageLength = unicodeMessage.length();
        for (int i = 0; i < messageLength; i++) {
            BigInteger blockValue = BigInteger.ZERO;

            for (int j = 0; j < blockLength; j++) {
                blockValue = blockValue.add(BigInteger.valueOf(i).multiply(_charSetSize.pow(j)));
            }
            messageInt.add(blockValue);
        }

        return messageInt;
    }

    /**
     * message to integer representation
     *
     * @param message
     * @return
     */
    private static ArrayList<Integer> messageToIntList(String message) {
        ArrayList<Integer> messageInt = new ArrayList<>();
        for (int i = 0; i < message.length(); i++) {
            messageInt.add((int) message.charAt(i));
        }
        return messageInt;
    }

    public static String bigIntListToUnicode(ArrayList<BigInteger> bigIntMessage, int blockLength) {
        List<Integer> unicodeList = new ArrayList<>();

        for (BigInteger cipherBlock : bigIntMessage) {
            List<Integer> unicodeBlockList = new ArrayList<>();
            for (int i = blockLength - 1; i >= 0; i--) {
                BigInteger unicodeNumber = cipherBlock.divide(_charSetSize.pow(i));
                unicodeBlockList.add(unicodeNumber.intValue());
                cipherBlock = cipherBlock.mod(_charSetSize.pow(i));
            }
            //Collections.reverse(unicodeBlockList);
            unicodeList.addAll(unicodeBlockList);
        }

        StringBuilder unicodeMessage = new StringBuilder();
        for (int number : unicodeList) {
            unicodeMessage.appendCodePoint(number);
        }

        return unicodeMessage.toString();
    }
}
