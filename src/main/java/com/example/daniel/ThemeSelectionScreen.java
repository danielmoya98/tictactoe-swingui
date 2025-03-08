package com.example.daniel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThemeSelectionScreen extends JFrame {
    public ThemeSelectionScreen() {
        setTitle("Select Theme");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        JLabel selectLabel = new JLabel("Select a theme", SwingConstants.CENTER);
        selectLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(selectLabel);

        JButton classicButton = new JButton("Classic");
        classicButton.setFont(new Font("Arial", Font.BOLD, 20));
        classicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TicTacToe('X', "classic").setVisible(true);
                dispose();
            }
        });
        add(classicButton);

        JButton darkButton = new JButton("Dark");
        darkButton.setFont(new Font("Arial", Font.BOLD, 20));
        darkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TicTacToe('X', "dark").setVisible(true);
                dispose();
            }
        });
        add(darkButton);

        JButton lightButton = new JButton("Light");
        lightButton.setFont(new Font("Arial", Font.BOLD, 20));
        lightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TicTacToe('X', "light").setVisible(true);
                dispose();
            }
        });
        add(lightButton);

        setSize(400, 400);
        setLocationRelativeTo(null);
    }
}