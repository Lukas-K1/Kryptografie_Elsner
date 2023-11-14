package org.example.rsa.Algorithms;

import java.math.BigInteger;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ExtendedEuclideanTest {
	@Test
	public void extendedGCD() {
		BigInteger a = null;
		BigInteger b = null;
		ExtendedEuclidean.ExtendedEuclidResult expected = new ExtendedEuclidean.ExtendedEuclidResult(null, null, null);
		ExtendedEuclidean.ExtendedEuclidResult actual = ExtendedEuclidean.extendedGCD(a, b);

		assertEquals(expected, actual);
	}

	@Test
	public void gcd() {
		BigInteger a = null;
		BigInteger b = null;
		BigInteger expected = null;
		BigInteger actual = ExtendedEuclidean.gcd(a, b);

		assertEquals(expected, actual);
	}

	@Test
	public void getModInverse() {
		BigInteger e = null;
		BigInteger phi = null;
		BigInteger expected = null;
		BigInteger actual = ExtendedEuclidean.getModInverse(e, phi);

		assertEquals(expected, actual);
	}
}
