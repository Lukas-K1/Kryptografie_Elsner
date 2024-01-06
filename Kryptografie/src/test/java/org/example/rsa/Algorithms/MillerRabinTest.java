package org.example.rsa.Algorithms;

import java.math.BigInteger;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class MillerRabinTest {
	@Test
	public void isPrime() throws Exception {
		BigInteger probablyPrime = BigInteger.valueOf(8501);
		int k = 315;
		BigInteger n = BigInteger.ONE;
		BigInteger m = BigInteger.valueOf(15);
		Boolean expected = false;
		Boolean actual = MillerRabin.isPrime(probablyPrime, k, n, m);

		assertEquals(expected, actual);
	}

	@Test
	public void isPrimeTrue() throws Exception {
		BigInteger probablyPrime = BigInteger.valueOf(8549);
		int k = 315;
		BigInteger n = BigInteger.ONE;
		BigInteger m = BigInteger.valueOf(15);
		Boolean expected = true;
		Boolean actual = MillerRabin.isPrime(probablyPrime, k, n, m);

		assertEquals(expected, actual);
	}
}
