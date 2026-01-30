package mygame;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    private final MiniBoard[][] miniBoards = new MiniBoard[3][3];
    private final GameManager manager;

    public GameWindow(GameManager manager) {
        this.manager = manager;
        manager.setWindow(this);

        setTitle("Ultimate Tic Tac Toe");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                miniBoards[i][j] = new MiniBoard(i, j, manager, this);
                boardPanel.add(miniBoards[i][j]);
            }

        add(boardPanel);
        setVisible(true);
        updateActiveBoardHighlight();

        if (isCurrentPlayerAI()) makeAIMove();
    }

    public MiniBoard getMiniBoard(int r, int c) {
        return miniBoards[r][c];
    }

    public GameManager getManager() {
        return manager;
    }

    public boolean isCurrentPlayerAI() {
        return manager.isAIEnabled() && manager.getCurrentPlayer() == Player.O;
    }

    public void updateActiveBoardHighlight() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                miniBoards[i][j].setHighlight(manager.isMiniBoardActive(i, j));
    }

    public void makeAIMove() {
        Timer t = new Timer(500, e -> {
            manager.makeAIMove(this);
            updateActiveBoardHighlight();
        });
        t.setRepeats(false);
        t.start();
    }
}