package org.example.maske;

import org.example.ellipticCurve.algorithms.CurveAlgorithms;
import org.example.ellipticCurve.algorithms.CustomTypes.Point2D;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ZusatzMaske {
    

    public void test(){
        JFrame frame = new JFrame("Integrationsprojekt");

        // Create a panel with a BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create a panel for the first row (1/3 of the window)
        JPanel row1 = new JPanel(new GridLayout(1, 3)); // 1 row, 3 columns in the first row
        addBorder(row1, "Parameter");

        // Create components for the first row
        JTextField aParameter = new JTextField(1);
        JTextField bParameter = new JTextField(1);
        JTextField scaleParameter = new JTextField(1);

        JButton button = new JButton("Start");

        JButton buttonZusatz = new JButton("Basisaufgabe");

        JButton buttomSecantOperation = new JButton("Sekantenverfahren");
        JButton buttonTangentOperation = new JButton("Tangentenverfahren");
        JTextField secantPoint1 = new JTextField(1);
        JTextField secantPoint2 = new JTextField(1);

        // Add components to the first row
        JPanel column1 = new JPanel();
        column1.setLayout(new BoxLayout(column1, BoxLayout.Y_AXIS));
        JLabel ellipticCurveDefinition = new JLabel("y^2 = x^3 + ax + b");
        column1.add(ellipticCurveDefinition);
        column1.add(createLabeledPanel("a", aParameter));
        column1.add(createLabeledPanel("b", bParameter));
        column1.add(createLabeledPanel("Skalierungs Parameter", scaleParameter));
        aParameter.setText("1");
        bParameter.setText("0");
        scaleParameter.setText("1");
        column1.add(button);
        
        JPanel column4 = new JPanel(new GridLayout(2, 1));
        column4.setLayout(new BoxLayout(column4, BoxLayout.Y_AXIS));
        //column4.add(createLabeledPanel("Länge des Klartexts", lengthKlartext));
        column4.add(buttonZusatz);

        row1.add(column1);
        row1.add(new JPanel());
        row1.add(new JPanel());
        row1.add(column4);

        // Create a panel for the second row (2/3 of the window)
        JPanel row2 = new JPanel(new GridLayout(1, 2)); // 2 columns in the second row
        addBorder(row2, "Elliptic Curve");

        GraphPanel graph = new GraphPanel();
        row2.add(graph);

        // Add rows to the main panel
        mainPanel.add(row1, BorderLayout.NORTH);
        mainPanel.add(row2, BorderLayout.CENTER);

        // Add the main panel to the frame
        frame.add(mainPanel);


        // Set frame properties
        frame.setSize(1200, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        row1.setPreferredSize(new Dimension(0, frame.getHeight() / 3));
        frame.setVisible(true);

        buttonZusatz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    Maske maske = new Maske();
                    maske.launch();
            }
            });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    graph.scale(graph.getGraphics(), Double.valueOf(scaleParameter.getText()));
                    graph.drawEllipticCurve(graph.getGraphics(), Double.valueOf(aParameter.getText()), Double.valueOf(bParameter.getText()), Double.valueOf(scaleParameter.getText()));
                    // graph.drawDot(graph.getGraphics(), new DoubleDot(-3, -3), Double.valueOf(scaleParameter.getText()));
                    // graph.drawDot(graph.getGraphics(), new DoubleDot(-3, 3), Double.valueOf(scaleParameter.getText()));
                    // graph.drawDot(graph.getGraphics(), new DoubleDot(3, -3), Double.valueOf(scaleParameter.getText()));
                    // graph.drawDot(graph.getGraphics(), new DoubleDot(3, 3), Double.valueOf(scaleParameter.getText()));
                    graph.drawLine(graph.getGraphics(), new DoubleDot(3, 3), new DoubleDot(3, -3), Double.valueOf(scaleParameter.getText()));
                    double aValue = Double.valueOf(aParameter.getText());
                    double bValue = Double.valueOf(bParameter.getText());
                    CurveAlgorithms.setFunction(aValue, bValue);
            }
            });
        buttomSecantOperation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    Point2D p1 = Point2D.parseFromString(secantPoint1.getText());
                    Point2D p2 = Point2D.parseFromString(secantPoint2.getText());
                    Point2D intersection = CurveAlgorithms.ellipticSecant(p1, p2);
            }
        });
        buttonTangentOperation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    Point2D p1 = Point2D.parseFromString(secantPoint1.getText());
                    Point2D intersection = CurveAlgorithms.ellipticTangent(p1);
            }
        });

    }


    private void addBorder(JPanel panel, String title) {
        Border border = BorderFactory.createTitledBorder(title);
        panel.setBorder(border);
    }

    // labeledPanel.add(label, BorderLayout.NORTH); gibt namen doppelt drin ein
    private JPanel createLabeledPanel(String labelText, JTextField textField) {
            JPanel labeledPanel = new JPanel(new BorderLayout());
            //JLabel label = new JLabel(labelText);
            //labeledPanel.add(label, BorderLayout.NORTH);
            labeledPanel.add(textField, BorderLayout.NORTH);
            addBorder(labeledPanel, labelText);
            return labeledPanel;
    }

    private JPanel createLabeledPanel(String labelText, JTextArea textArea) {
            JPanel labeledPanel = new JPanel(new BorderLayout());
            //JLabel label = new JLabel(labelText);
            //labeledPanel.add(label, BorderLayout.NORTH);
            labeledPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
            addBorder(labeledPanel, labelText);
            return labeledPanel;
        }
}
