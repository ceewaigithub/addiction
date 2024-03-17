package blackjack;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;

import entity.User;
import main.Card;
import main.Player;

public class BlackJackPanel extends JPanel {

    // Variables
    private BlackJack2 blackjack;
    private JFrame frame;
    private JPanel gamePanel, buttonPanel; 
    private JButton hitButton, stayButton, exitButton;
    private int boardWidth = 800;
    private int boardHeight = 540;
    private BlackJackAssetSetter bJackAssetSetter;

    // Constructor
    public BlackJackPanel(BlackJack2 blackjack) {

        // Set up frame
        frame = new JFrame("BlackJack");
        frame.setSize(boardWidth, boardHeight);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set up gamePanel + add gamePanel
        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(255, 90, 01));
        frame.add(gamePanel);

        // Set up buttonPanel + add buttonPanel
        buttonPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                // Paint standard features
                super.paintComponent(g);
                // Paint hands
                bJackAssetSetter.drawHands(g);
                // If game ends, endGame paints the results
                if (!blackjack.getGameStatus()) {
                    endGame(g, blackjack.determineWinners());
                }
            }
        };
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Set up hitButton + purpose hitButton + add hitButton
        hitButton = new JButton("Hit");
        buttonPanel.add(hitButton);
        hitButton.setFocusable(false);
        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hit in game
                blackjack.hit();
                // Repaint panel
                repaint();
            }
        });

        // Set up stayButton + purpose stayButton + add stayButton
        stayButton = new JButton("Stay");
        buttonPanel.add(stayButton);
        stayButton.setFocusable(false);
        stayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Stay in game
                blackjack.stay();
                // Repaint panel
                repaint();
            }
        });

        // Set up exitButton + purpose exitButton
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close frame
                frame.setVisible(false);
            }
        });

        // Set up Asset Setter
        bJackAssetSetter = new BlackJackAssetSetter(blackjack, boardWidth, boardHeight);

        // After setting up GUI, start game
        blackjack.startGame();
    }

    public void endGame(Graphics g, String message) {

        // Display results
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.white);
        g.drawString(message, (boardWidth - g.getFontMetrics().stringWidth(message)) / 2, boardHeight / 2);

        // Remove all buttons + add exit button
        buttonPanel.removeAll();
        buttonPanel.add(exitButton);
    }
}
