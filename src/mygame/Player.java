package mygame;

public enum Player {
    X, O, EMPTY;

    @Override
    public String toString() {
        return switch (this) {
            case X -> "X";
            case O -> "O";
            default -> "";
        };
    }
}
