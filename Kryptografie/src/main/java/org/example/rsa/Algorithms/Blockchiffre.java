package org.example.rsa.Algorithms;

import org.example.rsa.PairTypes.PairCipherBlockLength;
import org.example.rsa.PairTypes.RSAKeys;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Blockchiffre {
    private static final BigInteger _charSetSize = BigInteger.valueOf(55296);
    private static final BigInteger _charSetSize2 = BigInteger.valueOf(47);
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
        ArrayList<BigInteger> blockList = messageToBigIntList(message, charBlockLength);
        ArrayList<BigInteger> cipherList= new ArrayList<>();
        System.out.println("BlockList gen");
        System.out.println(blockList);
        for (BigInteger messageBlockNumber: blockList){
            cipherList.add(FastExponentiation.exponentiation(messageBlockNumber, rsaKeys.getKey(), rsaKeys.getN()));
        }
        System.out.println("CipherList gen");
        System.out.println(cipherList);
        return bigIntListToUnicode(cipherList, charBlockLength + 1);
    }

    private static String decryptCipher(String cipher, Integer blockLength, RSAKeys rsaKeys) throws Exception {
        ArrayList<BigInteger> cipherList = messageToBigIntList(cipher, blockLength);
        ArrayList<BigInteger> blockList= new ArrayList<>();
        System.out.println("CipherList dec");
        System.out.println(cipherList);
        for (BigInteger cipherNumber: cipherList){
            blockList.add(FastExponentiation.exponentiation(cipherNumber, rsaKeys.getKey(), rsaKeys.getN()));
        }
        System.out.println("BlockList dec");
        System.out.println(blockList);
        return bigIntListToUnicode(blockList, blockLength - 1);
    }

    public static ArrayList<BigInteger> messageToBigIntList(String unicodeMessage, int blockLength) {
        List<Integer> unicodeList = messageToIntList(unicodeMessage);
        ArrayList<BigInteger> messageInt = new ArrayList<>();

        int messageLength = unicodeMessage.length();
        for (int i = 0; i < messageLength; i += blockLength) {
            BigInteger blockValue = BigInteger.ZERO;
            for (int j = 0; j < blockLength; j++) {
                blockValue = blockValue.multiply(_charSetSize)
                        .add(BigInteger.valueOf(unicodeList.get(i+j)));
            }
            messageInt.add(blockValue);
        }

        return messageInt;
    }

    /**
     * alternative implementattion for testing
     * DO NOT REMOVE TODO
     * @param unicodeMessage
     * @param blockLength
     * @return
     */
    public static ArrayList<BigInteger> messageToBigIntList2(String unicodeMessage, int blockLength) {
        List<Integer> unicodeList = messageToIntList(unicodeMessage);
        ArrayList<BigInteger> messageInt = new ArrayList<>();
        ArrayList<ArrayList<Integer>> blocks = new ArrayList<>();
        for (int i = 0; i < unicodeList.size(); i++) {
            if (i % blockLength == 0) {
                blocks.add(new ArrayList<>());
            }
            blocks.get(i / blockLength).add(unicodeList.get(i));
        }

        for (ArrayList<Integer> block : blocks) {
            BigInteger blockValue = BigInteger.ZERO;
            for (Integer number : block) {
                for (int i = 0; i < blockLength; i++) {
                    blockValue = blockValue.add(BigInteger.valueOf(number).multiply(_charSetSize.pow(i)));
                }
            }
            messageInt.add(blockValue);
        }
//        int messageLength = unicodeMessage.length();
//        for (int i = 0; i < messageLength; i++) {
//            BigInteger blockValue = BigInteger.ZERO;
//
//            for (int j = 0; j < blockLength; j++) {
//                blockValue = blockValue.add(BigInteger.valueOf(unicodeList.get(i)).multiply(_charSetSize.pow(j)));
//            }
//            messageInt.add(blockValue);
//        }

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
        for (BigInteger cipherBlock : bigIntMessage){
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

    /**
     * alternative implementattion for testing
     * DO NOT REMOVE TODO
     * @param bigIntMessage
     * @param blockLength
     * @return
     */
    public static String bigIntListToUnicode2(ArrayList<BigInteger> bigIntMessage, int blockLength) {
        List<Integer> unicodeList = new ArrayList<>();


        for (BigInteger cipherBlock : bigIntMessage){
            while (!cipherBlock.equals(BigInteger.ZERO)) {
                unicodeList.add(0,cipherBlock.mod(_charSetSize).intValue());
                cipherBlock = cipherBlock.divide(_charSetSize);
            }
//            for (int i = 0; i < blockLength; i++) {
//                BigInteger unicode = cipherBlock.divide(_charSetSize.pow(i));
//                unicodeList.add(unicode.intValue());
//                cipherBlock = cipherBlock.mod(_charSetSize.pow(i));
//            }
        }
        int count = 0;
        while (count < blockLength) {
            unicodeList.add(0, 0);
            count++;
        }

        StringBuilder unicodeMessage = new StringBuilder();
        for (int number : unicodeList) {
            unicodeMessage.appendCodePoint(number);
        }

        return unicodeMessage.toString();
    }
    private static String decryptMessage(String message,RSAKeys keys, int blocklength) {
        List<Integer> unicode = new ArrayList<>();
        for(int i = 0; i < message.length(); i++){
            unicode.add((int)message.charAt(i));
        }
        List<List<BigInteger>> encryptedNumericBlocks = new ArrayList<>();
        for (int i = 0; i < unicode.size(); i += blocklength) {
            List<BigInteger> block = new ArrayList<>();
            for (int j = 0; j < blocklength; j++) {
                if (i + j < unicode.size()) {
                    block.add(BigInteger.valueOf(unicode.get(i + j)));
                } else {
                    block.add(BigInteger.ZERO);
                }
            }
            encryptedNumericBlocks.add(block);
        }
        List<BigInteger> encryptedNumericMessages = new ArrayList<>();
        for (List<BigInteger> encryptedNumeric : encryptedNumericBlocks) {
            BigInteger sum = BigInteger.ZERO;
            for (int j = 0; j < encryptedNumeric.size(); j++) {
                BigInteger temp = encryptedNumeric.get(encryptedNumeric.size() - j - 1).multiply(_charSetSize.pow(j));
                sum = sum.add(temp);
            }
            encryptedNumericMessages.add(sum);
        }
        List<BigInteger> decryptedMessage = new ArrayList<>();
        for (BigInteger encryptedNumericMessage : encryptedNumericMessages) {
            decryptedMessage.add(FastExponentiation.exponentiation(encryptedNumericMessage, keys.getKey(), keys.getN()));
        }
        List<Integer> numericMessage = new ArrayList<>();
        List<Integer> decryptedMessageNumbers = new ArrayList<>();
        for (BigInteger decrypted : decryptedMessage) {

            // Divide message into blocks of size blockSize
            List<BigInteger> blocks = new ArrayList<>();

            BigInteger numberSystemToThePowerOfBlockSize = _charSetSize.pow(blocklength);

            while (!decrypted.equals(BigInteger.ZERO)) {
                blocks.add(decrypted.mod(numberSystemToThePowerOfBlockSize));
                decrypted = decrypted.divide(numberSystemToThePowerOfBlockSize);
            }

            for(BigInteger block : blocks) {
                // For each block go through every character and convert it to a number
                // in the number system with respect to its index
                for (int i = blocklength - 1; i >= 0; i--) {
                    BigInteger numberSystemToThePowerOfI = _charSetSize.pow(i);
                    BigInteger blockValue = block.divide(numberSystemToThePowerOfI);
                    decryptedMessageNumbers.add(blockValue.intValue());
                    block = block.subtract(blockValue.multiply(numberSystemToThePowerOfI));
                }
            }
            numericMessage.addAll(decryptedMessageNumbers);
        }
        StringBuilder text = new StringBuilder();
        for (Integer integer : numericMessage) {
            text.append((char) integer.intValue());
        }
        return text.toString();
    }
}
