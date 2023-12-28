package org.example.ellipticCurve.algorithms;

import org.example.ellipticCurve.algorithms.CustomTypes.Polynomial;

public class CurveAlgorithms {
    private static double _x;
    private static Polynomial _function;

    public static Polynomial getFunction() {
        return _function;
    }

    public static void setFunction(String function) {
        _function = new Polynomial(function);
    }

    //#region Tangentenverfahren
    /**
     * performs the newton raphson algorithm
     * @param x0 = start value
     * @param epsilon = precision
     * @return approximated root
     */
    public static Double newtonRaphsonFunction(double x0, double epsilon) {
        _x = x0;
        do {
            double fx = function(_function, _x); // f(x)
            double xDerivative = derivative(_function, _x); // f'(x)
            _x = _x - (fx / xDerivative);
        }
        while (Math.abs(function(_function, _x)) > epsilon);

        return _x;
    }
    //#endregion


    //#region Sekantenverfahren
    /**
     * performs the secant method
     * @param x0 = x value point1
     * @param x1 = x value point2
     * @param epsilon = precision
     * @return approximated root
     */
    public static double secantMethod(double x0, double x1, double epsilon) {
        double x2;
        do {
            double fx0 = function(_function, x0);
            double fx1 = function(_function, x1);
            x2 = x1 - (fx1 * (x1 - x0)) / (fx1 - fx0);
            x0 = x1;
            x1 = x2;
        }
        while (Math.abs(function(_function, x2)) > epsilon);

        return x2;
    }
    //#endregion

    private static double function(Polynomial function, double x) {
        return function.evaluate(x);
    }

    private static double derivative(Polynomial function, double x) {
        Polynomial derivative = function.derivative();
        return derivative.evaluate(x);
    }
}
