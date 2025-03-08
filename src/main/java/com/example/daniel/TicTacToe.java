package com.example.daniel;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;

        public class TicTacToe extends JFrame {
            private JButton[][] buttons = new JButton[3][3];
            private boolean xTurn;
            private JLabel statusLabel;
            private int movesCount = 0;
            private GameHistory gameHistory;
            private Timer turnTimer;
            private int timeLeft = 10;
            private String difficulty;
            private AIPlayer aiPlayer;
            private Scoreboard scoreboard;
            private boolean isAgainstAI;
            private char playerSymbol;

            public TicTacToe(char initialPlayer, String difficulty) {
                this.xTurn = true;
                this.difficulty = difficulty;
                this.playerSymbol = initialPlayer;
                this.isAgainstAI = true;
                this.aiPlayer = new AIPlayer();
                this.gameHistory = new GameHistory();
                setupUI();
                initializeGame();
            }

            public static void main(String[] args) {
                EventQueue.invokeLater(() -> {
                    TicTacToe game = new TicTacToe('X', "Easy");
                    game.setVisible(true);
                });
            }

            private void setupUI() {
                setTitle("Tic Tac Toe - " + difficulty + " mode");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLayout(new BorderLayout());

                // Game board
                JPanel boardPanel = new JPanel(new GridLayout(3, 3, 5, 5));
                boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                boardPanel.setBackground(new Color(186, 186, 186));

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        buttons[i][j] = new JButton("");
                        buttons[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                        buttons[i][j].setFocusPainted(false);
                        final int row = i;
                        final int col = j;
                        buttons[i][j].addActionListener(e -> handleClick(row, col));
                        boardPanel.add(buttons[i][j]);
                    }
                }

                // Status Panel
                JPanel statusPanel = new JPanel(new BorderLayout());
                statusLabel = new JLabel("Player " + (xTurn ? "X" : "O") + "'s turn", SwingConstants.CENTER);
                statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
                statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                statusPanel.add(statusLabel, BorderLayout.CENTER);

                // Scoreboard
                scoreboard = new Scoreboard();
                statusPanel.add(scoreboard, BorderLayout.EAST);

                // Control Panel
                JPanel controlPanel = new JPanel();
                JButton resetButton = new JButton("New Game");
                JButton historyButton = new JButton("Show History");
                JButton saveButton = new JButton("Save Game");
                JButton loadButton = new JButton("Load Game");
                JButton themeButton = new JButton("Change Theme");

                resetButton.addActionListener(e -> resetGame());
                historyButton.addActionListener(e -> showHistory());
                saveButton.addActionListener(e -> saveGame());
                loadButton.addActionListener(e -> loadGame());
                themeButton.addActionListener(e -> changeTheme());

                controlPanel.add(resetButton);
                controlPanel.add(historyButton);
                controlPanel.add(saveButton);
                controlPanel.add(loadButton);
                controlPanel.add(themeButton);

                add(statusPanel, BorderLayout.NORTH);
                add(boardPanel, BorderLayout.CENTER);
                add(controlPanel, BorderLayout.SOUTH);

                setSize(500, 600);
                setLocationRelativeTo(null);
            }

            private void initializeGame() {
                // Timer setup
                turnTimer = new Timer(1000, e -> updateTimer());
                turnTimer.start();

                // If AI goes first
                if (isAgainstAI && playerSymbol == 'O') {
                    makeAIMove();
                }
            }

            private void updateTimer() {
                timeLeft--;
                statusLabel.setText("Player " + (xTurn ? "X" : "O") + "'s turn - Time left: " + timeLeft + "s");
                if (timeLeft == 0) {
                    if (isAgainstAI && ((playerSymbol == 'X') == xTurn)) {
                        makeAIMove();
                    } else {
                        switchTurn();
                    }
                }
            }

            private void handleClick(int row, int col) {
                if (buttons[row][col].getText().equals("") &&
                    (!isAgainstAI || (playerSymbol == 'X') == xTurn)) {
                    makeMove(row, col);

                    if (!checkGameEnd() && isAgainstAI) {
                        makeAIMove();
                    }
                }
            }

            private void makeMove(int row, int col) {
                buttons[row][col].setText(xTurn ? "X" : "O");
                buttons[row][col].setForeground(xTurn ? Color.BLUE : Color.RED);
                movesCount++;
                SoundEffects.playSound("click.wav");

                if (checkGameEnd()) {
                    return;
                }

                switchTurn();
            }

            private void makeAIMove() {
                if (movesCount < 9) {
                    int[] move = aiPlayer.getMove(buttons, difficulty);
                    makeMove(move[0], move[1]);
                }
            }

            private boolean checkGameEnd() {
                if (checkWin()) {
                    endGame((xTurn ? "X" : "O") + " wins!");
                    scoreboard.updateScore(xTurn ? 'X' : 'O');
                    gameHistory.recordWin(xTurn ? 'X' : 'O');
                    SoundEffects.playSound("win.wav");
                    return true;
                } else if (movesCount == 9) {
                    endGame("It's a draw!");
                    scoreboard.updateScore('D');
                    return true;
                }
                return false;
            }

            private boolean checkWin() {
                // Horizontal, vertical and diagonal checks
                for (int i = 0; i < 3; i++) {
                    if (checkLine(i, 0, i, 1, i, 2) || // Horizontal
                        checkLine(0, i, 1, i, 2, i)) { // Vertical
                        return true;
                    }
                }
                // Diagonals
                return checkLine(0, 0, 1, 1, 2, 2) ||
                       checkLine(0, 2, 1, 1, 2, 0);
            }

            private boolean checkLine(int r1, int c1, int r2, int c2, int r3, int c3) {
                return !buttons[r1][c1].getText().equals("") &&
                       buttons[r1][c1].getText().equals(buttons[r2][c2].getText()) &&
                       buttons[r1][c1].getText().equals(buttons[r3][c3].getText());
            }

            private void switchTurn() {
                xTurn = !xTurn;
                timeLeft = 10;
                statusLabel.setText("Player " + (xTurn ? "X" : "O") + "'s turn - Time left: " + timeLeft + "s");
            }

            private void endGame(String message) {
                statusLabel.setText(message);
                turnTimer.stop();
                disableButtons();
            }

            private void disableButtons() {
                for (JButton[] row : buttons) {
                    for (JButton button : row) {
                        button.setEnabled(false);
                    }
                }
            }

            private void resetGame() {
                for (JButton[] row : buttons) {
                    for (JButton button : row) {
                        button.setText("");
                        button.setEnabled(true);
                    }
                }
                xTurn = true;
                movesCount = 0;
                timeLeft = 10;
                statusLabel.setText("Player X's turn - Time left: " + timeLeft + "s");
                turnTimer.start();

                if (isAgainstAI && playerSymbol == 'O') {
                    makeAIMove();
                }
            }

            private void showHistory() {
                JOptionPane.showMessageDialog(this, gameHistory.getHistory());
            }

            private void saveGame() {
                GameState.saveGame(buttons, xTurn, movesCount);
                JOptionPane.showMessageDialog(this, "Game saved successfully!");
            }

            private void loadGame() {
                Object[] gameData = GameState.loadGame();
                if (gameData != null) {
                    updateGameState(gameData);
                    JOptionPane.showMessageDialog(this, "Game loaded successfully!");
                }
            }

            private void updateGameState(Object[] gameData) {
                JButton[][] loadedButtons = (JButton[][]) gameData[0];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        buttons[i][j].setText(loadedButtons[i][j].getText());
                        buttons[i][j].setEnabled(loadedButtons[i][j].isEnabled());
                    }
                }
                xTurn = (boolean) gameData[1];
                movesCount = (int) gameData[2];
                timeLeft = 10;
                statusLabel.setText("Player " + (xTurn ? "X" : "O") + "'s turn - Time left: " + timeLeft + "s");
            }

            private void changeTheme() {
                String[] themes = {"Classic", "Dark", "Light"};
                String selected = (String) JOptionPane.showInputDialog(
                    this,
                    "Choose a theme:",
                    "Theme Selection",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    themes,
                    themes[0]
                );

                if (selected != null) {
                    applyTheme(selected);
                }
            }

            private void applyTheme(String theme) {
                Color bgColor, btnColor, textColor;
                switch (theme) {
                    case "Dark":
                        bgColor = new Color(50, 50, 50);
                        btnColor = new Color(70, 70, 70);
                        textColor = Color.WHITE;
                        break;
                    case "Light":
                        bgColor = new Color(240, 240, 240);
                        btnColor = new Color(255, 255, 255);
                        textColor = Color.BLACK;
                        break;
                    default: // Classic
                        bgColor = new Color(186, 186, 186);
                        btnColor = UIManager.getColor("Button.background");
                        textColor = Color.BLACK;
                }

                getContentPane().setBackground(bgColor);
                statusLabel.setForeground(textColor);
                for (JButton[] row : buttons) {
                    for (JButton button : row) {
                        button.setBackground(btnColor);
                        if (button.getText().isEmpty()) {
                            button.setForeground(textColor);
                        }
                    }
                }
            }
        }