package mygame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MiniBoard extends JPanel {

    private final JButton[] buttons = new JButton[9];
    private final int row, col;
    private final GameManager manager;
    private final GameWindow window;
    private boolean won = false;
    private final JLabel winnerLabel;

    public MiniBoard(int r, int c, GameManager m, GameWindow w) {
        row = r;
        col = c;
        manager = m;
        window = w;

        setLayout(new OverlayLayout(this));
        JPanel grid = new JPanel(new GridLayout(3, 3));

        for (int i = 0; i < 9; i++) {
            JButton b = new JButton("");
            b.setFont(new Font("Arial", Font.BOLD, 24));
            int idx = i;
            b.addActionListener(e -> click(b, idx));
            buttons[i] = b;
            grid.add(b);
        }

        winnerLabel = new JLabel("", SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 64));
        winnerLabel.setForeground(new Color(200, 0, 0, 160));
        winnerLabel.setVisible(false);

        add(winnerLabel);
        add(grid);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }

    private void click(JButton b, int idx) {
        if (manager.isGameOver() || !manager.isMiniBoardActive(row, col) || b.getText().length() > 0 || won)
            return;

        Player p = manager.getCurrentPlayer();
        b.setText(p.toString());
        b.setEnabled(false);

        if (checkWinner(p)) {
            won = true;
            winnerLabel.setText(p.toString());
            winnerLabel.setVisible(true);
            manager.markMiniBoardWinner(row, col, p);
            for (JButton x : buttons) x.setEnabled(false);
        }

        manager.setNextActiveBoard(idx / 3, idx % 3);
        manager.switchTurn();
        window.updateActiveBoardHighlight();

        if (window.isCurrentPlayerAI()) {
            Timer timer = new Timer(300, e -> {
                manager.makeAIMove(window);
                window.updateActiveBoardHighlight();
            });
            timer.setRepeats(false);
            timer.start();
        }

        if (manager.checkUltimateWinner() != Player.EMPTY || manager.isDraw()) {
            manager.setGameOver(true);
            new EndGameScreen("Game Over", manager, manager.isAIEnabled());
        }
    }

    public boolean checkWinner(Player p) {
        int[][] combos = {
                {0,1,2},{3,4,5},{6,7,8},
                {0,3,6},{1,4,7},{2,5,8},
                {0,4,8},{2,4,6}
        };
        for (int[] c : combos)
            if (p.toString().equals(buttons[c[0]].getText()) &&
                    p.toString().equals(buttons[c[1]].getText()) &&
                    p.toString().equals(buttons[c[2]].getText()))
                return true;
        return false;
    }

    public boolean playWinningMove(Player p) {
        for (JButton b : buttons) {
            if (b.getText().isEmpty()) {
                b.setText(p.toString());
                boolean win = checkWinner(p);
                b.setText("");
                if (win) {
                    b.doClick();
                    return true;
                }
            }
        }
        return false;
    }

    public void makeRandomAIMove() {
        List<JButton> free = new ArrayList<>();
        for (JButton b : buttons)
            if (b.getText().isEmpty()) free.add(b);
        if (!free.isEmpty())
            free.get(new Random().nextInt(free.size())).doClick();
    }

    public boolean isWon() {
        return won;
    }

    public void setHighlight(boolean h) {
        setBorder(BorderFactory.createLineBorder(h ? Color.RED : Color.BLACK, 2));
    }
}