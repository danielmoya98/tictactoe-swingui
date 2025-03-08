package com.example.daniel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DifficultySelectionScreen extends JFrame {
    private char playerSymbol;

    public DifficultySelectionScreen(char playerSymbol) {
        this.playerSymbol = playerSymbol;
        setTitle("Select Difficulty");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        JLabel selectLabel = new JLabel("Select difficulty level", SwingConstants.CENTER);
        selectLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(selectLabel);

        String[] difficulties = {"easy", "medium", "hard"};
        for (String difficulty : difficulties) {
            JButton button = new JButton(difficulty.substring(0, 1).toUpperCase() + difficulty.substring(1));
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(e -> {
                new TicTacToe(playerSymbol, difficulty).setVisible(true);
                dispose();
            });
            add(button);
        }

        setSize(400, 400);
        setLocationRelativeTo(null);
    }
}