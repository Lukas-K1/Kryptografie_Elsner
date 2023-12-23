package org.example.ellipticCurve.algorithms.Mask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class STMask extends JPanel implements ActionListener {
    private JTextField functionField, secantStartField, secantEndField, tangentField;
    private JButton drawButton;
    private String functionString;
    private double secantStart, secantEnd, tangentPoint;

    public STMask() {
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Funktion (z.B. x^2):"));
        functionField = new JTextField(20);
        add(functionField);

        add(new JLabel("Sehnen (Startwert, Endwert):"));
        secantStartField = new JTextField(10);
        add(secantStartField);
        secantEndField = new JTextField(10);
        add(secantEndField);

        add(new JLabel("Tangente (Punkt):"));
        tangentField = new JTextField(10);
        add(tangentField);

        drawButton = new JButton("Zeichnen");
        drawButton.addActionListener(this);
        add(drawButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the curve based on the function
        g.setColor(Color.BLUE);
        for (int x = 0; x < getWidth(); x++) {
            double y = evaluateFunction(x);
            int yPixel = getHeight() - (int) y;
            g.drawLine(x, yPixel, x, yPixel);
        }

        // Draw secant line
        g.setColor(Color.RED);
        int secantStartX = (int) secantStart;
        int secantEndX = (int) secantEnd;
        double secantStartY = evaluateFunction(secantStart);
        double secantEndY = evaluateFunction(secantEnd);
        g.drawLine(secantStartX, getHeight() - (int) secantStartY, secantEndX, getHeight() - (int) secantEndY);

        // Draw tangent line
        g.setColor(Color.GREEN);
        int tangentX = (int) tangentPoint;
        double tangentY = evaluateFunction(tangentPoint);
        int tangentYPixel = getHeight() - (int) tangentY;
        g.drawLine(tangentX, tangentYPixel - 100, tangentX, tangentYPixel + 100);
    }

    private double evaluateFunction(double x) {
        // Evaluate the function using the provided string
        String expression = functionField.getText();
        expression = expression.replaceAll("x", "(" + x + ")");
        return Double.parseDouble(new Object() {
        }.toString()
                .replaceFirst(".*?([\\d.]+).*", "$1")
                .replaceAll(",", ".").trim());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            functionString = functionField.getText();
            secantStart = Double.parseDouble(secantStartField.getText());
            secantEnd = Double.parseDouble(secantEndField.getText());
            tangentPoint = Double.parseDouble(tangentField.getText());
            repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Falsche Eingabe!", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }
}
