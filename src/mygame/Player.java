package mygame;

// This enum represents the three possible states of a cell or mini-board:
// X = player X, O = player O, EMPTY = cell or mini-board is not yet claimed
public enum Player {
    X,      // Player X
    O,      // Player O
    EMPTY;  // No player yet

    // Converts the enum value to a string so it can be displayed on buttons or labels
    @Override
    public String toString() {
        // If the value is X, return "X"; if O, return "O"; if EMPTY, return empty string
        return switch (this) {
            case X -> "X";
            case O -> "O";
            default -> "";
        };
    }
}