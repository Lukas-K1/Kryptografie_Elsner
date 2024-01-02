package org.example.maske;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.math.BigInteger;
import java.math.BigDecimal;

public class GraphPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Die Breite und Höhe der Zeichenfläche erhalten
        int width = getWidth();
        int height = getHeight();

        // Achsen zeichnen
        g.drawLine(0, height/2, width, height/2); // X-Achse
        g.drawLine(width/2, 0, width/2, height); // Y-Achse
    }

    public void scale(Graphics g, int x, int y){
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        this.paintComponent(g);
        Dot start = this.startPunkt();

        for(int i = 1; i<11; i++){
            g.drawLine(start.x+(width/20*i), start.y, start.x+(width/20*i), start.y+10);
            g.drawLine(start.x-(width/20*i), start.y, start.x-(width/20*i), start.y+10);
            String scale = String.valueOf(((x/20)*i));
            g.drawString(scale, start.x+(width/20*i)-20, start.y+25);
            g.drawString("-"+scale, start.x-(width/20*i), start.y+25);
        }

        for(int i = 1; i<6; i++){
            g.drawLine(start.x, start.y+(height/10*i), start.x-10, start.y+(height/10*i));
            g.drawLine(start.x, start.y-(height/10*i), start.x-10, start.y-(height/10*i));
            String scale = String.valueOf(((y/10)*i));
            g.drawString(scale, start.x-25, start.y+(height/10*i)-5);
            g.drawString("-"+scale, start.x-25, start.y-(height/10*i)+15);
        }
    }

    public Dot startPunkt(){
        int width = getWidth();
        int height = getHeight();

        return new Dot(width/2, height/2);
    }

    public void drawEllipticCurve(Graphics g, int a, int b, int x, int y){

        int width = getWidth();
        int height = getHeight();
        Dot start = this.startPunkt();
        double test = 1;

        List<DoubleDot> curveDots = this.calculateEllipticCurvePoints(a,b,x,y);

        g.setColor(Color.RED);
        for(int i = 0; i<curveDots.size()-2; i++){
            int xInt = (int) Math.round(curveDots.get(i).x*20);
            int xIntNext = (int) Math.round(curveDots.get(i+2).x*20);
            int yInt = (int) Math.round(curveDots.get(i).y*20);
            int yIntNext = (int) Math.round(curveDots.get(i+2).y*20);
            g.drawLine(start.x+xInt*1, start.y+yInt*1, start.x+xIntNext*1, start.y+yIntNext*1);
        }
    }

    private List<DoubleDot> calculateEllipticCurvePoints(int a, int b, int x, int y){
        List<DoubleDot> result = new ArrayList<>();

        double width = getWidth();
        double height = getHeight();
        BigDecimal aValue = new BigDecimal(""+a);
        BigDecimal bValue = new BigDecimal(""+b);
        BigDecimal xDoubleValue = new BigDecimal(""+x);
        double negateX = -x;
        BigDecimal yDoubleValue = new BigDecimal(""+y);

        System.out.println("Berechne");

        for(double i = negateX; i<xDoubleValue.doubleValue(); i += 0.0001){

            BigDecimal xValue = BigDecimal.valueOf(i);
            BigDecimal ySquared = xValue.pow(3).add(aValue.multiply(xValue).add(bValue));

            if(ySquared.compareTo(BigDecimal.ZERO) > 0){
                BigDecimal yValue = BigDecimal.valueOf(Math.sqrt(ySquared.doubleValue()));
                result.add(new DoubleDot(xValue.doubleValue(), yValue.doubleValue()));
                result.add(new DoubleDot(xValue.doubleValue(), -yValue.doubleValue()));
            }
        }

        return result;
    }
}