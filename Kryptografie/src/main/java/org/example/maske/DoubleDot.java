package org.example.maske;

public class DoubleDot {
    double x;
    double y;

    public DoubleDot(double x, double y){
        this.x = x;
        this.y = y;
    }

    public boolean aufKurve(double a, double b){
        double y = Math.pow(this.y, 2);
        double x = Math.pow(this.x, 3)+a*this.x+b;

        y = (double) Math.round(y*100) / 100;
        x = (double) Math.round(x*100) / 100;
        return y == x || -y == x;
    }
}
