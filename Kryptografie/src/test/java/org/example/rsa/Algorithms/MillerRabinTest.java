package org.example.rsa.Algorithms;

import java.math.BigInteger;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class MillerRabinTest {
	@Test
	public void isPrime() throws Exception {
		BigInteger probablyPrime = null;
		int k = 123;
		BigInteger n = null;
		BigInteger m = null;
		Boolean expected = true;
		Boolean actual = MillerRabin.isPrime(probablyPrime, k, n, m);

		assertEquals(expected, actual);
	}
}
