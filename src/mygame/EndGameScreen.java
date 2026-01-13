package mygame;

import javax.swing.*;
import java.awt.*;

public class EndGameScreen extends JFrame {
    public EndGameScreen(String message, GameManager oldManager, boolean isAIEnabled) {
        setTitle("Game Over");
        setSize(350, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel resultLabel = new JLabel(message, SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(resultLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton restartBtn = new JButton("Restart Match");
        JButton mainMenuBtn = new JButton("Main Menu");

        restartBtn.addActionListener(e -> {
            dispose();
            GameManager manager = new GameManager();
            manager.setAIEnabled(isAIEnabled);
            new GameWindow(manager);
        });

        mainMenuBtn.addActionListener(e -> {
            dispose();
            new ModeSelectionScreen();
        });

        buttonPanel.add(restartBtn);
        buttonPanel.add(mainMenuBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}