package org.example.maske;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.example.rsa.Handler.RSAHandler;
import org.example.rsa.PairTypes.PairCipherBlockLength;
import org.example.rsa.PairTypes.RSAKeyPair;

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

        JButton button = new JButton("Start");

        JButton buttonZusatz = new JButton("Basisaufgabe");

        // Add components to the first row
        JPanel column1 = new JPanel();
        column1.setLayout(new BoxLayout(column1, BoxLayout.Y_AXIS));
        JLabel ellipticCurveDefinition = new JLabel("y^2 = x^3 + ax + b");
        column1.add(ellipticCurveDefinition);
        column1.add(createLabeledPanel("a", aParameter));
        column1.add(createLabeledPanel("b", bParameter));
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