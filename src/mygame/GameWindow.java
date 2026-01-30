package mygame;

import javax.swing.*;
import java.awt.*;

// This class creates the main game window where the 3x3 grid of mini-boards is displayed.
// It handles the overall layout of the boards, highlights the active mini-board, and
// triggers AI moves when necessary.
public class GameWindow extends JFrame {

    // A 3x3 array to hold all the mini-boards
    private final MiniBoard[][] miniBoards = new MiniBoard[3][3];

    // Reference to the game manager (keeps track of turns, winners, AI, etc.)
    private final GameManager manager;

    // Constructor: runs when a new GameWindow is created
    public GameWindow(GameManager manager) {
        this.manager = manager;
        manager.setWindow(this); // Tell the game manager about this window

        // Set the window title
        setTitle("Ultimate Tic Tac Toe");
        // Set the window size (width x height in pixels)
        setSize(600, 600);
        // Close the program if this window is closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Center the window on the screen
        setLocationRelativeTo(null);

        // Create a panel to hold the 3x3 mini-boards
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Create a MiniBoard at position (i,j) and add it to the panel
                miniBoards[i][j] = new MiniBoard(i, j, manager, this);
                boardPanel.add(miniBoards[i][j]);
            }
        }

        // Add the panel of mini-boards to the window
        add(boardPanel);

        // Show the window on the screen
        setVisible(true);

        // Highlight the currently active mini-board (where the next move is allowed)
        updateActiveBoardHighlight();

        // If AI starts first, make the AI move immediately
        if (isCurrentPlayerAI()) makeAIMove();
    }

    // Get a specific mini-board by row and column
    public MiniBoard getMiniBoard(int r, int c) {
        return miniBoards[r][c];
    }

    // Get the GameManager controlling this game
    public GameManager getManager() {
        return manager;
    }

    // Check if it's currently the AI's turn
    public boolean isCurrentPlayerAI() {
        return manager.isAIEnabled() && manager.getCurrentPlayer() == Player.O;
    }

    // Update which mini-board is highlighted to show the player where they are allowed to play
    public void updateActiveBoardHighlight() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                // Ask the mini-board to highlight itself if it’s active
                miniBoards[i][j].setHighlight(manager.isMiniBoardActive(i, j));
    }

    // Make an AI move with a short delay to simulate thinking
    public void makeAIMove() {
        // Timer delays the move by 500 milliseconds
        Timer t = new Timer(500, e -> {
            // Ask the GameManager to make the AI move
            manager.makeAIMove(this);
            // Update which mini-board is highlighted after the AI moves
            updateActiveBoardHighlight();
        });
        t.setRepeats(false); // Only run the timer once
        t.start();           // Start the timer
    }
}