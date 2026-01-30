package mygame;

public class GameManager {

    private Player currentPlayer = Player.X;
    private boolean aiEnabled = false;
    private String aiDifficulty = "easy";
    private boolean gameOver = false;

    private int activeMiniRow = -1;
    private int activeMiniCol = -1;

    private final Player[][] miniWinners = new Player[3][3];

    private GameWindow window;

    public GameManager() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                miniWinners[i][j] = Player.EMPTY;
    }

    public void setWindow(GameWindow w) {
        window = w;
    }

    public GameWindow getWindow() {
        return window;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchTurn() {
        currentPlayer = (currentPlayer == Player.X) ? Player.O : Player.X;
    }

    public boolean isAIEnabled() {
        return aiEnabled;
    }

    public void setAIEnabled(boolean enabled) {
        aiEnabled = enabled;
    }

    public void setAIDifficulty(String difficulty) {
        aiDifficulty = difficulty;
    }

    public int getActiveMiniRow() {
        return activeMiniRow;
    }

    public int getActiveMiniCol() {
        return activeMiniCol;
    }

    public void setNextActiveBoard(int r, int c) {
        activeMiniRow = r;
        activeMiniCol = c;
    }

    public boolean isMiniBoardActive(int r, int c) {
        if (activeMiniRow == -1) return true;
        if (miniWinners[activeMiniRow][activeMiniCol] != Player.EMPTY) return true;
        return activeMiniRow == r && activeMiniCol == c;
    }

    public void markMiniBoardWinner(int r, int c, Player p) {
        miniWinners[r][c] = p;
        activeMiniRow = -1;
        activeMiniCol = -1;
    }

    public Player checkUltimateWinner() {
        int[][] combos = {
                {0,1,2},{3,4,5},{6,7,8},
                {0,3,6},{1,4,7},{2,5,8},
                {0,4,8},{2,4,6}
        };

        for (int[] c : combos) {
            Player p = miniWinners[c[0]/3][c[0]%3];
            if (p != Player.EMPTY &&
                    p == miniWinners[c[1]/3][c[1]%3] &&
                    p == miniWinners[c[2]/3][c[2]%3])
                return p;
        }
        return Player.EMPTY;
    }

    public boolean isDraw() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (miniWinners[i][j] == Player.EMPTY)
                    return false;
        return true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean v) {
        gameOver = v;
    }

    public void makeAIMove(GameWindow window) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (isMiniBoardActive(i, j)) {
                    MiniBoard mb = window.getMiniBoard(i, j);
                    if (mb.playWinningMove(Player.O)) return;
                    mb.makeRandomAIMove();
                    return;
                }
    }
}