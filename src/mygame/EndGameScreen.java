package mygame;

import javax.swing.*;
import java.awt.*;

public class EndGameScreen extends JFrame {

    public EndGameScreen(String msg, GameManager manager, boolean ai) {

        setTitle("Game Over");
        setSize(320, 220);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel label = new JLabel(msg, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel buttons = new JPanel(new GridLayout(2, 1, 8, 8));

        JButton restart = new JButton("Restart");
        JButton menu = new JButton("Main Menu");

        restart.addActionListener(e -> {
            if (manager.getWindow() != null)
                manager.getWindow().dispose();

            dispose();

            GameManager newManager = new GameManager();
            newManager.setAIEnabled(manager.isAIEnabled());
            newManager.setAIDifficulty(manager.isAIEnabled() ? "easy" : "");

            new GameWindow(newManager);
        });

        menu.addActionListener(e -> {
            if (manager.getWindow() != null)
                manager.getWindow().dispose();

            dispose();
            new ModeSelectionScreen();
        });

        buttons.add(restart);
        buttons.add(menu);

        add(label, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        setVisible(true);
    }
}