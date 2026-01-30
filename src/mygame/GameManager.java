package mygame;

// This class manages the state of the entire Ultimate Tic Tac Toe game.
// It keeps track of whose turn it is, which mini-boards are won, which board is active,
// whether AI is enabled, and handles AI moves.
public class GameManager {

    // Who's currently playing: X or O
    private Player currentPlayer = Player.X;

    // Whether AI is enabled (for Player vs AI mode)
    private boolean aiEnabled = false;

    // Difficulty level of AI: "easy", "medium", "hard" (currently only "easy" implemented)
    private String aiDifficulty = "easy";

    // Flag to track if the game has ended
    private boolean gameOver = false;

    // Coordinates of the mini-board the next player must play in (-1 = any board)
    private int activeMiniRow = -1;
    private int activeMiniCol = -1;

    // Tracks winners of each mini-board; empty if not won yet
    private final Player[][] miniWinners = new Player[3][3];

    // Reference to the main game window (used for AI moves)
    private GameWindow window;

    // Constructor: initializes all mini-boards as empty
    public GameManager() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                miniWinners[i][j] = Player.EMPTY;
    }

    // Set or get the GameWindow reference
    public void setWindow(GameWindow w) {
        window = w;
    }

    public GameWindow getWindow() {
        return window;
    }

    // Get the current player
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    // Switch turns between X and O
    public void switchTurn() {
        currentPlayer = (currentPlayer == Player.X) ? Player.O : Player.X;
    }

    // Check if AI is enabled
    public boolean isAIEnabled() {
        return aiEnabled;
    }

    // Enable or disable AI
    public void setAIEnabled(boolean enabled) {
        aiEnabled = enabled;
    }

    // Set the AI difficulty
    public void setAIDifficulty(String difficulty) {
        aiDifficulty = difficulty;
    }

    // Get the currently active mini-board coordinates
    public int getActiveMiniRow() {
        return activeMiniRow;
    }

    public int getActiveMiniCol() {
        return activeMiniCol;
    }

    // Set which mini-board the next player must play in
    public void setNextActiveBoard(int r, int c) {
        activeMiniRow = r;
        activeMiniCol = c;
    }

    // Check if a mini-board is currently active for the next move
    public boolean isMiniBoardActive(int r, int c) {
        // If no specific board is active, all boards are playable
        if (activeMiniRow == -1) return true;
        // If the targeted board has already been won, allow playing anywhere
        if (miniWinners[activeMiniRow][activeMiniCol] != Player.EMPTY) return true;
        // Otherwise, only the active board is playable
        return activeMiniRow == r && activeMiniCol == c;
    }

    // Mark a mini-board as won and reset active board restriction
    public void markMiniBoardWinner(int r, int c, Player p) {
        miniWinners[r][c] = p;
        activeMiniRow = -1;
        activeMiniCol = -1;
    }

    // Check if there is an ultimate winner across the 3x3 mini-boards
    public Player checkUltimateWinner() {
        // All possible winning combinations
        int[][] combos = {
                {0,1,2},{3,4,5},{6,7,8}, // rows
                {0,3,6},{1,4,7},{2,5,8}, // columns
                {0,4,8},{2,4,6}          // diagonals
        };

        for (int[] c : combos) {
            // Convert flat index to mini-board coordinates
            Player p = miniWinners[c[0]/3][c[0]%3];
            // Check if all three positions are won by the same player
            if (p != Player.EMPTY &&
                    p == miniWinners[c[1]/3][c[1]%3] &&
                    p == miniWinners[c[2]/3][c[2]%3])
                return p; // Return the winner
        }
        return Player.EMPTY; // No ultimate winner yet
    }

    // Check if all mini-boards are full, meaning a draw
    public boolean isDraw() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (miniWinners[i][j] == Player.EMPTY)
                    return false; // Still at least one mini-board is undecided
        return true; // All boards decided, it's a draw
    }

    // Check if the game is over
    public boolean isGameOver() {
        return gameOver;
    }

    // Set the game over flag
    public void setGameOver(boolean v) {
        gameOver = v;
    }

    // Make a move for the AI
    public void makeAIMove(GameWindow window) {
        // Loop through all mini-boards
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (isMiniBoardActive(i, j)) {          // Find an active board
                    MiniBoard mb = window.getMiniBoard(i, j);
                    if (mb.playWinningMove(Player.O))      // Try to win if possible
                        return;
                    mb.makeRandomAIMove();               // Otherwise, play randomly
                    return;                               // AI only makes one move
                }
    }
}