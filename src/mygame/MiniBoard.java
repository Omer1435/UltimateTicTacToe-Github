package mygame;

import javax.swing.*;
import java.awt.*;

// Import ArrayList to store dynamic lists of objects (like buttons)
import java.util.ArrayList;

// Import List, a general type of collection, used to hold groups of objects
import java.util.List;

// Import Random to generate random numbers (used for AI to pick moves randomly)
import java.util.Random;

// This class represents a single 3x3 mini-board in Ultimate Tic Tac Toe.
// Each mini-board contains 9 buttons for the cells and handles all player and AI moves.
public class MiniBoard extends JPanel {

    // Array of 9 buttons representing the 3x3 cells
    private final JButton[] buttons = new JButton[9];

    // Position of this mini-board in the main board (row, column)
    private final int row, col;

    // Reference to the game manager (keeps track of turns, winners, AI, etc.)
    private final GameManager manager;

    // Reference to the main game window
    private final GameWindow window;

    // Tracks whether this mini-board has been won
    private boolean won = false;

    // Label to display the winner on top of the mini-board
    private final JLabel winnerLabel;

    // Constructor: called when a new mini-board is created
    public MiniBoard(int r, int c, GameManager m, GameWindow w) {
        row = r;          // store row position
        col = c;          // store column position
        manager = m;      // store reference to the GameManager
        window = w;       // store reference to the GameWindow

        // Use an overlay layout so we can place the winner label on top of the buttons
        setLayout(new OverlayLayout(this));

        // Panel to hold the 3x3 buttons
        JPanel grid = new JPanel(new GridLayout(3, 3));

        // Create 9 buttons for the mini-board
        for (int i = 0; i < 9; i++) {
            JButton b = new JButton("");                   // start empty
            b.setFont(new Font("Arial", Font.BOLD, 24));  // make text large and bold
            int idx = i;                                  // store index for click event
            b.addActionListener(e -> click(b, idx));      // handle click on this button
            buttons[i] = b;                               // store button in array
            grid.add(b);                                  // add button to grid panel
        }

        // Create the winner label (will appear when someone wins this mini-board)
        winnerLabel = new JLabel("", SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 64));          // big font
        winnerLabel.setForeground(new Color(200, 0, 0, 160));           // semi-transparent red
        winnerLabel.setVisible(false);                                   // hidden until someone wins

        // Add winner label and the grid to this panel
        add(winnerLabel);
        add(grid);

        // Draw a black border around this mini-board
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }

    // Called when a player clicks a cell
    private void click(JButton b, int idx) {
        // Ignore clicks if:
        // 1. The game is over
        // 2. This mini-board is not active for the next move
        // 3. The button is already filled
        // 4. This mini-board has already been won
        if (manager.isGameOver() || !manager.isMiniBoardActive(row, col) || b.getText().length() > 0 || won)
            return;

        Player p = manager.getCurrentPlayer();  // get current player (X or O)
        b.setText(p.toString());                // mark the cell with X or O
        b.setEnabled(false);                    // disable the button so it can't be clicked again

        // Check if this move wins the mini-board
        if (checkWinner(p)) {
            won = true;                         // mark this mini-board as won
            winnerLabel.setText(p.toString());  // show winner label
            winnerLabel.setVisible(true);       // make the label visible
            manager.markMiniBoardWinner(row, col, p); // tell GameManager this board is won
            for (JButton x : buttons) x.setEnabled(false); // disable all buttons
        }

        // Set the next active mini-board based on this cell's position
        manager.setNextActiveBoard(idx / 3, idx % 3);

        // Switch turn to the other player
        manager.switchTurn();

        // Update highlights to show which board is active
        window.updateActiveBoardHighlight();

        // If it's AI's turn, schedule AI move after a short delay
        if (window.isCurrentPlayerAI()) {
            Timer timer = new Timer(300, e -> {
                manager.makeAIMove(window);          // AI makes a move
                window.updateActiveBoardHighlight(); // update highlights again
            });
            timer.setRepeats(false);  // run only once
            timer.start();
        }

        // Check if the game is over (either someone wins or it's a draw)
        if (manager.checkUltimateWinner() != Player.EMPTY || manager.isDraw()) {
            manager.setGameOver(true); // mark the game as over
            new EndGameScreen("Game Over", manager, manager.isAIEnabled()); // show end screen
        }
    }

    // Check if a given player has won this mini-board
    public boolean checkWinner(Player p) {
        // All winning combinations (rows, columns, diagonals)
        int[][] combos = {
                {0,1,2},{3,4,5},{6,7,8},
                {0,3,6},{1,4,7},{2,5,8},
                {0,4,8},{2,4,6}
        };
        for (int[] c : combos)
            if (p.toString().equals(buttons[c[0]].getText()) &&
                    p.toString().equals(buttons[c[1]].getText()) &&
                    p.toString().equals(buttons[c[2]].getText()))
                return true; // player has a winning combination
        return false;
    }

    // Check if a move can immediately win the board for the AI
    // Returns true if a winning move was found and executed
    public boolean playWinningMove(Player p) {
        for (JButton b : buttons) {
            if (b.getText().isEmpty()) {       // check empty cell
                b.setText(p.toString());       // temporarily place mark
                boolean win = checkWinner(p);  // check if it wins
                b.setText("");                 // remove temporary mark
                if (win) {
                    b.doClick();               // perform the winning move
                    return true;               // success
                }
            }
        }
        return false; // no winning move found
    }

    // Make a random AI move (used if no winning move is available)
    public void makeRandomAIMove() {
        List<JButton> free = new ArrayList<>();
        for (JButton b : buttons)
            if (b.getText().isEmpty()) free.add(b); // collect all empty buttons
        if (!free.isEmpty())
            free.get(new Random().nextInt(free.size())).doClick(); // click a random one
    }

    // Return whether this mini-board has been won
    public boolean isWon() {
        return won;
    }

    // Highlight or un-highlight this mini-board (red border if active, black if not)
    public void setHighlight(boolean h) {
        setBorder(BorderFactory.createLineBorder(h ? Color.RED : Color.BLACK, 2));
    }
}