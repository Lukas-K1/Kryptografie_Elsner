package org.example.rsa.Algorithms;

import org.example.rsa.PairTypes.PairCipherBlockLength;
import org.example.rsa.PairTypes.RSAKeys;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Blockchiffre {
    private static final BigInteger _charSetSize = BigInteger.valueOf(55295);
    private static final String messageFiller = "\u0000";

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

    public static PairCipherBlockLength encryptMessage(String message, RSAKeys rsaKeys, int blockLength) throws Exception {
        if (!checkBlockLength(blockLength, rsaKeys.getN())) {
            throw new Exception("blocklänge nicht passend");
        }
        String filledMessage = fillMessage(message, blockLength);
        String cipher = generateCipher(filledMessage, blockLength, rsaKeys);
        return new PairCipherBlockLength(cipher, blockLength + 1);
    }

    public static String decryptMessage(PairCipherBlockLength encryptedMessage, RSAKeys rsaKeys) throws Exception {
        String cipher = encryptedMessage.getCipher();
        Integer blockLength = encryptedMessage.getBlockLength();
        String messageText = "";
        messageText = decryptCipher(cipher, blockLength, rsaKeys);
        return messageText;
    }

    private static String decryptCipher(String cipher, Integer blockLength, RSAKeys rsaKeys) throws Exception {
        String messageText = "";
        while (!cipher.isEmpty()) {
            String cipherBlock = cipher.substring(0, blockLength);
            //BigInteger cipherNumber = convertChiffreToNumber(cipherBlock, blockLength - 1);
            BigInteger cipherNumber = unicodeToBigInt(cipherBlock, blockLength - 1);
            BigInteger members = FastExponentiation.exponentiation(cipherNumber, rsaKeys.getKey(), rsaKeys.getN());
            //String textBlock = convertToTextBlock(members, blockLength - 2);
            String textBlock = bigIntToMessage(members, blockLength - 2);
            messageText = messageText.concat(textBlock);
            cipher = cipher.substring(blockLength);
        }
        return messageText;
    }

    private static String convertToTextBlock(BigInteger m, int power) throws Exception {

        String textBlock = "";
        for (int i = 0; i < power; power--) {
            BigInteger firstCharacter = m.mod(_charSetSize);
            if (firstCharacter.compareTo(_charSetSize) > 0) {
                throw new Exception("the character representing unicode + " + firstCharacter + " is not allowed");
            }
            textBlock = textBlock.concat(String.valueOf(Character.toChars(firstCharacter.intValue())));
            m = m.divide(_charSetSize);
        }
        return textBlock;

    }

    private static BigInteger convertChiffreToNumber(String cipherBlock, int power) {
        BigInteger chiffreNumber = BigInteger.ZERO;
        for (int i = 0; i < cipherBlock.length(); i++) {
            BigInteger charToDecimal = BigInteger.valueOf(cipherBlock.charAt(i));
            chiffreNumber = chiffreNumber.add(charToDecimal.multiply(_charSetSize.pow(power)));
            power--;
        }

        return chiffreNumber;
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

    public static int calcBlockLength(int primeNumberlength) {
        double log = (primeNumberlength * (Math.log(2) / Math.log(_charSetSize.doubleValue())));
        return (int) Math.floor(log);
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
     * @param charBlockLength = Blocklänge
     * @return Chiffrat
     */
    private static String generateCipher(String message, int charBlockLength, RSAKeys rsaKeys) throws Exception {
        String cipher = "";
        while (!message.isEmpty()) {
            //BigInteger messageBlockNumber = convertToNumberBlock(message.substring(0, charBlockLength), charBlockLength - 1);
            ArrayList<Integer> messageIntPresentation = messageToInt(message.substring(0, charBlockLength));
            BigInteger messageBlockNumber = numberMessageToBigInt(messageIntPresentation, charBlockLength);
            BigInteger messageBlockChiffre = FastExponentiation.exponentiation(messageBlockNumber, rsaKeys.getKey(), rsaKeys.getN());
            //String chiffreBlock = generateChiffreBlock(messageBlockChiffre, charBlockLength);
            String chiffreBlock = bigIntToUnicode(messageBlockChiffre, charBlockLength);
            cipher = cipher.concat(chiffreBlock);
            message = message.substring(charBlockLength);
        }

        return cipher;
    }

    private static String generateChiffreBlock(BigInteger messageBlockChiffre, int charBlockLength) {
        String cipherBlock = "";
        for (int i = 0; i < charBlockLength; i++) {
            BigInteger charToDecimal = messageBlockChiffre.divide(_charSetSize);
            cipherBlock = cipherBlock.concat(String.valueOf(Character.toChars(charToDecimal.intValue())));
            messageBlockChiffre = messageBlockChiffre.mod(_charSetSize);
            charBlockLength--;
        }
        return cipherBlock;
    }

    private static BigInteger convertToNumberBlock(String messageBlock, int power) throws Exception {
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < messageBlock.length(); i++) {
            BigInteger charToDecimal = BigInteger.valueOf((int) messageBlock.charAt(i));
            result = result.add(charToDecimal.multiply(_charSetSize));

            if (charToDecimal.compareTo(_charSetSize) > 0) {
                throw new Exception(messageBlock.charAt(0) + " is out of valid characters");
            }
            power--;
        }
        return result;
    }

    private static ArrayList<Integer> messageToInt(String message) {
        ArrayList<Integer> messageInt = new ArrayList<>();
        for (int i = 0; i < message.length(); i++) {
            messageInt.add((int) message.charAt(i));
        }
        return messageInt;
    }

    public static BigInteger numberMessageToBigInt(ArrayList<Integer> messageInt, int blockLength) {
        BigInteger messageBigInt = BigInteger.ZERO;
        int messageSize = messageInt.size();
        int currentBlockLength = blockLength;

        for (int i = 0; i < messageSize; i++) {
            int number = messageInt.get(i);
            messageBigInt = messageBigInt.multiply(BigInteger.valueOf(_charSetSize.intValue()))
                    .add(BigInteger.valueOf(number));

            currentBlockLength--;

            if (currentBlockLength == 0 || i == messageSize - 1) {
                messageBigInt = messageBigInt.shiftLeft((blockLength - currentBlockLength) * 16);
                currentBlockLength = blockLength;
            }
        }

        return messageBigInt;
    }

    public static String bigIntToUnicode(BigInteger bigIntMessage, int blockLength) {
        List<Integer> unicodeList = new ArrayList<>();

        while (!bigIntMessage.equals(BigInteger.ZERO)) {
            BigInteger unicode = bigIntMessage.mod(BigInteger.valueOf(_charSetSize.intValue()));
            unicodeList.add(unicode.intValue());
            bigIntMessage = bigIntMessage.divide(BigInteger.valueOf(_charSetSize.intValue()));
        }

        int paddingSize = blockLength + 1 - unicodeList.size();
        for (int i = 0; i < paddingSize; i++) {
            unicodeList.add(0);
        }

        Collections.reverse(unicodeList);

        StringBuilder unicodeMessage = new StringBuilder();
        for (int number : unicodeList) {
            unicodeMessage.appendCodePoint(number);
        }

        return unicodeMessage.toString();
    }

    public static BigInteger unicodeToBigInt(String unicodeMessage, int blockLength) {
        List<Integer> unicodeList = messageToInt(unicodeMessage);
        BigInteger bigIntMessage = BigInteger.ZERO;

        int messageLength = unicodeMessage.length();
        for (int i = 0; i < messageLength; i += blockLength) {
            BigInteger blockValue = BigInteger.ZERO;

            for (int j = 0; j < blockLength && i + j < messageLength; j++) {
                blockValue = blockValue.multiply(BigInteger.valueOf(_charSetSize.intValue()))
                        .add(BigInteger.valueOf(unicodeList.get(i + j)));
            }

            bigIntMessage = bigIntMessage.shiftLeft(blockLength * 16).add(blockValue);
        }

        return bigIntMessage;
    }

    public static String bigIntToMessage(BigInteger bigIntMessage, int blockLength) {
        List<Integer> messageList = new ArrayList<>();

        while (blockLength > 0) {
            BigInteger unicode = bigIntMessage.mod(BigInteger.valueOf(_charSetSize.intValue()));
            messageList.add(unicode.intValue());
            bigIntMessage = bigIntMessage.divide(BigInteger.valueOf(_charSetSize.intValue()));
            blockLength--;
        }

        Collections.reverse(messageList);

        StringBuilder message = new StringBuilder();
        for (int number : messageList) {
            message.append(Character.toChars(number));
        }

        return message.toString();
    }
}
