package org.example.rsa.Algorithms;

import org.example.rsa.PairTypes.PairCipherBlockLength;
import org.example.rsa.PairTypes.RSAKeys;

import java.math.BigInteger;
import java.util.ArrayList;
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

    private static BigInteger numberMessageToBigInt(ArrayList<Integer> messageInt, int blockLength) {
        BigInteger messageBigInt = BigInteger.ZERO;
        for (int number : messageInt) {
            messageBigInt = messageBigInt.add(BigInteger.valueOf(number).multiply(_charSetSize.pow(blockLength - 1)));
            blockLength--;
        }
        return messageBigInt;
    }

    private static String bigIntToUnicode(BigInteger bigIntMessage, int blocklength) {
        StringBuilder unicodeMessage = new StringBuilder();
        List<Integer> unicodeList = new ArrayList<>();
        int counter = 0;
        while (!bigIntMessage.equals(BigInteger.ZERO)) {
            BigInteger unicode = bigIntMessage.mod(_charSetSize);
            unicodeList.add(unicode.intValue());
            bigIntMessage = bigIntMessage.divide(_charSetSize);
            counter++;
        }
        while (counter < blocklength + 1) {
            unicodeList.add(0);
            counter++;
        }
        for (int number : unicodeList) {
            unicodeMessage.append(Character.toChars(number));
        }
        return unicodeMessage.toString();
    }

    private static BigInteger unicodeToBigInt(String unicodeMessage, int blocklength) {
        List<Integer> unicodeList = messageToInt(unicodeMessage);
        BigInteger bigIntMessage = BigInteger.ZERO;
        for (int i = 0; i < unicodeList.size(); i++) {
            for (int j = 0; j < blocklength - i; j++) {
                bigIntMessage = bigIntMessage.add(BigInteger.valueOf(unicodeList.get(i + j)));
            }
        }
        bigIntMessage = bigIntMessage.multiply(_charSetSize.pow(blocklength));
        return bigIntMessage;
    }

    private static String bigIntToMessage(BigInteger bigIntMessage, int blocklength) {
        StringBuilder message = new StringBuilder();
        List<Integer> messageList = new ArrayList<>();

        while (blocklength > 0){
            BigInteger unicode = bigIntMessage.mod(_charSetSize.pow(blocklength));
            messageList.add(unicode.intValue());
            bigIntMessage = bigIntMessage.divide(_charSetSize.pow(blocklength));
            blocklength--;
        }
        // alternativ
//        List<BigInteger> numberBlocks = new ArrayList<>();
//        while(!bigIntMessage.equals(BigInteger.ZERO)){
//            numberBlocks.add(bigIntMessage.mod(_charSetSize.pow(blocklength+1)));
//            bigIntMessage = bigIntMessage.divide(_charSetSize.pow(blocklength+1));
//        }
//
//        for (BigInteger numberBlock : numberBlocks) {
//            for (int i = blocklength; i >= 0; i--) {
//                BigInteger blockValue = numberBlock.divide(_charSetSize.pow(i));
//                messageList.add(blockValue.intValue());
//                numberBlock = numberBlock.subtract(blockValue.multiply(_charSetSize.pow(i)));
//            }
//        }

        for (int number : messageList) {
            message.append(Character.toChars(number));
        }
        return message.toString();
    }
}
