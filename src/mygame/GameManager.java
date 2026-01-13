package mygame;

import java.util.*;

public class GameManager {
    private mygame.Player currentPlayer = mygame.Player.X;
    private mygame.Player[][] boardWinners = new mygame.Player[3][3];
    private int activeMiniRow = -1, activeMiniCol = -1;
    private boolean gameOver = false;
    private boolean isAIEnabled = false;

    public boolean isValidNextBoard(GameWindow window) {
        if (activeMiniRow == -1 || activeMiniCol == -1) return false;
        MiniBoard board = window.getMiniBoard(activeMiniRow, activeMiniCol);
        return board != null && !board.isFull() && !board.isWon();
    }


    public GameManager() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                boardWinners[i][j] = mygame.Player.EMPTY;
    }

    public mygame.Player getCurrentPlayer() { return currentPlayer; }
    public void switchTurn() { currentPlayer = (currentPlayer == mygame.Player.X) ? mygame.Player.O : mygame.Player.X; }

    public boolean isMiniBoardActive(int row, int col) {
        return (activeMiniRow == -1 && boardWinners[row][col] == mygame.Player.EMPTY)
                || (row == activeMiniRow && col == activeMiniCol);
    }

    public void setNextActiveBoard(int row, int col) {
        activeMiniRow = row;
        activeMiniCol = col;
    }

    public int getActiveMiniRow() { return activeMiniRow; }
    public int getActiveMiniCol() { return activeMiniCol; }

    public void markMiniBoardWinner(int row, int col, mygame.Player winner) {
        boardWinners[row][col] = winner;
    }

    public mygame.Player checkUltimateWinner() {
        for (int i = 0; i < 3; i++) {
            if (boardWinners[i][0] != mygame.Player.EMPTY &&
                    boardWinners[i][0] == boardWinners[i][1] &&
                    boardWinners[i][1] == boardWinners[i][2]) return boardWinners[i][0];
            if (boardWinners[0][i] != mygame.Player.EMPTY &&
                    boardWinners[0][i] == boardWinners[1][i] &&
                    boardWinners[1][i] == boardWinners[2][i]) return boardWinners[0][i];
        }

        if (boardWinners[0][0] != mygame.Player.EMPTY &&
                boardWinners[0][0] == boardWinners[1][1] &&
                boardWinners[1][1] == boardWinners[2][2]) return boardWinners[0][0];

        if (boardWinners[0][2] != mygame.Player.EMPTY &&
                boardWinners[0][2] == boardWinners[1][1] &&
                boardWinners[1][1] == boardWinners[2][0]) return boardWinners[0][2];

        return mygame.Player.EMPTY;
    }

    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean state) { gameOver = state; }

    public void setAIEnabled(boolean enabled) { isAIEnabled = enabled; }
    public boolean isAIEnabled() { return isAIEnabled; }

    public void makeEasyAIMove(mygame.GameWindow window) {
        if (!isAIEnabled || gameOver || currentPlayer != mygame.Player.O) return;

        Random rand = new Random();
        List<mygame.MiniBoard> availableBoards = new ArrayList<>();

        if (activeMiniRow != -1 && activeMiniCol != -1) {
            mygame.MiniBoard target = window.getMiniBoard(activeMiniRow, activeMiniCol);
            if (target.isFull() || target.isWon()) {
                activeMiniRow = -1;
                activeMiniCol = -1;
            }
        }

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                mygame.MiniBoard mb = window.getMiniBoard(i, j);
                if (!mb.isFull() && !mb.isWon()) availableBoards.add(mb);
            }

        if (availableBoards.isEmpty()) return;

        mygame.MiniBoard boardToPlay = (activeMiniRow != -1) ? window.getMiniBoard(activeMiniRow, activeMiniCol)
                : availableBoards.get(rand.nextInt(availableBoards.size()));

        boardToPlay.makeRandomAIMove(this, window);
    }
}
