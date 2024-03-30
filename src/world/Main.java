package world;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.BoxLayout;

public class Main {
    public static void main(String[] args) {
        // Create a new JFrame window
        JFrame window = new JFrame();
        window.setTitle("Crippling Addiction");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        // Create a new JPanel to hold the main content
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Create a new GamePanel and add it to the main panel
        GamePanel gamePanel = new GamePanel(window);
        mainPanel.add(gamePanel);

        // Add the main panel to the window
        window.add(mainPanel);
        window.pack();

        // Center the window on the screen and make it visible
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Start the game logic and the game thread
        gamePanel.gameLogic.startGame();
        gamePanel.startGameThread();
    }
}
