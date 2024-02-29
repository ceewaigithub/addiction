package world;

import javax.swing.JFrame;
import javax.swing.JPanel;

import blackjack.BlackJackPanel;

import javax.swing.BoxLayout;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setTitle("Crippling Addiction");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        GamePanel gamePanel = new GamePanel(window);
        mainPanel.add(gamePanel);

        // BlackJackPanel bjPanel = new BlackJackPanel();
        // mainPanel.add(bjPanel);

        window.add(mainPanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGame();
        gamePanel.startGameThread();

    }
}
