package org.example.ellipticCurve.algorithms;

import org.example.ellipticCurve.algorithms.CustomTypes.Point2D;
import org.example.ellipticCurve.algorithms.CustomTypes.Polynomial;

public class CurveAlgorithms {
    private static double _x;
    private static Polynomial _function;
    private static String _functionString;

    public static Polynomial getFunction() {
        return _function;
    }
    public static String getFunctionString() {
        return _functionString;
    }
    static String functionBlueprint = "1*x^3 + a*x^1 + b";


    public static void setFunction(String function) {
        _function = new Polynomial(function);
    }


    public static void setFunction(double a, double b) {
        String func = functionBlueprint;
        func = func.replace("a", String.valueOf(a));
        func = func.replace("b", String.valueOf(b));
        double validCoefficients = 4*Math.pow(a, 3) + 27*Math.pow(b, 2);
        if(validCoefficients == 0){
            throw new IllegalArgumentException("Invalid coefficients");
        }
        _functionString = func;
        System.out.println(func);
        _function = new Polynomial(func);
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

    public static Point2D ellipticTangent(Point2D point1) {
        if (point1.getY() == 0) {
            return point1;
        }
        double m = 0;
        Polynomial derivative = _function.derivative();
        m = (derivative.evaluate(point1.getX()))/(2 * point1.getY());
        double x3 = Math.pow(m, 2) - 2 * point1.getX();
        double y3 = m * (x3 - point1.getX()) + point1.getY();
        return new Point2D(x3, y3);
    }

    public static Point2D ellipticSecant(Point2D point1, Point2D point2) {
        double m = 0;
        if(point1.equals(point2)){
            throw new IllegalArgumentException("Points are equal");
        }
        if (point1.getX() == point2.getX()) {
            return point1.invert();
        }
        m = (point2.getY() - point1.getY()) / (point2.getX() - point1.getX());
        double n = point1.getY() - m * point1.getX();

        double x3 = Math.pow(m, 2) - point1.getX() - point2.getX();
        double y3 = m * (x3 - point1.getX()) + point1.getY();

        return new Point2D(x3, y3);
    }
}
