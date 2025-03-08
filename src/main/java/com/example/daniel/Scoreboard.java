package com.example.daniel;

import javax.swing.*;
import java.awt.*;

public class Scoreboard extends JPanel {
    private int xWins = 0;
    private int oWins = 0;
    private int draws = 0;
    private JLabel xWinsLabel;
    private JLabel oWinsLabel;
    private JLabel drawsLabel;

    public Scoreboard() {
        setLayout(new GridLayout(3, 1));
        xWinsLabel = new JLabel("Player X wins: " + xWins);
        oWinsLabel = new JLabel("Player O wins: " + oWins);
        drawsLabel = new JLabel("Draws: " + draws);
        add(xWinsLabel);
        add(oWinsLabel);
        add(drawsLabel);
    }

    public void updateScore(char winner) {
        if (winner == 'X') {
            xWins++;
        } else if (winner == 'O') {
            oWins++;
        } else {
            draws++;
        }
        xWinsLabel.setText("Player X wins: " + xWins);
        oWinsLabel.setText("Player O wins: " + oWins);
        drawsLabel.setText("Draws: " + draws);
    }
}