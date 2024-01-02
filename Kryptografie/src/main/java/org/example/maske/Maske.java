package org.example.maske;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.example.rsa.Handler.RSAHandler;
import org.example.rsa.PairTypes.PairCipherBlockLength;
import org.example.rsa.PairTypes.RSAKeyPair;

class Maske {

        public int anzahlAnSchritte = 0;
        public int primzahlLänge = 0;

        //String Sammlung
        public String öffentlichAlice = "";
        public String öffentlichBob = "";
        public String privatAlice = "";
        public String privateBob = "";
        public String signaturAlice = "";
        public String signaturBob = "";
        public String signaturGültigkeitAlice = "Ausgabe von gültiger oder ungültiger Signatur";
        public String signaturGültigkeitBob = "Ausgabe von gültiger oder ungültiger Signatur";
        public int klarBlockLength = 0;
        public int chiffBlockLength = 0;

        RSAHandler handler = new RSAHandler();
        PairCipherBlockLength encryptedMessageAlice;
        PairCipherBlockLength encryptedMessageBob;
        PairCipherBlockLength receivedMessageAlice;
        PairCipherBlockLength receivedMessageBob;

        RSAKeyPair keyPairAlice;
        RSAKeyPair keyPairBob;

        public void launch() {
                JFrame frame = new JFrame("Integrationsprojekt");

                // Create a panel with a BorderLayout
                JPanel mainPanel = new JPanel(new BorderLayout());

                // Create a panel for the first row (1/3 of the window)
                JPanel row1 = new JPanel(new GridLayout(1, 3)); // 1 row, 3 columns in the first row
                addBorder(row1, "Schlüsselerzeugung");

                // Create components for the first row
                JTextField anzahlSchritteFeld = new JTextField(1);
                JTextField primzahlLängeFeld = new JTextField(1);
                JTextField seedM = new JTextField(1);

                JTextArea oeffentlichAlice = new JTextArea(öffentlichAlice);
                oeffentlichAlice.setEditable(false);
                oeffentlichAlice.setLineWrap(true);
                oeffentlichAlice.setWrapStyleWord(true);

                JTextArea oeffentlichBob = new JTextArea(öffentlichBob);
                oeffentlichBob.setEditable(false);
                oeffentlichBob.setLineWrap(true);
                oeffentlichBob.setWrapStyleWord(true);

                JButton button = new JButton("Start");

                JButton buttonZusatz = new JButton("Zusatzaufgabe");

                // Add components to the first row
                JPanel column1 = new JPanel();
                column1.setLayout(new BoxLayout(column1, BoxLayout.Y_AXIS));
                column1.add(createLabeledPanel("Anzahl der Schritte", anzahlSchritteFeld));
                column1.add(createLabeledPanel("Länge der Primzahlen", primzahlLängeFeld));
                column1.add(createLabeledPanel("Setze Seed M", seedM));
                JLabel klarBlockLengthLabel = new JLabel("Blocklänge des Klartextes : \n " + klarBlockLength);
                JLabel chiffBlockLengthLabel = new JLabel("Blocklänge des Chiffriertextes : \n " + chiffBlockLength);
                column1.add(klarBlockLengthLabel);
                column1.add(chiffBlockLengthLabel);
                column1.add(button);

                JPanel column2 = new JPanel(new GridLayout(1, 1));
                column2.add(createLabeledPanel("Öffentlicher Schlüssel Alice", oeffentlichAlice));

                JPanel column3 = new JPanel(new GridLayout(1, 1));
                column3.add(createLabeledPanel("Öffentlicher Schlüssel Bob", oeffentlichBob));

                JPanel column4 = new JPanel(new GridLayout(2, 1));
                column4.setLayout(new BoxLayout(column4, BoxLayout.Y_AXIS));
                //column4.add(createLabeledPanel("Länge des Klartexts", lengthKlartext));
                column4.add(buttonZusatz);

                row1.add(column1);
                row1.add(column2);
                row1.add(column3);
                row1.add(column4);

                // Create a panel for the second row (2/3 of the window)
                JPanel row2 = new JPanel(new GridLayout(1, 2)); // 2 columns in the second row
                addBorder(row2, "Alice und Bob");
                
                // Create components for the second row
                JTextArea geheimAlice = new JTextArea(privatAlice);
                geheimAlice.setEditable(false);
                geheimAlice.setLineWrap(true);
                geheimAlice.setWrapStyleWord(true);

                JTextArea geheimBob = new JTextArea(privateBob);
                geheimBob.setEditable(false);
                geheimBob.setLineWrap(true);
                geheimBob.setWrapStyleWord(true);

                JTextArea textAlice = new JTextArea();
                textAlice.setEditable(true);
                textAlice.setLineWrap(true);
                textAlice.setWrapStyleWord(true);

                JTextArea textBob = new JTextArea();
                textBob.setEditable(true);
                textBob.setLineWrap(true);
                textBob.setWrapStyleWord(true);

                JTextArea signaturenAlice = new JTextArea(signaturAlice);
                signaturenAlice.setEditable(false);
                signaturenAlice.setLineWrap(true);
                signaturenAlice.setWrapStyleWord(true);

                JTextArea signaturenBob = new JTextArea(signaturBob);
                signaturenBob.setEditable(false);
                signaturenBob.setLineWrap(true);
                signaturenBob.setWrapStyleWord(true);

                JLabel gueltigkeitAlice = new JLabel(signaturGültigkeitAlice);

                JLabel gueltigkeitBob = new JLabel(signaturGültigkeitBob);

                JButton buttonVerschlüsseln = new JButton("Verschlüsseln");
                JButton buttonSignieren = new JButton("Signieren");

                JButton buttonVerschlüsselnBob = new JButton("Verschlüsseln");
                JButton buttonSignierenBob = new JButton("Signieren");

                JButton buttonEntschlüsseln = new JButton("Entschlüsseln/Verifizieren");
                JButton buttonVersenden = new JButton("Versenden");

                JButton buttonEntschlüsselnBob = new JButton("Entschlüsseln/Verifizieren");
                JButton buttonVersendenBob = new JButton("Versenden");

                JButton buttonReset = new JButton("Reset");

                JButton buttonResetBob = new JButton("Reset");

                // Add components to the second row
                JPanel column21 = new JPanel(new GridLayout(8, 1));
                addBorder(column21, "Alice");
                column21.add(createLabeledPanel("Alice geheimerschlüsselteil d_A", geheimAlice));

                column21.add(createLabeledPanel("Klar- und Chiffriertext", textAlice));

                JPanel buttonRow = new JPanel(new GridLayout(1, 2));
                //addBorder(buttonRow, "test");
                JPanel buttonCol1 = new JPanel();
                buttonCol1.add(buttonVerschlüsseln);
                JPanel buttonCol2 = new JPanel();
                buttonCol2.add(buttonSignieren);
                buttonRow.add(buttonCol1);
                buttonRow.add(buttonCol2);

                column21.add(buttonRow);

                JPanel buttonRow2 = new JPanel(new GridLayout(1, 2));
                //addBorder(buttonRow, "test");
                JPanel buttonCol21 = new JPanel();
                buttonCol21.add(buttonEntschlüsseln);
                JPanel buttonCol22 = new JPanel();
                buttonCol22.add(buttonVersenden);
                buttonRow2.add(buttonCol21);
                buttonRow2.add(buttonCol22);

                column21.add(buttonRow2);
        
                column21.add(createLabeledPanel("Signaturen", signaturenAlice));

                JPanel buttonRow4 = new JPanel(new GridLayout(1, 2));
                JPanel buttonCol41 = new JPanel();
                buttonCol41.add(gueltigkeitAlice);
                buttonRow4.add(buttonCol41);
                column21.add(buttonRow4);

                JPanel buttonRow3 = new JPanel(new GridLayout(1, 2));
                JPanel buttonCol31 = new JPanel();
                buttonCol31.add(buttonReset);
                buttonRow3.add(buttonCol31);
                column21.add(buttonRow3);

                // Bobs Maske
                JPanel column22 = new JPanel(new GridLayout(8, 1));
                addBorder(column22, "Bob");
                
                column22.add(createLabeledPanel("Bobs geheimerschlüsselteil d_A", geheimBob));

                column22.add(createLabeledPanel("Klar- und Chiffriertext", textBob));

                JPanel buttonRowBob = new JPanel(new GridLayout(1, 2));
                //addBorder(buttonRow, "test");
                JPanel buttonCol1Bob = new JPanel();
                buttonCol1Bob.add(buttonVerschlüsselnBob);
                JPanel buttonCol2Bob = new JPanel();
                buttonCol2Bob.add(buttonSignierenBob);
                buttonRowBob.add(buttonCol1Bob);
                buttonRowBob.add(buttonCol2Bob);

                column22.add(buttonRowBob);

                JPanel buttonRow2Bob = new JPanel(new GridLayout(1, 2));
                //addBorder(buttonRow, "test");
                JPanel buttonCol21Bob = new JPanel();
                buttonCol21Bob.add(buttonEntschlüsselnBob);
                JPanel buttonCol22Bob = new JPanel();
                buttonCol22Bob.add(buttonVersendenBob);
                buttonRow2Bob.add(buttonCol21Bob);
                buttonRow2Bob.add(buttonCol22Bob);

                column22.add(buttonRow2Bob);
        
                column22.add(createLabeledPanel("Signaturen", signaturenBob));

                JPanel buttonRow4Bob = new JPanel(new GridLayout(1, 2));
                JPanel buttonCol41Bob = new JPanel();
                buttonCol41Bob.add(gueltigkeitBob);
                buttonRow4Bob.add(buttonCol41Bob);
                column22.add(buttonRow4Bob);

                JPanel buttonRow3Bob = new JPanel(new GridLayout(1, 2));
                JPanel buttonCol31Bob = new JPanel();
                buttonCol31Bob.add(buttonResetBob);
                buttonRow3Bob.add(buttonCol31Bob);
                column22.add(buttonRow3Bob);

                row2.add(column21);
                row2.add(column22);

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

                // Add action listener to the buttons
                button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                String anzahlSchritte = anzahlSchritteFeld.getText();
                                String primzahlLängeText = primzahlLängeFeld.getText();
                                
                                if(anzahlSchritte != "" && primzahlLängeText != ""){
                                        anzahlAnSchritte = Integer.valueOf(anzahlSchritte);
                                        primzahlLänge = Integer.valueOf(primzahlLängeText);
                                }

                                handler.setMillerRabinTrials(anzahlAnSchritte);
                                handler.setPrimeNumberLength(primzahlLänge);
                                handler.setNumberLengthPQ(primzahlLänge);
                                String seedMText = seedM.getText();
                                handler.setM(Integer.valueOf(seedMText));

                                try {
                                        keyPairAlice = handler.generateKeyPairAlice();
                                        keyPairBob = handler.generateKeyPairBob();
                                        oeffentlichAlice.setText(keyPairAlice.getPublicKey().toString());
                                        oeffentlichBob.setText(keyPairBob.getPublicKey().toString());
                                        handler.setBlockLength();
                                        geheimAlice.setText(keyPairAlice.getPrivateKey().toString());
                                        geheimBob.setText(keyPairBob.getPrivateKey().toString());
                                } catch (Exception e1) {
                                        // TODO Auto-generated catch block
                                        e1.printStackTrace();
                                }
                        }
                        });

                buttonVerschlüsseln.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                try {
                                        encryptedMessageAlice = handler.encryptMessageAlice(textAlice.getText());
                                        receivedMessageBob = encryptedMessageAlice;
                                        textAlice.setText(encryptedMessageAlice.getCipher());
                                } catch (Exception ex) {
                                        throw new RuntimeException(ex);
                                }
                        }
                        });

                buttonSignieren.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                try {
                                        handler.signatureForMessage(encryptedMessageAlice.getCipher());
                                } catch (Exception ex) {
                                        throw new RuntimeException(ex);
                                }
                        }
                        });

                buttonVerschlüsselnBob.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                try {
                                        encryptedMessageBob = handler.encryptMessageBob(textBob.getText());
                                        receivedMessageAlice = encryptedMessageBob;
                                        textBob.setText(encryptedMessageBob.getCipher());
                                } catch (Exception ex) {
                                        throw new RuntimeException(ex);
                                }
                        }
                        });

                buttonSignierenBob.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                try {
                                        handler.signatureForMessage(encryptedMessageBob.getCipher());
                                } catch (Exception ex) {
                                        throw new RuntimeException(ex);
                                }
                        }
                        });

                buttonEntschlüsseln.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                try {
                                        String message = handler.decryptMessageAlice(receivedMessageAlice);
                                        textAlice.setText(message);
                                } catch (Exception ex) {
                                        throw new RuntimeException(ex);
                                }
                        }
                        });

                buttonVersenden.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                textBob.setText(receivedMessageBob.getCipher());
                        }
                        });

                buttonEntschlüsselnBob.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                try {
                                        String message = handler.decryptMessageBob(receivedMessageBob);
                                        textBob.setText(message);
                                } catch (Exception ex) {
                                        throw new RuntimeException(ex);
                                }
                        }
                        });

                buttonVersendenBob.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                textAlice.setText(receivedMessageAlice.getCipher());
                        }
                        });

                buttonReset.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                textAlice.setText("");
                        }
                        });

                buttonResetBob.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                textBob.setText("");
                        }
                        });

                buttonZusatz.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                frame.dispose();
                                ZusatzMaske maske = new ZusatzMaske();
                                maske.test();
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