package mygame;

// Import all Swing components for creating GUI elements like windows, buttons, and labels
import javax.swing.*;

// Import AWT components for things like colors, fonts, and layouts
import java.awt.*;

// This class creates the "Select Difficulty" screen that appears
// when the player chooses to play against AI.
// It lets the player pick Easy, Medium, Hard, or go back to the main menu.
public class MainMenu extends JFrame {

    // Constructor: this code runs automatically when we create a new MainMenu
    public MainMenu() {
        // Set the title that appears on the window frame
        setTitle("Select Difficulty");
        // Set the size of the window (width x height in pixels)
        setSize(420, 340);
        // Close the program if this window is closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Center the window on the screen
        setLocationRelativeTo(null);
        // Use BorderLayout to organize components (top, center, bottom, etc.)
        setLayout(new BorderLayout());

        // Create a label (text) at the top of the window
        JLabel title = new JLabel("Select Difficulty", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26)); // Make the text big and bold
        add(title, BorderLayout.NORTH); // Place it at the top of the window

        // Create a panel to hold the buttons in a vertical grid
        JPanel panel = new JPanel(new GridLayout(4, 1, 12, 12));
        // 4 rows, 1 column, 12 pixels spacing horizontally and vertically

        // Create the difficulty buttons
        JButton easy = new JButton("Easy");
        JButton medium = new JButton("Medium");
        JButton hard = new JButton("Hard");
        // Create a "Back" button to return to the main menu
        JButton back = new JButton("Return to Main Menu");

        // When the player clicks Easy, Medium, or Hard, start the game with that difficulty
        easy.addActionListener(e -> start("easy"));
        medium.addActionListener(e -> start("medium"));
        hard.addActionListener(e -> start("hard"));

        // When the player clicks Back, close this screen and open the main menu
        back.addActionListener(e -> {
            dispose(); // Close this window
            new ModeSelectionScreen(); // Open the main menu screen
        });

        // Add all buttons to the panel in order
        panel.add(easy);
        panel.add(medium);
        panel.add(hard);
        panel.add(back);

        // Add some padding around the buttons so they don't touch the edges
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Add the panel to the center of the window
        add(panel, BorderLayout.CENTER);

        // Make the window visible on the screen
        setVisible(true);
    }

    // Helper method to start a new game with AI
    private void start(String difficulty) {
        GameManager manager = new GameManager(); // Create a new game manager
        manager.setAIEnabled(true);               // Enable AI for this game
        manager.setAIDifficulty(difficulty);     // Set the chosen difficulty
        new GameWindow(manager);                  // Open the game window
        dispose();                                // Close the difficulty selection screen
    }
}