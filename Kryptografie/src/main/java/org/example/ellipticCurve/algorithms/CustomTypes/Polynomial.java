package org.example.ellipticCurve.algorithms.CustomTypes;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {
    private String _polynomialString;
    private String resultPolynomial;
    private ArrayList<Double> _coefficients = new ArrayList<>();
    private ArrayList<Integer> _exponents = new ArrayList<>();

    public Polynomial(String polynomialString) {
        _polynomialString = polynomialString;
        _polynomialString = _polynomialString.replaceAll("- ", "-");
        _polynomialString = _polynomialString.replaceAll("\\+ ", "+");
        if (_polynomialString.contains("+-")) {
            _polynomialString = _polynomialString.replace("+-", "-");
        }
        String[] terms = _polynomialString.split(" ");
        for (String term : terms) {
            String[] parts = term.split("\\*x\\^?");
            double coefficient = Double.parseDouble(parts[0]);
            int exponent = parts.length > 1 ? Integer.parseInt(parts[1].trim()) : 0;
            _coefficients.add(coefficient);
            _exponents.add(exponent);
        }
    }

    // Funktion zur Berechnung des Funktionswerts für gegebenes x
    public double evaluate(double x) {

        double result = 0;
        for (int i = 0; i < _coefficients.size(); i++) {
            result += _coefficients.get(i) * Math.pow(x, _exponents.get(i));
        }
        return result;
    }

    // Funktion zur Berechnung der Ableitung des Polynoms
    public Polynomial derivative() {
        // Ableitung des Polynoms
//        String derivative = derive(_polynomialString);
        String derivative = derivativeFunction();
        return new Polynomial(derivative);
    }

    // Hilfsmethode zur Auswertung eines mathematischen Ausdrucks
    @Deprecated
    private double evaluateExpression(String expression) {
        return Double.parseDouble(new Object() {}.toString()
                .replaceFirst(".*?([\\d.]+).*", "$1")
                .replaceAll(",", ".").trim());
    }

    // Hilfsmethode zur Ableitung eines Polynoms
    @Deprecated
    private String derive(String polynomial) {
        ArrayList<String> terms = new ArrayList<>();
        Pattern pattern = Pattern.compile("[-+]?\\b\\d+\\.?\\d*\\*?x?\\^?\\d*\\b");
        Matcher matcher = pattern.matcher(polynomial);

        while (matcher.find()) {
            terms.add(matcher.group());
        }

        StringBuilder derivative = new StringBuilder();

        for (String term : terms) {
            String[] parts = term.split("\\*x\\^?");
            double coefficient = Double.parseDouble(parts[0]);
            int exponent = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;

            if (exponent > 0) {
                double newCoefficient = coefficient * exponent;
                int newExponent = exponent - 1;

                if (!derivative.isEmpty() && newCoefficient >= 0) {
                    derivative.append(" + ");
                }
                else if (newCoefficient < 0) {
                    derivative.append(" - ");
                }

                derivative.append(newCoefficient);
                if (newExponent > 0) {
                    derivative.append("*x^").append(newExponent);

                }
            }
        }

        return derivative.toString();
    }

    private String derivativeFunction() {
        String derivative = "";
        for (int i = 0; i < _coefficients.size(); i++) {
            double coefficient = _coefficients.get(i);
            int exponent = _exponents.get(i);
            if (exponent > 0) {
                double newCoefficient = coefficient * exponent;
                int newExponent = exponent - 1;

                if (!derivative.isEmpty()) {
                    derivative += (newCoefficient > 0) ? " + " : " - ";
                    derivative += Math.abs(newCoefficient);
                }
                else {
                    derivative += newCoefficient;
                }

                if (newExponent > 0) {
                    derivative += "*x^" + newExponent;

                }
            }
        }
        return derivative;
    }

    public String getPolynomial() {
        return _polynomialString;
    }

    public String getDerivative(){
        return derivativeFunction();
    }
}
