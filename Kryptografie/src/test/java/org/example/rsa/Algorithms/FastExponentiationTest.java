package org.example.rsa.Algorithms;

import java.math.BigInteger;

import org.junit.Test;
import org.junit.jupiter.api.*;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class FastExponentiationTest {
	@Test
	public void exponentiation() {
		BigInteger baseValue = null;
		BigInteger exponent = null;
		BigInteger modulo = null;
		BigInteger expected = null;
		BigInteger actual = FastExponentiation.exponentiation(baseValue, exponent, modulo);

		assertEquals(expected, actual);
	}
}
