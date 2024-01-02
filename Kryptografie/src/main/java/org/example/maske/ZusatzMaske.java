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

        // Add the main panel to the frame
        frame.add(mainPanel);
        frame.add(mainPanel);

        // Set frame properties
        frame.setSize(1200, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        row1.setPreferredSize(new Dimension(0, frame.getHeight() / 3));
        frame.setVisible(true);
    }
}
