package org.example.ellipticCurve.algorithms.CustomTypes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PolynomialTest {

    @Test
    void evaluate4() {
        Polynomial polynomial = new Polynomial("2*x^3 - 5*x^2 + 3*x^1 - 7");
        assertEquals(-7, polynomial.evaluate(1));
    }

    @Test
    void getDerivativeString() {
        Polynomial polynomial = new Polynomial("2*x^3 - 5*x^2 + 3*x^1 - 7");
        assertEquals("6.0*x^2 - 10.0*x^1 + 3.0", polynomial.getDerivative());
    }
}