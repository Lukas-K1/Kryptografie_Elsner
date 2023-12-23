package org.example.ellipticCurve.algorithms.CustomTypes;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {
    private String _polynomialString;

    public Polynomial(String polynomialString) {
        this._polynomialString = polynomialString;
    }

    // Funktion zur Berechnung des Funktionswerts für gegebenes x
    public double evaluate(double x) {
        // Ersetzen von 'x' in der Polynomzeichenkette durch den Wert von x
        String expr = _polynomialString.replaceAll("x", "(" + x + ")");
        return evaluateExpression(expr);
    }

    // Funktion zur Berechnung der Ableitung des Polynoms
    public Polynomial derivative() {
        // Ableitung des Polynoms
        String derivative = derive(_polynomialString);
        return new Polynomial(derivative);
    }

    // Hilfsmethode zur Auswertung eines mathematischen Ausdrucks
    private double evaluateExpression(String expression) {
        return Double.parseDouble(new Object() {}.toString()
                .replaceFirst(".*?([\\d.]+).*", "$1")
                .replaceAll(",", ".").trim());
    }

    // Hilfsmethode zur Ableitung eines Polynoms
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
                    derivative.append("+");
                }

                derivative.append(newCoefficient);
                if (newExponent > 0) {
                    derivative.append("*x^").append(newExponent);
                }
            }
        }

        return derivative.toString();
    }
}
