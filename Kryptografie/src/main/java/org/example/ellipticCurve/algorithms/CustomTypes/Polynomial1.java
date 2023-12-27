package org.example.ellipticCurve.algorithms.CustomTypes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial1 extends ArrayList<Integer> {
    private Polynomial1 _polynomial;

    /**
     no-arg constructor returns a polynomial of degree 1, with value 0
     */

    public Polynomial1() {
        // invoke superclass constructor, i.e. the
        // constructor of ArrayList<Integer> with
        super(1); // we want capacity at least 1
        // parameter 1 (capacity, not size)
        this.add(0,0); // uses autoboxing (index, value)
        _polynomial = this;
    }

    /**
     Construct polynomial from string representation
     that matches the output format of the Polynomial toString method.

     That is, you should be able to do:

     <code>Polynomial p = new Polynomial("0");<br />
     Polynomial p = new Polynomial("1");<br />
     Polynomial p = new Polynomial("-4");<br />
     Polynomial p = new Polynomial("2x - 3");<br />
     Polynomial p = new Polynomial("x^2 - 5x + 6");<br />
     Polynomial p = new Polynomial("x^2 - x - 1");<br />
     Polynomial p = new Polynomial("x^2 - x");<br />
     Polynomial p = new Polynomial("-x^7 - 2x^5 + 3x^3 - 4x");<br /></code>

     And for any Polymomial object p, the following test should pass:<br />
     <code>assertEquals(new Polynomial(p.toString()), p);</code><br />

     @param s string representation of Polynomial
     */

    public Polynomial1(String s) {
        super(1);
        // For information on regular expressions in Java,
        // see http://docs.oracle.com/javase/tutorial/essential/regex

        // First check for special case of only digits,
        // with possibly a - in front
        // i.e. a degree 0 polynomial that is just an integer constant

        Pattern integerConstantPattern =
                Pattern.compile("^-?\\d+$");
        Matcher integerConstantMatcher = integerConstantPattern.matcher(s);

        // if that pattern matches, then the whole string is just
        // an integer constant.  So we can safely call Integer.parseInt(s)
        // to convert to int, and add in this parameter.

        if (integerConstantMatcher.matches()) {
            this.add(0,Integer.parseInt(s));
            return; // we are done!
        }

        // now, try for polynomials of degree 1

        Pattern degreeOnePattern =
                Pattern.compile("^(-?)(\\d*)x( ([+-]) (\\d+))?$");
        // Explanation:
        // start/end         ^                            $
        // sign for x term    (-?)                            group(1)
        // coeff for x term       (\\d*)                      group(2)
        // x in x term                  x
        // optional constant part        (               )?   group(3)
        // sign for constant                ([+-])            group(4)
        // coeff for constant                      (\\d+)     group(5)

        Matcher degreeOneMatcher = degreeOnePattern.matcher(s);

        if (degreeOneMatcher.matches()) {

            int xCoeff = 1;
            int constantTerm = 0;

            String xCoeffSign = degreeOneMatcher.group(1);
            String xCoeffString = degreeOneMatcher.group(2);
            String constantTermSign = degreeOneMatcher.group(4);
            String constantTermString = degreeOneMatcher.group(5);

            if (xCoeffString != null && !xCoeffString.equals("")) {
                xCoeff = Integer.parseInt(xCoeffString);
            }

            if (xCoeffSign != null && xCoeffSign.equals("-")) {
                xCoeff *= -1;
            }

            if (constantTermString != null && !constantTermString.equals("")) {
                constantTerm = Integer.parseInt(constantTermString);
            }

            if (constantTermSign != null && constantTermSign.equals("-")) {
                constantTerm *= -1;
            }

            this.add(0,constantTerm);
            this.add(1,xCoeff);
            return;
        }

        // then try for higher degree

        String twoOrMoreRe =
                "^" // start of the string
                        + "([-]?)(\\d*)x\\^(\\d+)" // first x^d term, groups 1,2,3
                        + "(( [+-] \\d*x\\^\\d+)*)" // zero or more x^k terms group 4 (and 5)
                        + "( [+-] \\d*x)?" // optional x term (group 6)
                        + "( [+-] \\d+)?" // optional constant term (group 7)
                        + "$"; // the end of the string

        Pattern degreeTwoOrMorePattern  = Pattern.compile(twoOrMoreRe);
        Matcher degreeTwoOrMoreMatcher = degreeTwoOrMorePattern.matcher(s);

        // if we have a match...
        if (degreeTwoOrMoreMatcher.matches()) {

            int firstCoeff = 1;
            String startSign      = degreeTwoOrMoreMatcher.group(1);
            String coeffString    = degreeTwoOrMoreMatcher.group(2);
            String degreeString   = degreeTwoOrMoreMatcher.group(3);
            String middleXtoTheTerms = degreeTwoOrMoreMatcher.group(4);
            String optionalXTermPart = degreeTwoOrMoreMatcher.group(6);
            String optionalConstantTermPart = degreeTwoOrMoreMatcher.group(7);

            if (coeffString != null && !coeffString.equals("")) {
                firstCoeff = Integer.parseInt(coeffString);
            }

            if (startSign != null && startSign.equals("-")) {
                firstCoeff *= -1;
            }

            int degree = Integer.parseInt(degreeString);

            this.ensureCapacity(degree+1); // method of ArrayList<Integer>
            for(int i=0; i<=degree; i++) // initialize all to zero
                this.add(0,0);

            this.set(degree,firstCoeff);

            if (middleXtoTheTerms!=null && !middleXtoTheTerms.equals("")) {

                Pattern addlXtoThePowerTermPattern  =
                        Pattern.compile(" ([+-]) (\\d+)(x\\^)(\\d+)");
                Matcher addlXtoThePowerTermMatcher
                        = addlXtoThePowerTermPattern.matcher(middleXtoTheTerms);

                while (addlXtoThePowerTermMatcher.find()) {

                    int coeff = 1;
                    String sign           = addlXtoThePowerTermMatcher.group(1);
                    String nextCoeffString    = addlXtoThePowerTermMatcher.group(2);
                    String nextDegreeString   = addlXtoThePowerTermMatcher.group(4);

                    if (nextCoeffString != null && !nextCoeffString.equals("")) {
                        coeff = Integer.parseInt(nextCoeffString);
                    }

                    if (sign != null && sign.equals("-")) {
                        coeff *= -1;
                    }

                    this.set(Integer.parseInt(nextDegreeString),coeff);

                }
            } // if middleXToTheTerms has something

            // Now all that is left is, possibly, an x term and a constant
            // term.    We need to select them out if they are there.

            if (optionalXTermPart != null && !optionalXTermPart.equals("")) {

                Pattern optXTermPattern =
                        Pattern.compile("^ ([+-]) (\\d*)x$");
                Matcher optXTermMatcher = optXTermPattern.matcher(optionalXTermPart);
                optXTermMatcher.find();

                int xCoeff = 1;
                int constantTerm = 0;
                String xCoeffSign = optXTermMatcher.group(1);
                String xCoeffString = optXTermMatcher.group(2);

                if (xCoeffString != null && !xCoeffString.equals("")) {
                    xCoeff = Integer.parseInt(xCoeffString);
                }

                if (xCoeffSign != null && xCoeffSign.equals("-")) {
                    xCoeff *= -1;
                }

                this.set(1,xCoeff);
            } // optionalXTerm part

            if (optionalConstantTermPart != null
                    && !optionalConstantTermPart.equals("")) {

                Pattern constantTermPattern =
                        Pattern.compile("^ ([+-]) (\\d+)$");
                Matcher constantTermMatcher
                        = constantTermPattern.matcher(optionalConstantTermPart);

                constantTermMatcher.find();

                int constant = 0;
                String sign = constantTermMatcher.group(1);
                String constantString = constantTermMatcher.group(2);

                if (constantString != null && !constantString.equals("")) {
                    constant = Integer.parseInt(constantString);
                }

                if (sign!=null && sign.equals("-")) {
                    constant *= -1;
                }

                this.set(0,constant);
            } // a constant term is present

            _polynomial = this;

            return;
        } // degreeTwoOrMore

        // in the end, if we don't find what we need,
        // through an exception

        throw new IllegalArgumentException("Bad Polynomial String: [" + s + "]");

    }

    /**
     get the degree of the polynomial.  Always >= 1
     @return degree of the polynomial
     */
    public int getDegree() { return this.size() - 1; }


    /**
     Return string respresentation of Polynomial.

     Leading coefficient has negative sign in front, with no space.
     Other signs have a space on either side.    Coefficients that are ones
     should be omitted (except in the x^0 term).
     Terms with zero coefficients, except in the
     special case where the polynomial is of degree zero, and the constant
     term is in fact zero--in that case, "0" should be returned.


     Examples:<br />
     0<br />
     1<br />
     -4<br />
     2x - 3<br />
     x^2 - 5x + 6<br />
     x^2 - x - 1<br />
     x^2 - x <br />
     -x^7 - 2x^5 + 3x^3 - 4x<br />

     See the source code of PolynomialTest.java for more examples.

     @return string representation of Polynomial
     */

    public String toString() {
        String result = "";

        DecimalFormat df = new DecimalFormat("#");

        // check for special case of degree 0

        if (this.getDegree() == 0) {
            return df.format(this.get(0));
        }

        // Make the first term


        int firstTerm = (this.get(this.getDegree()));

        // if first term is negative put the minus sign on

        if (firstTerm < 0) {
            result += "-";
        }

        // put on the first coeff, supressing 1's

        if (Math.abs(firstTerm) != 1) {
            result += df.format(Math.abs(firstTerm));
        }

        result +="x";

        if (this.getDegree() > 1) {
            result += "^" + this.getDegree();
        }

        // append rest of terms

        for (int i=this.getDegree() - 1; i>=0; i--) {

            // if this coeff is zero, supress it

            if (this.get(i)==0) {
                continue;
            }

            // put on the sign

            result += (this.get(i) < 0) ? " - " : " + ";

            // put on the first coeff, supressing 1's

            if (Math.abs(this.get(i))!=1) {
                result += df.format(Math.abs(this.get(i)));
            }

            if (i>=2) {
                result += "x" + "^" + i;
            } else if (i==1) {
                result += "x";
            }; // else i==0 and we do nothing. :-)

        }

        return result;

    }

    public Polynomial1 derivative(final Polynomial1 t)
            throws Exception {
        int n = _polynomial.size();
        if (n == 0) {
            throw new Exception("no coefficients in polynomial");
        }
        Polynomial1 result =
                new Polynomial1();
        for (int j = n - 1; j > 0; j--) {
            for (int var : t) {
                result.add(var * j);
            }
        }
        return result;
    }

    /**
     * Compute the value of the function for the given argument.
     * <p>
     *  The value returned is </p><p>
     *  {@code coefficients[n] * x^n + ... + coefficients[1] * x  + coefficients[0]}
     * </p>
     *
     * @param x Argument for which the function value should be computed.
     * @return the value of the polynomial at the given point.
     */
    public double value(double x) throws Exception {
        return evaluate(_polynomial, x);
    }

    protected static double evaluate(Polynomial1 polynomial, double argument)
            throws Exception {
        int n = polynomial.size();
        if (n == 0) {
            throw new Exception("no coefficients in polynomial");
        }
        double result = polynomial.get(n-1);//coefficients[n - 1];
        for (int j = n - 2; j >= 0; j--) {
            result = argument * result + polynomial.get(j);
        }
        return result;
    }

}
