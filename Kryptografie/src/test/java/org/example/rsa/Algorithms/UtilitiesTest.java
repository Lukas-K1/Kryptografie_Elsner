package org.example.rsa.Algorithms;

import java.math.BigInteger;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.security.NoSuchAlgorithmException;
import org.example.rsa.PairTypes.PrivateKey;
import org.example.rsa.PairTypes.PublicKey;

public class UtilitiesTest {
	@Test
	public void getRandomBigInteger() {
		BigInteger a = null;
		BigInteger b = null;
		BigInteger n = null;
		BigInteger m = null;
		BigInteger expected = null;
		BigInteger actual = Utilities.getRandomBigInteger(a, b, n, m);

		assertEquals(expected, actual);
	}

	@Test
	public void generateRandom() throws Exception {
		BigInteger m = null;
		BigInteger n = null;
		BigInteger a = null;
		BigInteger b = null;
		int millerRabinTrials = 123;
		BigInteger expected = null;
		BigInteger actual = Utilities.generateRandom(m, n, a, b, millerRabinTrials);

		assertEquals(expected, actual);
	}

	@Test
	public void hash256() throws NoSuchAlgorithmException {
		String message = "abc";
		PrivateKey privateKey = new PrivateKey(null, null);
		String expected = "abc";
		String actual = Utilities.hash256(message, privateKey);

		assertEquals(expected, actual);
	}

	@Test
	public void isSignatureValid() throws NoSuchAlgorithmException {
		String message = "abc";
		String signature = "abc";
		PublicKey publicKey = new PublicKey(null, null);
		boolean expected = true;
		boolean actual = Utilities.isSignatureValid(message, signature, publicKey);

		assertEquals(expected, actual);
	}
}
