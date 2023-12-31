package org.example.ellipticCurve.algorithms;

import java.math.BigInteger;

// TODO proper english name
public class EllipticCurvePoint{
    private BigInteger _x;
    private BigInteger _y;
    private BigInteger _a;
    private BigInteger _b;
    private BigInteger _p;

    public EllipticCurvePoint(BigInteger x, BigInteger y, BigInteger a, BigInteger b, BigInteger p) {
        _x = x;
        _y = y;
        _a = a;
        _b = b;
        _p = p;
    }
    public EllipticCurvePoint add(EllipticCurvePoint newPoint) {
        if (!_a.equals(newPoint._a) || !_b.equals(newPoint._b) || !_p.equals(newPoint._p)) {
            throw new IllegalArgumentException("Points are not on the same curve");
        }
        if (_x.equals(newPoint._x) && _y.equals(newPoint._y)) {
            return this.doublePoint();
        }
        BigInteger slope;
        if (_x.equals(newPoint._x)) {
            slope = _x.pow(2).multiply(BigInteger.valueOf(3)).add(_a)
                    .multiply(BigInteger.valueOf(2)
                    .multiply(_y)
                    .modInverse(_p));
        } else {
            slope = _y.subtract(newPoint._y)
                    .multiply(_x.subtract(newPoint._x)
                    .modInverse(_p));
        }

        BigInteger newX = slope.pow(2)
                    .subtract(_x)
                    .subtract(newPoint._x)
                    .mod(_p);
        BigInteger newY = slope.multiply(_x.subtract(newX))
                    .subtract(_y).mod(_p);
        return new EllipticCurvePoint(newX, newY, _a, _b, _p);
    }

    public EllipticCurvePoint doublePoint() {
        BigInteger slope = _x.pow(2)
                    .multiply(BigInteger.valueOf(3)).add(_a)
                    .multiply(BigInteger.valueOf(2)
                    .multiply(_y).modInverse(_p));
        BigInteger newX = slope.pow(2)
                    .subtract(_x)
                    .subtract(_x).mod(_p);
        BigInteger newY = slope.multiply(_x.subtract(newX))
                    .subtract(_y).mod(_p);
        return new EllipticCurvePoint(newX, newY, _a, _b, _p);
    }

    public EllipticCurvePoint subtract(EllipticCurvePoint newPoint) {
        if (newPoint == null) {
            return null;
        }
        return add(newPoint.negate());
    }

    public EllipticCurvePoint multiply(BigInteger n) {
        if (n.equals(BigInteger.ZERO)) {
            return null;
        }
        if (n.equals(BigInteger.ONE)) {
            return this;
        }
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            return doublePoint().multiply(n.divide(BigInteger.TWO));
        }
        return add(doublePoint().multiply(n.divide(BigInteger.TWO)));
    }

    /**
     * calculates right hand side of equation
     * @return
     */
    public BigInteger calculateRightHandSide(BigInteger x) {
        return x.multiply(x).mod(_p).add(_a.multiply(x)).add(_b).mod(_p);
    }

    private EllipticCurvePoint negate() {
        return new EllipticCurvePoint(_x, _y.negate(), _a, _b, _p);
    }
}
