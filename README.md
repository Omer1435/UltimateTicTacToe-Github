# Ultimate Tic Tac Toe

A Java Swing desktop version of **Ultimate Tic Tac Toe**. The game uses a 3x3 grid of smaller Tic Tac Toe boards, where each move decides which mini-board the next player must play in.

The project includes a graphical interface, player-vs-player mode, player-vs-AI mode, active-board highlighting, win detection for mini-boards, overall winner detection, and an end-game screen with restart and main menu options.

## Features

- **Java Swing GUI** with separate screens for mode selection, difficulty selection, gameplay, and game over.
- **Player vs Player mode** for two local players.
- **Player vs AI mode** with difficulty selection screen.
- **Ultimate Tic Tac Toe rules**, where the cell chosen in one mini-board determines the next active mini-board.
- **Active board highlighting** so players can easily see where the next move is allowed.
- **Mini-board winner tracking** using X and O overlays.
- **Ultimate winner detection** across the larger 3x3 board.
- **Restart and main menu options** after the game ends.

> Note: The AI currently uses a simple move strategy. It first looks for a winning move on the active board and otherwise plays a random available move. The difficulty menu exists, but the AI behaviour is currently basic.

## Project Structure

```text
UltimateTicTacToe-Github/
├── src/
│   └── mygame/
│       ├── Main.java                 # Application entry point
│       ├── ModeSelectionScreen.java  # First screen: PvP or Player vs AI
│       ├── MainMenu.java             # AI difficulty selection screen
│       ├── GameWindow.java           # Main game window and board layout
│       ├── MiniBoard.java            # Individual 3x3 mini-board logic
│       ├── GameManager.java          # Game state, turns, AI, and winner logic
│       ├── EndGameScreen.java        # Restart and main menu screen
│       └── Player.java               # Player enum: X, O, EMPTY
├── UltimateTicTacToe.iml             # IntelliJ IDEA module file
└── README.md
```

## Requirements

- Java JDK 14 or newer recommended
- IntelliJ IDEA, VS Code, or any Java-compatible IDE

The project uses Java Swing, so no external libraries are required.

## How to Run

### Option 1: Run in IntelliJ IDEA

1. Clone or download the repository.
2. Open the project in IntelliJ IDEA.
3. Make sure the `src` folder is marked as the source root.
4. Open `src/mygame/Main.java`.
5. Run the `main` method.

### Option 2: Run from the Terminal

From the project root, compile the Java files:

```bash
javac -d out src/mygame/*.java
```

Then run the game:

```bash
java -cp out mygame.Main
```

## How to Play

1. Start the game.
2. Choose either **Player vs AI** or **Player vs Player**.
3. If you choose AI mode, select a difficulty.
4. Player X starts first.
5. Click a cell inside an active mini-board.
6. The cell you choose sends the next player to the matching mini-board on the larger board.
7. Win mini-boards by getting three in a row.
8. Win the full game by claiming three mini-boards in a row.

## Game Rules Summary

Ultimate Tic Tac Toe is played on nine small Tic Tac Toe boards arranged in a larger 3x3 grid.

- Each small board works like normal Tic Tac Toe.
- The position of your move decides which small board your opponent must play in next.
- If the required board is already won, the next player can play in any available board.
- The first player to win three mini-boards in a row wins the full game.

## Future Improvements

Possible improvements for later versions:

- Add separate AI logic for easy, medium, and hard difficulties.
- Display the actual winner on the game-over screen instead of only showing "Game Over".
- Add score tracking across multiple rounds.
- Improve the visual design of the board and menus.
- Add draw detection for individual mini-boards.
- Add sound effects or animations.

## Author

Created by **Omer Adeel**.
