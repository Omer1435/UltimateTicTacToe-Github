package mygame;

import javax.swing.*;
import java.awt.*;

// This class creates the "Game Over" screen that appears when the game ends.
// It shows a message and gives two options: restart the game or go back to the main menu.
public class EndGameScreen extends JFrame {

    // Constructor: runs when a new EndGameScreen is created
    // msg = text to display (e.g., "Game Over")
    // manager = reference to the current GameManager (keeps track of the game state)
    // ai = true if the game involved AI, false for PvP
    public EndGameScreen(String msg, GameManager manager, boolean ai) {

        // Set the window title
        setTitle("Game Over");
        // Set the window size (width x height in pixels)
        setSize(320, 220);
        // Center the window on the screen
        setLocationRelativeTo(null);
        // Use a BorderLayout to organize components (center and south)
        setLayout(new BorderLayout());
        // Close only this window when clicking the close button
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Create a label to show the message in the center
        JLabel label = new JLabel(msg, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18)); // make the text bold

        // Create a panel to hold the buttons
        JPanel buttons = new JPanel(new GridLayout(2, 1, 8, 8)); // 2 rows, 1 column, 8px spacing

        // Button to restart the game
        JButton restart = new JButton("Restart");
        // Button to go back to the main menu
        JButton menu = new JButton("Main Menu");

        // When restart is clicked
        restart.addActionListener(e -> {
            // Close the current game window if it exists
            if (manager.getWindow() != null)
                manager.getWindow().dispose();

            // Close the end game screen
            dispose();

            // Create a new GameManager for the new game
            GameManager newManager = new GameManager();
            newManager.setAIEnabled(manager.isAIEnabled()); // keep AI setting
            newManager.setAIDifficulty(manager.isAIEnabled() ? "easy" : ""); // reset AI difficulty

            // Open a new game window with the new manager
            new GameWindow(newManager);
        });

        // When main menu is clicked
        menu.addActionListener(e -> {
            // Close the current game window if it exists
            if (manager.getWindow() != null)
                manager.getWindow().dispose();

            // Close the end game screen
            dispose();
            // Open the mode selection screen
            new ModeSelectionScreen();
        });

        // Add buttons to the buttons panel
        buttons.add(restart);
        buttons.add(menu);

        // Add the label to the center of the window
        add(label, BorderLayout.CENTER);
        // Add the buttons panel to the bottom of the window
        add(buttons, BorderLayout.SOUTH);

        // Make the window visible on the screen
        setVisible(true);
    }
}