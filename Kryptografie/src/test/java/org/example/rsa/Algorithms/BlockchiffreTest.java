package org.example.rsa.Algorithms;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.example.rsa.PairTypes.PairCipherBlockLength;
import org.example.rsa.PairTypes.RSAKeys;

public class BlockchiffreTest {
	@Test
	public void calculateBlockLength() {
		String blockLength = "7646106098027519744244395349050541229862450072951769912421603079185396677401061286273210500317429813689758757404464272049551073948015867458446688676577403";
		BigInteger n = BigInteger.valueOf(Long.parseLong(blockLength));
		int expected = 32;
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
		PairCipherBlockLength encryptedMessage = new PairCipherBlockLength("R8F9BX-YOI,FQC2LZGO9OIZLNC5", 9);
		String number = "791569306435939";
		RSAKeys rsaKeys = new RSAKeys(BigInteger.valueOf(Long.parseLong("577322589362687")), BigInteger.valueOf(Long.parseLong(number)));
		String expected = "MATHEMATIK IST SPANNEND!";
		String actual = Blockchiffre.decryptMessage(encryptedMessage, rsaKeys);

		assertEquals(expected, actual);
	}

	@Test
	public void encryptMessage() throws Exception {
		String message = "MATHEMATIK IST SPANNEND!";
		RSAKeys rsaKeys = new RSAKeys(BigInteger.valueOf(Long.parseLong("15485863")), BigInteger.valueOf(Long.parseLong("791569306435939")));
		PairCipherBlockLength expected = new PairCipherBlockLength("R8F9BX-YOI,FQC2LZGO9OIZLNC5", 9);
		PairCipherBlockLength actual = Blockchiffre.encryptMessage(message, rsaKeys, 8);
		assertEquals(expected.getCipher(), actual.getCipher());
		assertEquals(expected.getBlockLength(), actual.getBlockLength());
	}
}
