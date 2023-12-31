package org.example.rsa.Algorithms;

import java.math.BigInteger;

import org.junit.Test;
import org.junit.jupiter.api.*;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class FastExponentiationTest {
	@Test
	public void exponentiation() {
		BigInteger baseValue = BigInteger.valueOf(4347);
		BigInteger exponent = BigInteger.valueOf(5618);
		BigInteger modulo = BigInteger.valueOf(8467);
		BigInteger expected = BigInteger.valueOf(412);
		BigInteger actual = FastExponentiation.exponentiation(baseValue, exponent, modulo);

		assertEquals(expected, actual);
	}
}
