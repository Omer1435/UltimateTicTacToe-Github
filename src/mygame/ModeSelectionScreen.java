package mygame;

import javax.swing.*;
import java.awt.*;

public class ModeSelectionScreen extends JFrame {

    public ModeSelectionScreen() {
        setTitle("Select Game Mode");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JLabel title = new JLabel("Choose Game Mode", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title);

        JButton aiBtn = new JButton("Player vs AI");
        aiBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        aiBtn.addActionListener(e -> {
            dispose();
            new MainMenu();
        });
        add(aiBtn);

        JButton pvpBtn = new JButton("Player vs Player");
        pvpBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        pvpBtn.addActionListener(e -> {
            dispose();
            GameManager manager = new GameManager();
            new GameWindow(manager);
        });
        add(pvpBtn);

        setVisible(true);
    }
}
