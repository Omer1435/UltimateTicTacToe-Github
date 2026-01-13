package mygame;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private MiniBoard[][] miniBoards = new MiniBoard[3][3];
    private GameManager manager;

    public GameWindow(GameManager manager) {
        this.manager = manager;

        setTitle("Ultimate Tic Tac Toe");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                miniBoards[i][j] = new MiniBoard(i, j, manager, this);
                boardPanel.add(miniBoards[i][j]);
            }
        }

        add(boardPanel);
        setVisible(true);
        updateActiveBoardHighlight();

        if (isCurrentPlayerAI()) {
            makeAIMove();
        }
    }

    public MiniBoard getMiniBoard(int row, int col) {
        return miniBoards[row][col];
    }

    public void updateActiveBoardHighlight() {
        int activeRow = manager.getActiveMiniRow();
        int activeCol = manager.getActiveMiniCol();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                MiniBoard mb = miniBoards[i][j];
                boolean shouldHighlight = manager.isMiniBoardActive(i, j);
                mb.setHighlight(shouldHighlight);
            }
        }
    }

    public boolean isCurrentPlayerAI() {
        return manager.isAIEnabled() && manager.getCurrentPlayer() == Player.O;
    }

    public void makeAIMove() {
        Timer timer = new Timer(500, e -> {
            manager.makeEasyAIMove(this);
        });
        timer.setRepeats(false);
        timer.start();
    }
}