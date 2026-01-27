package mygame;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class MiniBoard extends JPanel {
    private JButton[] buttons = new JButton[9];
    private int row, col;
    private GameManager manager;
    private mygame.GameWindow window;
    private boolean isWon = false;
    private JLabel winnerLabel;

    public MiniBoard(int row, int col, GameManager manager, mygame.GameWindow window) {
        this.row = row;
        this.col = col;
        this.manager = manager;
        this.window = window;

        setLayout(new OverlayLayout(this));
        JPanel grid = new JPanel(new GridLayout(3, 3));

        for (int i = 0; i < 9; i++) {
            JButton button = new JButton("");
            button.setFont(new Font("Arial", Font.BOLD, 24));
            final int index = i;

            button.addActionListener(e -> {
                if (!manager.isValidNextBoard(window)) {
                    manager.setNextActiveBoard(-1, -1);
                    window.updateActiveBoardHighlight();
                }

                boolean isActive = manager.isMiniBoardActive(this.row, this.col);
                if (button.getText().equals("") && !isWon && isActive && !manager.isGameOver()) {
                    Player current = manager.getCurrentPlayer();
                    button.setText(current.toString());
                    button.setEnabled(false);

                    if (checkWinner(current)) {
                        isWon = true;
                        winnerLabel.setText(current.toString());
                        winnerLabel.setVisible(true);
                        for (JButton b : buttons) b.setEnabled(false);
                        manager.markMiniBoardWinner(row, col, current);

                        Player gameWinner = manager.checkUltimateWinner();
                        if (gameWinner != Player.EMPTY) {
                            manager.setGameOver(true);

                            boolean ai = window.isCurrentPlayerAI();
                            boolean isPlayerWinner = gameWinner == Player.X;

                            String endMessage = ai
                                    ? (isPlayerWinner ? "Congrats! You win!" : "Better luck next time!")
                                    : (gameWinner + " wins the game!");

                            new EndGameScreen(endMessage, manager, ai);
                            return;
                        }
                    }

                    manager.setNextActiveBoard(index / 3, index % 3);
                    manager.switchTurn();
                    window.updateActiveBoardHighlight();

                    if (!manager.isGameOver() && window.isCurrentPlayerAI()) {
                        window.makeAIMove();
                    }
                }
            });

            buttons[i] = button;
            grid.add(button);
        }

        winnerLabel = new JLabel("", SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 60));
        winnerLabel.setForeground(new Color(200, 0, 0, 180));
        winnerLabel.setVisible(false);

        add(winnerLabel);
        add(grid);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }

    public void setHighlight(boolean highlight) {
        setBorder(BorderFactory.createLineBorder(highlight ? Color.RED : Color.BLACK, 2));
    }

    public boolean checkWinner(Player player) {
        String mark = player.toString();
        String[][] grid = new String[3][3];

        for (int i = 0; i < 9; i++) {
            grid[i / 3][i % 3] = buttons[i].getText();
        }

        for (int i = 0; i < 3; i++) {
            if (mark.equals(grid[i][0]) && mark.equals(grid[i][1]) && mark.equals(grid[i][2])) return true;
            if (mark.equals(grid[0][i]) && mark.equals(grid[1][i]) && mark.equals(grid[2][i])) return true;
        }

        return (mark.equals(grid[0][0]) && mark.equals(grid[1][1]) && mark.equals(grid[2][2]))
                || (mark.equals(grid[0][2]) && mark.equals(grid[1][1]) && mark.equals(grid[2][0]));
    }

    // ===== THIS IS THE ADDED METHOD =====
    public JButton getWinningMove(Player player) {
        for (int i = 0; i < 9; i++) {
            JButton btn = buttons[i];
            if (btn.getText().equals("")) {

                btn.setText(player.toString());
                boolean win = checkWinner(player);
                btn.setText("");

                if (win) return btn;
            }
        }
        return null;
    }
    // ==================================

    public boolean isFull() {
        for (JButton b : buttons) {
            if (b.getText().equals("")) return false;
        }
        return true;
    }

    public boolean isWon() {
        return isWon;
    }

    public void makeRandomAIMove(GameManager manager, GameWindow window) {
        List<JButton> available = new ArrayList<>();
        for (JButton b : buttons) {
            if (b.getText().equals("")) available.add(b);
        }
        if (!available.isEmpty()) {
            available.get(new Random().nextInt(available.size())).doClick();
        }
    }
}