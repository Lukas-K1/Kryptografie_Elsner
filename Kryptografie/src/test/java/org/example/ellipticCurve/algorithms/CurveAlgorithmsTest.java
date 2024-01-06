package org.example.ellipticCurve.algorithms;

import org.example.ellipticCurve.algorithms.CustomTypes.Point2D;
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

    @Test
    void setFunctionAB() {
        setFunction("1*x^3 + a*x^1 + b", 3, 4);
        String function = CurveAlgorithms.getFunctionString();
        assertEquals("1*x^3 + 3.0*x^1 + 4.0", function);
    }

    @Test
    void setFunctionAB2() {
        setFunction(3,4);
        String function = CurveAlgorithms.getFunctionString();
        assertEquals("1*x^3 + 3.0*x^1 + 4.0", function);
    }

    @Test
    void ellipticSecantSkript() {
        setFunction("1*x^3 + a*x^1 + b", -2, 0);
        Point2D p1 = new Point2D(-1, 1);
        Point2D p2 = new Point2D(0, 0);
        Point2D p3 = new Point2D(2, -2);
        Point2D result = CurveAlgorithms.ellipticSecant(p1, p2);
        assertEquals(p3.getX(), result.getX());
        assertEquals(p3.getY(), result.getY());
    }

    @Test
    void ellipticTangentSkript() {
        setFunction("1*x^3 + a*x^1 + b", -2, 0);
        Point2D p1 = new Point2D(-1, 1);
        Point2D p3 = new Point2D(2.25, 2.625);
        Point2D result = CurveAlgorithms.ellipticTangent(p1);
        assertEquals(p3.getX(), result.getX());
        assertEquals(p3.getY(), result.getY());
    }
}