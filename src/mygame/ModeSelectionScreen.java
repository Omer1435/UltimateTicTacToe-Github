package mygame;

import javax.swing.*;
import java.awt.*;

// This class creates the first screen the player sees: the mode selection menu.
// It allows the player to choose between Player vs AI or Player vs Player.
public class ModeSelectionScreen extends JFrame {

    // Constructor: runs when a new ModeSelectionScreen is created
    public ModeSelectionScreen() {
        // Set the title of the window (appears in the title bar)
        setTitle("Select Game Mode");
        // Set the window size (width x height in pixels)
        setSize(400, 200);
        // Close the program if this window is closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Center the window on the screen
        setLocationRelativeTo(null);
        // Use a GridLayout to organize components in 3 rows, 1 column, with spacing of 10 pixels
        setLayout(new GridLayout(3, 1, 10, 10));

        // Create a label at the top of the window
        JLabel title = new JLabel("Choose Game Mode", SwingConstants.CENTER);
        // Make the label text bold and slightly bigger
        title.setFont(new Font("Arial", Font.BOLD, 20));
        // Add the label to the window
        add(title);

        // Create a button for Player vs AI mode
        JButton aiBtn = new JButton("Player vs AI");
        aiBtn.setFont(new Font("Arial", Font.PLAIN, 18)); // set button text size
        aiBtn.addActionListener(e -> {
            dispose();           // Close this screen
            new MainMenu();      // Open the difficulty selection menu for AI
        });
        add(aiBtn); // Add the button to the window

        // Create a button for Player vs Player mode
        JButton pvpBtn = new JButton("Player vs Player");
        pvpBtn.setFont(new Font("Arial", Font.PLAIN, 18)); // set button text size
        pvpBtn.addActionListener(e -> {
            dispose();                  // Close this screen
            GameManager manager = new GameManager(); // Create a new game manager (AI disabled)
            new GameWindow(manager);    // Open the main game window for PvP
        });
        add(pvpBtn); // Add the button to the window

        // Make the window visible on the screen
        setVisible(true);
    }
}