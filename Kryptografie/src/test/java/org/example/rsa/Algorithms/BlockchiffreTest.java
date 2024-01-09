package org.example.rsa.Algorithms;

import java.math.BigInteger;

import org.example.rsa.PairTypes.PrivateKey;
import org.example.rsa.PairTypes.PublicKey;
import org.example.rsa.PairTypes.RSAKeyPair;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.example.rsa.PairTypes.PairCipherBlockLength;
import org.example.rsa.PairTypes.RSAKeys;

public class BlockchiffreTest {
	@Test
	public void calculateBlockLengthTest() {
		String nString = "91569306435939";
		BigInteger n = BigInteger.valueOf(Long.parseLong(nString));
		int expected = 2;
		int actual = Blockchiffre.calculateBlockLength(n);

		assertEquals(expected, actual);
	}


	@Test
	public void encryptDecryptMessageTest() throws Exception {
		String message = "MATHEMATIK IST SPANNEND!";
		BigInteger n = BigInteger.valueOf(Long.parseLong("7487702261154119839"));
		BigInteger d = BigInteger.valueOf(Long.parseLong("4929816652703129689"));
		BigInteger e = BigInteger.valueOf(Long.parseLong("2418102639206226409"));
		int blockLength = Blockchiffre.calculateBlockLength(n);
		PublicKey publicKey = new PublicKey(e, n);
		PrivateKey privateKey = new PrivateKey(d, n);
		PairCipherBlockLength encrypted = Blockchiffre.encryptMessage(message, publicKey, blockLength);
		String decryptedText = Blockchiffre.decryptMessage(encrypted, privateKey);
		assertEquals(message, decryptedText);
	}
}
