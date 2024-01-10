package org.example.rsa.Handler;

import org.example.rsa.Algorithms.Blockchiffre;
import org.example.rsa.Algorithms.ExtendedEuclidean;
import org.example.rsa.Algorithms.FastExponentiation;
import org.example.rsa.Algorithms.Utilities;
import org.example.rsa.PairTypes.PairCipherBlockLength;
import org.example.rsa.PairTypes.PrivateKey;
import org.example.rsa.PairTypes.PublicKey;
import org.example.rsa.PairTypes.RSAKeyPair;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * class to manage all keys
 * their input
 * aswell as generating keys and the modulus
 */
public class RSAHandler {

    public static int millerRabinTrials;
    public static int primeNumberLength;

    private static BigInteger _m;
    private static BigInteger _p;
    private static BigInteger _q;
    private static BigInteger _n;
    private static BigInteger _phi;
    private static BigInteger _e;
    private static BigInteger _d;
    private static BigInteger _countN = BigInteger.ONE;
    private static int _blockLength;
    private static int _numberLengthPQ;
    public static RSAUser Alice = new RSAUser();
    public static RSAUser Bob = new RSAUser();

    public static void setN(BigInteger n) {
        _n = n;
    }

    public void setNumberLengthPQ(int primeNumberLength) {
        _numberLengthPQ = primeNumberLength / 2;
    }

    public int getBlockLength() {
        return _blockLength;
    }

    public static int setBlockLength() {
//        return _blockLength = Blockchiffre.calcBlockLength(primeNumberLength);
        return _blockLength = Blockchiffre.calculateBlockLength(_n);
    }

    public void setMillerRabinTrials(int millerRabinTrials) {
        this.millerRabinTrials = millerRabinTrials;
    }

    public void setPrimeNumberLength(int primeNumberLength) {
        this.primeNumberLength = primeNumberLength;
    }

    public void setM(int m) {
        this._m = BigInteger.valueOf(m);
    }

    /**
     * method for performance test while generating prime numbers
     * @throws Exception
     */
    public static void generateRandomPrimes() throws Exception {
        int numberLengthPQ = _numberLengthPQ;
        _p = calculateP(numberLengthPQ);
        _q = calculateQ(numberLengthPQ);
        _n = calculateN(_p, _q);
        _phi = calculatePhiN(_p, _q);
        _e = calculateE(_phi);
        _d = ExtendedEuclidean.getModInverse(_e, _phi);
    }

    public static RSAKeyPair generateKeyPairBob() throws Exception {
        Bob.setKeyPair(generateRSAKeyPair());
        return Bob.getKeyPair();
    }

    public static RSAKeyPair generateKeyPairAlice() throws Exception {
        Alice.setKeyPair(generateRSAKeyPair());
        return Alice.getKeyPair();
    }

    //TODO: implement
    public static void sentMessage(RSAUser receiver , String message){

    }
    public void sentMessageBob(String message){
        sentMessage(Alice, message);
    }

    public void sentMessageAlice(String message){
        sentMessage(Bob, message);
    }

    public PairCipherBlockLength encryptMessageBob(String message) throws Exception {
        return encryptMessage(message, Alice.getKeyPair().getPublicKey());
    }

    public PairCipherBlockLength encryptMessageAlice(String message) throws Exception {
        return encryptMessage(message, Bob.getKeyPair().getPublicKey());
    }

    public String decryptMessageBob(PairCipherBlockLength encryptedMessage) throws Exception {
        return decryptMessage(encryptedMessage, Bob.getKeyPair().getPrivateKey());
    }

    public String decryptMessageAlice(PairCipherBlockLength encryptedMessage) throws Exception {
        return decryptMessage(encryptedMessage, Alice.getKeyPair().getPrivateKey());
    }

    public void receiveMessageBob(String message) throws Exception {
    }

    public void receiveMessageAlice(String message) throws Exception {
    }

    /**
     * generates random primes and the specific keys used later with the primes
     * @return RSAKeyPair containing PrivateKey and PublicKey
     * @throws Exception
     */
    public static RSAKeyPair generateRSAKeyPair() throws Exception {
        BigInteger p = calculateP(_numberLengthPQ);
        BigInteger q = calculateQ(_numberLengthPQ);

        BigInteger n = calculateN(p, q);
        BigInteger phi = calculatePhiN(p, q);

        BigInteger e = calculateE(phi);

        BigInteger d;

        try {
            d = ExtendedEuclidean.getModInverse(e, phi);
            //d = generateD(e, phi);
        } catch (Exception exception) {
            System.out.println(exception.getMessage() + "\n" + "private key could not be generated");
            return generateRSAKeyPair();
        }

        PublicKey publicKey = new PublicKey(e, n);
        PrivateKey privateKey = new PrivateKey(d, n);
        return new RSAKeyPair(publicKey, privateKey);
    }

    /**
     * calls block cipher encrypt method
     * @param message = string to be encrypted
     * @param publicKey = pair (e,n)
     * @return
     * @throws Exception
     */
    public static PairCipherBlockLength encryptMessage(String message, PublicKey publicKey) throws Exception {
        return Blockchiffre.encryptMessage(message, publicKey, _blockLength);
    }

    /**
     * calls block cipher decrypt
     * @param encryptedMessage = unicode message to be decrypted
     * @param privateKey = pair (d,n)
     * @return
     * @throws Exception
     */
    public static String decryptMessage(PairCipherBlockLength encryptedMessage, PrivateKey privateKey) throws Exception {
        return Blockchiffre.decryptMessage(encryptedMessage, privateKey);
    }

    /**
     * calls hash method from utilities
     * @param message = message that gets signed
     * @return hashed message (signed)
     * @throws Exception
     */
    public static String signatureForMessage(String message, PrivateKey privateKey) throws Exception {
        return Utilities.hash256(message, privateKey);
    }

    /**
     *
     * @param hashedMessage message which signature gets checked
     * @param signature
     * @return true if hash equals value of signature of the message
     * @throws Exception
     */
    public static boolean validSignature(String hashedMessage, String signature, PublicKey publicKey) throws Exception {
        if (primeNumberLength < 258){
            throw new Exception("prime number length is too small");
        }
        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        final byte[] hashbytes = digest.digest(hashedMessage.getBytes(StandardCharsets.UTF_8));
        BigInteger hashedInteger = new BigInteger(1, hashbytes);
        BigInteger signatureInteger = new BigInteger(signature, 16);

        BigInteger decryptedSignature = FastExponentiation.exponentiation(signatureInteger, publicKey.getKey(), publicKey.getN());
        return decryptedSignature.equals(hashedInteger);
    }

    /**
     * calculates modulo
     * @param p = random prime
     * @param q = random prime
     * @return n = p * q
     */
    public static BigInteger calculateN(BigInteger p, BigInteger q) {
        return _n = p.multiply(q);
    }

    /**
     * calculates random prime p
     * @param primeNumberLength
     * @return random prime
     * @throws Exception
     */
    public static BigInteger calculateP(int primeNumberLength) throws Exception {
        BigInteger possibleP;
        BigInteger a = BigInteger.TWO.pow(primeNumberLength - 1);
        BigInteger b = BigInteger.TWO.pow(primeNumberLength);
        do {
            possibleP = Utilities.generateRandomPrime(_m, _countN, a, b, millerRabinTrials);
        } while (possibleP.equals(_q));
        return _p = possibleP;
    }

    /**
     * calculates random prime q
     * @param primeNumberLength
     * @return random prime
     * @throws Exception
     */
    public static BigInteger calculateQ(int primeNumberLength) throws Exception {
        BigInteger possibleQ;
        BigInteger a = BigInteger.TWO.pow(primeNumberLength- 1);
        BigInteger b = BigInteger.TWO.pow(primeNumberLength);
        do {
            possibleQ = Utilities.generateRandomPrime(_m, _countN, a, b, millerRabinTrials);
        } while (possibleQ.equals(_p));
        return _q = possibleQ;
    }

    /**
     * calculates e for public key
     * @param phi = p-1 * q - 1
     * @return random prime e
     * @throws Exception
     */
    public static BigInteger calculateE(BigInteger phi) throws Exception {
        BigInteger e;

        BigInteger lowerBoundE = BigInteger.TWO;
        BigInteger upperBoundE = phi.subtract(BigInteger.ONE);

        do {
            e = Utilities.generateRandomPrime(_m, _countN, lowerBoundE, upperBoundE, millerRabinTrials);
        }
        while (!ExtendedEuclidean.gcd(e, phi).equals(BigInteger.ONE) || e.compareTo(phi) >= 0);
        return e;
    }

    /**
     * calulates phiN
     * @param p
     * @param q
     * @return phi(N) = p - 1 * q - 1
     */
    public static BigInteger calculatePhiN(BigInteger p, BigInteger q) {
        return _phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
    }

    /**
     * generates private key
     *
     * @param e
     * @param phi
     * @return
     * @throws Exception
     *
     */
    private static BigInteger generateD(BigInteger e, BigInteger phi) throws Exception {
//        e = e.mod(phi);
        BigInteger d = generateDHelper(phi, e);
        return d.mod(e);
    }

    /**
     * functioning attempt for mod inverse of e
     * moved and improved -> ExtendedEuclidean
     * @param phi
     * @param e
     * @return
     * @throws Exception
     */
    private static BigInteger generateDHelper(BigInteger phi, BigInteger e) throws Exception {
        if (!phi.mod(e).equals(BigInteger.ZERO)) {
            return generateDHelper(e, phi.mod(e))
                    .multiply(phi)
                    .subtract(BigInteger.ONE)
                    .divide(e.multiply(BigInteger.valueOf(-1)));
        }
        else if (e.equals(BigInteger.ONE)) {
            return BigInteger.ONE;
        } else {
            throw new Exception("values are not coprime");
        }
    }
}
