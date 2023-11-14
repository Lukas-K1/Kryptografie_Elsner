package org.example.rsa.Algorithms;

import java.math.BigInteger;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.example.rsa.PairTypes.PairCipherBlockLength;
import org.example.rsa.PairTypes.RSAKeys;

public class BlockchiffreTest {
	@Test
	public void calculateBlockLength() {
		BigInteger n = BigInteger.valueOf(169300000);
		int expected = 2;
		int actual = Blockchiffre.calculateBlockLength(n);

		assertEquals(expected, actual);
	}

	@Test
	public void calcBlockLength() {
		int primeNumberLength = 1024;
		int expected = 64;
		int actual = Blockchiffre.calcBlockLength(primeNumberLength);

		assertEquals(expected, actual);
	}

	@Test
	public void decryptMessage() throws Exception {
		PairCipherBlockLength encryptedMessage = new PairCipherBlockLength("abc", 123);
		RSAKeys rsaKeys = new RSAKeys(null, null);
		String expected = "abc";
		String actual = Blockchiffre.decryptMessage(encryptedMessage, rsaKeys);

		assertEquals(expected, actual);
	}

	@Test
	public void encryptMessage() throws Exception {
		String message = "abc";
		RSAKeys rsaKeys = new RSAKeys(null, null);
		PairCipherBlockLength expected = new PairCipherBlockLength("abc", 123);
		PairCipherBlockLength actual = Blockchiffre.encryptMessage(message, rsaKeys);

		assertEquals(expected, actual);
	}
}
