package org.example.ellipticCurve.algorithms.CustomTypes;

public class Point2D {
    private double _x;
    private double _y;

    public Point2D(double x, double y) {
        _x = x;
        _y = y;
    }

    public double getX() {
        return _x;
    }

    public double getY() {
        return _y;
    }

    public String toString() {
        return "(" + _x + ", " + _y + ")";
    }

    public boolean equals(Point2D point) {
        return _x == point._x && _y == point._y;
    }

    public Point2D invert() {
        return new Point2D(_x, -_y);
    }

    public Point2D parseFromString(String point) {
        String[] coordinates = point.replace("(", "").replace(")", "").split(",");
        return new Point2D(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
    }
}
