package mygame;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Select Difficulty");
        setSize(420, 340);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Select Difficulty", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(4, 1, 12, 12));

        JButton easy = new JButton("Easy");
        JButton medium = new JButton("Medium");
        JButton hard = new JButton("Hard");
        JButton back = new JButton("Return to Main Menu");

        easy.addActionListener(e -> start("easy"));
        medium.addActionListener(e -> start("medium"));
        hard.addActionListener(e -> start("hard"));

        back.addActionListener(e -> {
            dispose();
            new ModeSelectionScreen();
        });

        panel.add(easy);
        panel.add(medium);
        panel.add(hard);
        panel.add(back);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void start(String difficulty) {
        GameManager manager = new GameManager();
        manager.setAIEnabled(true);
        manager.setAIDifficulty(difficulty);
        new GameWindow(manager);
        dispose();
    }
}