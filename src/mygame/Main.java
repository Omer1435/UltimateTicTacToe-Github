package mygame;

import javax.swing.SwingUtilities;

// Entry point: launch the game in Swing's event thread
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ModeSelectionScreen::new);
    }
}