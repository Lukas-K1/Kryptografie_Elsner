package org.example.rsa.Algorithms;

import java.math.BigInteger;

public class Blockchiffre {
    private static final BigInteger _charSetSize = BigInteger.valueOf(55295);
    private static final String messageFiller = "\u0000";

    //TODO generate type to contain block length and cipher
    public static String encryptMessage(String message, BigInteger key, BigInteger n) throws Exception {
        int charBlockLength = calculateBlockLength(n);
        if (!checkBlockLength(charBlockLength, n)) {
            throw new Exception("blocklänge nicht passend");
        }
        String filledMessage = fillMessage(message, charBlockLength);
        return generateCipher(filledMessage, charBlockLength, key, n);
    }

    //TODO implement
    public static String decryptMessage(String encryptedMessage, BigInteger key, BigInteger n) throws Exception {
        String cipher = encryptedMessage; // TODO properly retrieve cipher
        Integer blockLength = null; // TODO should be received from input, ie TODO for encryptMessage
        String messageText = "";
        while (cipher.length() > 0) {
            String cipherBlock = cipher.substring(0, blockLength);
            BigInteger c = convertChiffreToNumber(cipherBlock, blockLength - 1);
            BigInteger m = FastExponentiation.exponentiation(c, key, n);
            String textBlock = convertToTextBlock(m, blockLength - 2);
            messageText = messageText.concat(textBlock);
            cipher = cipher.substring(blockLength);
        }
        return messageText;
    }

    private static String convertToTextBlock(BigInteger m, int power) throws Exception {
        BigInteger firstCharacter = m.divide(_charSetSize.pow(power));

        if (firstCharacter.compareTo(_charSetSize) > 0) {
            throw new Exception("the character representing unicode + " + firstCharacter + " is not allowed");
        }

        String textBlock = String.valueOf(Character.toChars(firstCharacter.intValue()));
        // TODO check for space placeholder
        if (textBlock.contains(messageFiller)) {
            return "";
        } else if (power > 0) {
            return textBlock.concat(convertToTextBlock(m.mod(_charSetSize.pow(power)), power - 1));
        } else {
            return textBlock;
        }

    }

    private static BigInteger convertChiffreToNumber(String cipherBlock, int power) {
        BigInteger chiffreNumber = BigInteger.valueOf(cipherBlock.charAt(0)).multiply(_charSetSize.pow(power));
        if (cipherBlock.length() <= 1) {
            return chiffreNumber;
        }
        return chiffreNumber.add(convertChiffreToNumber(cipherBlock.substring(1), power - 1));
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
            BigInteger messageBlockChiffre = FastExponentiation.exponentiation(messageBlockNumber, key, n);
            String chiffreBlock = generateChiffreBlock(messageBlockChiffre, charBlockLength);
            cipher = cipher.concat(chiffreBlock);
            message = message.substring(charBlockLength);
        }

        return cipher;
    }

    private static String generateChiffreBlock(BigInteger messageBlockChiffre, int charBlockLength) {
        String cipherCharacter = String.valueOf(Character.toChars(Integer
                .parseInt(String.valueOf(messageBlockChiffre.divide(_charSetSize.pow(charBlockLength))))));
        if (charBlockLength < 0) {
            return cipherCharacter;
        }
        return cipherCharacter.concat(
                generateChiffreBlock(messageBlockChiffre.mod(_charSetSize.pow(charBlockLength)), charBlockLength - 1));
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
