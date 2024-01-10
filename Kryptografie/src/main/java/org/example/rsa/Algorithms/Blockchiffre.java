package org.example.rsa.Algorithms;

import org.example.rsa.PairTypes.PairCipherBlockLength;
import org.example.rsa.PairTypes.RSAKeys;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Blockchiffre {
    private static final BigInteger _charSetSize = BigInteger.valueOf(55296);
    private static final String messageFiller = "\u0000";

    /**
     * encryption of string message
     *
     * @param message     = message to be encrypted
     * @param rsaKeys     = Key used for encryption
     * @param blockLength = Blocklänge
     * @return encryptedMessage
     * @throws Exception = Blocklänge nicht passend
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
     * @param encryptedMessage  = encryptedMessage
     * @param rsaKeys           = Key used for decryption
     * @return decryptedMessage
     * @throws Exception = Blocklänge nicht passend
     */
    public static String decryptMessage(PairCipherBlockLength encryptedMessage, RSAKeys rsaKeys) throws Exception {
        if (!checkBlockLength(encryptedMessage.getBlockLength()-1, rsaKeys.getN())) {
            throw new Exception("blocklänge nicht passend");
        }
        String cipher = encryptedMessage.getCipher();
        Integer blockLength = encryptedMessage.getBlockLength();
        String messageText = "";
        messageText = decryptCipher(cipher, blockLength, rsaKeys);
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

    /**
     * checks correctness of block length based on script
     *
     * @param charBlockLength = Blocklänge
     * @param n               = p*q
     * @return True if blocklength correct
     */
    private static boolean checkBlockLength(int charBlockLength, BigInteger n) {
        return _charSetSize.pow(charBlockLength).compareTo(n) < 0
                && n.compareTo(_charSetSize.pow(charBlockLength + 1)) < 0;
    }

    /**
     * Auffüllen der Länge des Textes auf eine Länge die ganzzahlig durch die Blocklänge teilbar ist
     *
     * @param message         = gesamter Klartext
     * @param charBlockLength = Blocklänge
     * @return FilledMessage
     */
    private static String fillMessage(String message, int charBlockLength) {
        StringBuilder filledMessage = new StringBuilder(message);
        if (!(message.length() % charBlockLength == 0)) {
            int messageLengthDifference = charBlockLength - (message.length() % charBlockLength);
            filledMessage.append(messageFiller.repeat(messageLengthDifference));
        }
        return filledMessage.toString();
    }

    /**
     * teilen des Klartextes in Blocke und Erzeugen des Chiffres
     *
     * @param message         = gesamter Klartext
     * @param charBlockLength = Blocklänge
     * @return Chiffrat
     */
    private static String generateCipher(String message, int charBlockLength, RSAKeys rsaKeys) throws Exception {
        ArrayList<BigInteger> blockList = messageToBigIntBlockList(message, charBlockLength);
        ArrayList<BigInteger> cipherList = new ArrayList<>();
        for (BigInteger messageBlockNumber : blockList) {
            cipherList.add(FastExponentiation.exponentiation(messageBlockNumber, rsaKeys.getKey(), rsaKeys.getN()));
        }
        return bigIntListToString(cipherList, charBlockLength + 1);
    }

    /**
     * teilen des Chiffrats in Blöcke und Entschlüsseln dieser
     *
     * @param cipher      = Chiffrat
     * @param blockLength = Blocklänge
     * @param rsaKeys     = Schlüssel
     * @return Chiffrat
     */
    private static String decryptCipher(String cipher, Integer blockLength, RSAKeys rsaKeys) throws Exception {
        ArrayList<BigInteger> cipherList = messageToBigIntBlockList(cipher, blockLength);
        ArrayList<BigInteger> blockList = new ArrayList<>();
        for (BigInteger cipherNumber : cipherList) {
            blockList.add(FastExponentiation.exponentiation(cipherNumber, rsaKeys.getKey(), rsaKeys.getN()));
        }
        return bigIntListToString(blockList, blockLength - 1);
    }

    /**
     * message to BigInteger Block representation
     *
     * @param message = message
     * @param blockLength = Blöcklänge
     * @return
     */
    private static ArrayList<BigInteger> messageToBigIntBlockList(String message, int blockLength) {
        List<Integer> unicodeList = messageToIntList(message);
        ArrayList<BigInteger> messageInt = new ArrayList<>();

        int messageLength = message.length();
        for (int i = 0; i < messageLength; i += blockLength) {
            BigInteger blockValue = BigInteger.ZERO;
            for (int j = 0; j < blockLength; j++) {
                blockValue = blockValue.multiply(_charSetSize)
                        .add(BigInteger.valueOf(unicodeList.get(i + j)));
            }
            messageInt.add(blockValue);
        }

        return messageInt;
    }

    /**
     * message to integer representation
     *
     * @param message = message
     * @return List of Char -> Int Representation
     */
    private static ArrayList<Integer> messageToIntList(String message) {
        ArrayList<Integer> messageInt = new ArrayList<>();
        for (int i = 0; i < message.length(); i++) {
            messageInt.add((int) message.charAt(i));
        }
        return messageInt;
    }

    /**
     * BigInteger Block representation to message
     *
     * @param bigIntMessage = Block Representation of a String
     * @param blockLength = Blocklänge
     * @return message
     */
    public static String bigIntListToString(ArrayList<BigInteger> bigIntMessage, int blockLength) {
        List<Integer> unicodeList = new ArrayList<>();
        for (BigInteger cipherBlock : bigIntMessage) {
            List<Integer> unicodeBlockList = new ArrayList<>();
            for (int i = 0; i < blockLength; i++) {
                BigInteger unicode = cipherBlock.mod(_charSetSize);
                unicodeBlockList.add(unicode.intValue());
                cipherBlock = cipherBlock.divide(_charSetSize);
            }
            Collections.reverse(unicodeBlockList);
            unicodeList.addAll(unicodeBlockList);
        }

        StringBuilder unicodeMessage = new StringBuilder();
        for (int number : unicodeList) {
            unicodeMessage.appendCodePoint(number);
        }

        return unicodeMessage.toString();
    }
}
