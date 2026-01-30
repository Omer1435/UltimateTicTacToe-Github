package mygame;  // Put this file in the "mygame" folder/package so all game files are organized together

// SwingUtilities helps open GUI windows safely in Java's "event thread"
import javax.swing.SwingUtilities;

// This is the starting point of the game.
// When you run the program, Java looks for the "main" method and starts here.
public class Main {

    public static void main(String[] args) {
        // SwingUtilities.invokeLater ensures that the game window opens
        // in the "Swing event thread", which is basically the special thread
        // that handles all the buttons, clicks, and UI updates in Java games.
        //
        // ModeSelectionScreen::new is shorthand for "create a new ModeSelectionScreen window".
        SwingUtilities.invokeLater(ModeSelectionScreen::new);
    }
}
