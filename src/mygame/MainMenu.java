package mygame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Ultimate Tic Tac Toe - Menu");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Select Difficulty", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton easyBtn = new JButton("Easy (AI makes mistakes)");
        JButton mediumBtn = new JButton("Medium (Not as bad)");
        JButton hardBtn = new JButton("Hard (Difficult to beat)");

        easyBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        mediumBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        hardBtn.setFont(new Font("Arial", Font.PLAIN, 18));

        easyBtn.addActionListener((ActionEvent e) -> startGame("easy"));
        mediumBtn.addActionListener(e -> startGame("medium"));
        hardBtn.addActionListener(e -> startGame("hard"));

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        buttonPanel.add(easyBtn);
        buttonPanel.add(mediumBtn);
        buttonPanel.add(hardBtn);

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void startGame(String difficulty) {
        GameManager manager = new GameManager();
        switch (difficulty.toLowerCase()) {
            case "easy":
                manager.setAIEnabled(true);
                manager.setAIDifficulty("easy");
                break;
            case "medium":
                manager.setAIEnabled(true);
                manager.setAIDifficulty("medium");
                break;
            case "hard":
                manager.setAIEnabled(true);
                manager.setAIDifficulty("hard");
                break;

        }
        new GameWindow(manager);
        dispose();
    }
}

