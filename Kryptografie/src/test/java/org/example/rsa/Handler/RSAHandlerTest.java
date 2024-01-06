package org.example.rsa.Handler;

import org.example.rsa.PairTypes.PrivateKey;
import org.example.rsa.PairTypes.PublicKey;
import org.example.rsa.PairTypes.RSAKeyPair;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RSAHandlerTest {
	@Test
	public void validSignature() throws Exception {
		RSAHandler r = new RSAHandler();
		String hashedMessage = "abc";
		String signature = "abc";
		boolean expected = true;
		PublicKey publicKey = new PublicKey(null, null);
		boolean actual = r.validSignature(hashedMessage, signature, publicKey);

		assertEquals(expected, actual);
	}

	@Test
	public void calculatePhiN() {
		BigInteger p = null;
		BigInteger q = null;
		BigInteger expected = null;
		BigInteger actual = RSAHandler.calculatePhiN(p, q);

		assertEquals(expected, actual);
	}

	@Test
	public void calculateE() throws Exception {
		BigInteger phi = null;
		BigInteger expected = null;
		BigInteger actual = RSAHandler.calculateE(phi);

		assertEquals(expected, actual);
	}

	@Test
	public void calculateQ() throws Exception {
		int primeNumberLength = 123;
		BigInteger expected = null;
		BigInteger actual = RSAHandler.calculateQ(primeNumberLength);

		assertEquals(expected, actual);
	}

	@Test
	public void calculateP() throws Exception {
		int primeNumberLength = 123;
		BigInteger expected = null;
		BigInteger actual = RSAHandler.calculateP(primeNumberLength);

		assertEquals(expected, actual);
	}

	@Test
	public void validSignatureTODO() throws Exception {
		RSAHandler r = new RSAHandler();
		String hashedMessage = "abc";
		String signature = "abc";
		boolean expected = true;
		PublicKey publicKey = new PublicKey(null, null);
		boolean actual = r.validSignature(hashedMessage, signature, publicKey);

		assertEquals(expected, actual);
	}

	@Test
	public void generateRSAKeyPair() throws Exception {
		RSAKeyPair expected = new RSAKeyPair(new PublicKey(null, null), new PrivateKey(null, null));
		RSAKeyPair actual = RSAHandler.generateRSAKeyPair();

		assertEquals(expected, actual);
	}

	@Test
	public void generateRandomPrimes() throws Exception {
		RSAHandler.generateRandomPrimes();
	}
}
