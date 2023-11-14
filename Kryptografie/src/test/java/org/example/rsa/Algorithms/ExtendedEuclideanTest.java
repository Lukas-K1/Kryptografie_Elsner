package org.example.rsa.Algorithms;

import java.math.BigInteger;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ExtendedEuclideanTest {
	@Test
	public void extendedGCD() {
		BigInteger a = BigInteger.valueOf(5);
		BigInteger b = BigInteger.valueOf(48);
		ExtendedEuclidean.ExtendedEuclidResult expected = new ExtendedEuclidean.ExtendedEuclidResult(BigInteger.valueOf(1), BigInteger.valueOf(-19), BigInteger.valueOf(2));
		ExtendedEuclidean.ExtendedEuclidResult actual = ExtendedEuclidean.extendedGCD(a, b);
		assertEquals(expected.gcd, actual.gcd);
		assertEquals(expected.x, actual.x);
		assertEquals(expected.y, actual.y);
	}

	@Test
	public void gcd() {
		BigInteger a = BigInteger.valueOf(5);
		BigInteger b = BigInteger.valueOf(48);
		BigInteger expected = BigInteger.ONE;
		BigInteger actual = ExtendedEuclidean.gcd(a, b);

		assertEquals(expected, actual);
	}

	@Test
	public void getModInverse() {
		BigInteger e = BigInteger.valueOf(5);
		BigInteger phi = BigInteger.valueOf(48);
		BigInteger expected = BigInteger.valueOf(29);
		BigInteger actual = ExtendedEuclidean.getModInverse(e, phi);

		assertEquals(expected, actual);
	}
}
