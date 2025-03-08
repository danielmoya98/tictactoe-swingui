package com.example.daniel;

public class GameHistory {
    private int xWins;
    private int oWins;

    public GameHistory() {
        xWins = 0;
        oWins = 0;
    }

    public void recordWin(char player) {
        if (player == 'X') {
            xWins++;
        } else if (player == 'O') {
            oWins++;
        }
    }

    public String getHistory() {
        return "Player X wins: " + xWins + "\nPlayer O wins: " + oWins;
    }
}