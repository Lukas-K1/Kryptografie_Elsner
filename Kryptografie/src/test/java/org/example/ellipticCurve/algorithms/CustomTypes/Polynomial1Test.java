package org.example.ellipticCurve.algorithms.CustomTypes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Polynomial1Test {

    @Test
    void getDegree() {
        Polynomial1 p = new Polynomial1("x^2 - 2x - 1");
        assertEquals(2, p.getDegree());
    }

    @Test
    void testToString() {
        Polynomial1 p = new Polynomial1("x^2 - 2x - 1");
        assertEquals("x^2 - 2x - 1", p.toString());
    }

    @Test
    void derivative() throws Exception {
        Polynomial1 p = new Polynomial1("x^2 - 2x - 1");
        Polynomial1 p2 = new Polynomial1("2x - 2");
        Polynomial1 result = p.derivative(p);
        assertEquals(p2.get(0), result.get(0));
        assertEquals(p2.get(1), result.get(1));
    }

    @Test
    void evaluate() throws Exception {
        Polynomial1 p = new Polynomial1("x^2 - 2x - 1");
        assertEquals(-2, p.evaluate(p,1));
    }
}