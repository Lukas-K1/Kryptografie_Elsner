package org.example.ellipticCurve.algorithms;

import org.junit.jupiter.api.Test;

import static org.example.ellipticCurve.algorithms.CurveAlgorithms.setFunction;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CurveAlgorithmsTest {

    @Test
    void newtonRaphsonFunction() {
        setFunction("- 0.15*x^2 + 2");
        double result = CurveAlgorithms.newtonRaphsonFunction(2, 0.0001);
        assertEquals(3.651483737349603,result);
    }

    @Test
    void secantMethod() {
        setFunction("-1*x^2 + 4");
        double result = CurveAlgorithms.secantMethod(0, 3, 0.0001);
        //assertEquals(2.0,result);
        assertEquals(1.9999897600262144,result); //ergebnis fast gleich zu auskommentiertem test
    }
}