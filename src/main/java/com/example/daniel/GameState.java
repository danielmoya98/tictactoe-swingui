package com.example.daniel;

import javax.swing.*;
import java.io.*;

public class GameState {
    public static void saveGame(JButton[][] buttons, boolean xTurn, int movesCount) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("gameState.dat"))) {
            out.writeObject(buttons);
            out.writeBoolean(xTurn);
            out.writeInt(movesCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object[] loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("gameState.dat"))) {
            JButton[][] buttons = (JButton[][]) in.readObject();
            boolean xTurn = in.readBoolean();
            int movesCount = in.readInt();
            return new Object[]{buttons, xTurn, movesCount};
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}