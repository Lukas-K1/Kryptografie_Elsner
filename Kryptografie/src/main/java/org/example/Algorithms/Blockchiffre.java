package org.example.Algorithms;

import java.math.BigInteger;
import java.util.function.BiFunction;

public class Blockchiffre {
    private static final BigInteger _charSetSize = BigInteger.valueOf(55295);
    private static final String messageFiller = "";

    //TODO generate type to contain block length and cipher
    public static void encryptMessage(String message, BigInteger key, BigInteger n) throws Exception {
        int charBlockLength = calculateBlockLength(n);
        if (!checkBlockLength(charBlockLength, n)) {
            throw new Exception("blocklänge nicht passend");
        }
        String filledMessage = fillMessage(message, charBlockLength);
        generateCipher(message, charBlockLength, key, n);
    }

    //TODO implement
    public static void decryptMessage(String encryptedMessage, BigInteger key, BigInteger n) throws NoSuchMethodException {
        throw new NoSuchMethodException();
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


    private static boolean checkBlockLength(int charBlockLength, BigInteger n) {
        return _charSetSize.pow(charBlockLength).compareTo(n) < 0 && n.compareTo(_charSetSize.pow(charBlockLength + 1)) < 0;
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
     *
     * @param message         = gesamter Klartext
     * @param charBlockLength = x
     * @param key             = Verschlüsselungswert
     * @param n               =
     * @return Chiffrat
     */
    private static String generateCipher(String message, int charBlockLength, BigInteger key, BigInteger n) throws Exception {
        String cipher = "";
        while (message.length() > 0) {
            BigInteger messageBlockNumber = convertToNumberBlock(message.substring(0, charBlockLength), charBlockLength - 1);
            BigInteger messageBlockChiffre = FastExponantiation.exponentiation(messageBlockNumber, key, n);
            String chiffreBlock = generateChiffreBlock(messageBlockChiffre, charBlockLength);
            cipher = cipher.concat(chiffreBlock);
            message = message.substring(charBlockLength);
        }

        return cipher;
    }

    private static String generateChiffreBlock(BigInteger messageBlockChiffre, int charBlockLength) {
        String cipherCharacter = String.valueOf(Character.toChars(Integer.parseInt(String.valueOf(messageBlockChiffre.divide(_charSetSize.pow(charBlockLength))))));
        if (charBlockLength < 0) {
            return cipherCharacter;
        }
        return cipherCharacter.concat(generateChiffreBlock(messageBlockChiffre.mod(_charSetSize.pow(charBlockLength)), charBlockLength - 1));
    }

    private static BigInteger convertToNumberBlock(String messageBlock, int power) throws Exception {
        BigInteger charToDecimal = BigInteger.valueOf(messageBlock.charAt(0));
        BigInteger x = charToDecimal.multiply(_charSetSize.pow(power));

        if (charToDecimal.compareTo(_charSetSize) > 0) {
            throw new Exception(messageBlock.charAt(0) + " is out of valid characters");
        } else if (messageBlock.length() > 1) {
            return x.add(convertToNumberBlock(messageBlock.substring(1), power - 1));
        } else {
            return x;
        }
    }
}
