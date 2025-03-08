package com.example.daniel;

import javax.swing.*;
import java.util.Random;

public class AIPlayer {
    private Random random = new Random();

    public int[] getMove(JButton[][] buttons, String difficulty) {
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!buttons[row][col].getText().equals(""));
        return new int[]{row, col};
    }
}