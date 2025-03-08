// PlayerSelectionScreen.java
package com.example.daniel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerSelectionScreen extends JFrame {
    public PlayerSelectionScreen() {
        setTitle("Select Player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        JLabel selectLabel = new JLabel("Select your player", SwingConstants.CENTER);
        selectLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(selectLabel);

        JButton xButton = new JButton("Player X");
        xButton.setFont(new Font("Arial", Font.BOLD, 20));
        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DifficultySelectionScreen('X').setVisible(true);
                dispose();
            }
        });
        add(xButton);

        JButton oButton = new JButton("Player O");
        oButton.setFont(new Font("Arial", Font.BOLD, 20));
        oButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DifficultySelectionScreen('O').setVisible(true);
                dispose();
            }
        });
        add(oButton);

        setSize(400, 300);
        setLocationRelativeTo(null);
    }
}