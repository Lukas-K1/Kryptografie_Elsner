package org.example.rsa.Algorithms;

import java.math.BigInteger;

import org.example.rsa.Handler.RSAHandler;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ExtendedEuclideanTest {
	@Test
	public void extendedGCD() {
		BigInteger a = BigInteger.valueOf(315);
		BigInteger b = BigInteger.valueOf(661643);
		ExtendedEuclidean.ExtendedEuclidResult expected = new ExtendedEuclidean.ExtendedEuclidResult(BigInteger.valueOf(1), BigInteger.valueOf(-319269), BigInteger.valueOf(152));
		ExtendedEuclidean.ExtendedEuclidResult actual = ExtendedEuclidean.extendedGCD(a, b);
		assertEquals(expected.gcd, actual.gcd);
		assertEquals(expected.x, actual.x);
		assertEquals(expected.y, actual.y);
	}

	@Test
	public void gcd() {
		BigInteger a = BigInteger.valueOf(315);
		BigInteger b = BigInteger.valueOf(661643);
		BigInteger expected = BigInteger.ONE;
		BigInteger actual = ExtendedEuclidean.gcd(a, b);

		assertEquals(expected, actual);
	}

	@Test
	public void getModInverse() throws Exception {
		BigInteger e = BigInteger.valueOf(315);
		BigInteger phi = BigInteger.valueOf(661643);
		BigInteger expected = BigInteger.valueOf(342374);
		BigInteger actual = ExtendedEuclidean.getModInverse(e, phi);

		assertEquals(expected, actual);
	}
}
