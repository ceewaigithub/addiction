package blackjack;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

import entity.User;
import main.BettingSystem;

public class BlackJackPanel extends JPanel {

    // Variables
    private BlackJack2 blackjack;
    private JFrame frame;
    private JFrame mapFrame;
    private JPanel gamePanel, buttonPanel, controlPanel, topPanel, bottomPanel, bettingPanel; 
    private JLabel messageLabel, topLabel, bottomLabel;
    private JButton hitButton, stayButton, exitButton;
    private int boardWidth = 800;
    private int boardHeight = 540;
    private BlackJackAssetSetter bJackAssetSetter;
    private BettingSystem bettingSystem;
    private User user;

    // Constructor
    public BlackJackPanel(BlackJack2 blackjack, JFrame frame, JFrame mapFrame, BettingSystem bettingSystem, User user) {
        
        // Pass in blackjack game
        this.blackjack = blackjack;

        // Get betting system
        this.bettingSystem = bettingSystem;

        // Pass in current frame
        this.frame = frame;

        // Pass in previous frame
        this.mapFrame = mapFrame;

        // Pass in user
        this.user = user;

        // Set up frame
        frame.setSize(boardWidth, boardHeight);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set up blackJackAssetSetter 
        bJackAssetSetter = new BlackJackAssetSetter(blackjack, boardWidth, boardHeight);

        // Set up gamePanel
        gamePanel = new JPanel() {
            public void paintComponent(Graphics g) {
                // Paint standard features
                super.paintComponent(g);
                // Paint hands
                bJackAssetSetter.drawHands(g);
                // If game ends, endGame paints the results
                if (!blackjack.getGameStatus()) {
                    endGame(blackjack.determineWinners());
                }
            }
        };
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(0, 116, 3));

        // Top Panel
        topPanel = new JPanel();
        topPanel.setBackground(gamePanel.getBackground());
        topLabel = new JLabel("Dealer");
        topLabel.setForeground(Color.WHITE);
        topLabel.setFont(new Font("Arial", Font.BOLD, 15));
        topPanel.add(topLabel);

        // Bottom Panel
        bottomPanel = new JPanel();
        bottomPanel.setBackground(gamePanel.getBackground());
        bottomLabel = new JLabel("Player (You)");
        bottomLabel.setForeground(Color.WHITE);
        bottomLabel.setFont(new Font("Arial", Font.BOLD, 15));
        bottomPanel.add(bottomLabel);

        // Add Top and Bottom panels to the gamePanel
        gamePanel.add(topPanel, BorderLayout.NORTH);
        gamePanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add messageLabel to the gamePanel
        messageLabel = new JLabel();
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gamePanel.add(messageLabel, BorderLayout.CENTER);
        
        // // Update frame color immediately
        // gamePanel.revalidate();
        // gamePanel.repaint();

        // Set up buttonPanel
        buttonPanel = new JPanel();

        // Set up hitButton + purpose hitButton + add hitButton
        hitButton = new JButton("Hit");
        buttonPanel.add(hitButton);
        hitButton.setFocusable(false);
        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hit in game
                blackjack.hit();
                // Dealer turn
                blackjack.dealerTurn(false);
                // Repaint panel
                frame.revalidate();
                frame.repaint();
            }
        });

        // Set up stayButton + purpose stayButton + add stayButton
        stayButton = new JButton("Stay");
        buttonPanel.add(stayButton);
        stayButton.setFocusable(false);
        stayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Dealer turn
                blackjack.dealerTurn(true);
                // Repaint panel
                frame.revalidate();
                frame.repaint();
            }
        });

        // Set up exitButton + purpose exitButton
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.money += (bettingSystem.getPlayerBalance() - 1000);
                // Close frame
                frame.setVisible(false);
                mapFrame.setVisible(true);
            }
        });

        // Set up betting panel
        bettingPanel = bettingSystem.getBettingPanel();

        // Get betButton + purpose betButton
        bettingSystem.getPlaceBetButton().addActionListener(e -> {
            placeBet();
        });

        // Add buttonPanel & bettingPanel to the frame's SOUTH position
        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel,BoxLayout.Y_AXIS));
        controlPanel.add(bettingPanel);
        controlPanel.add(buttonPanel);
        frame.add(controlPanel, BorderLayout.SOUTH);

        // Add gamePanel
        frame.add(gamePanel);

        // After setting up GUI, place bet
        bettingSystem.updateBettingPanel();
        bettingPanel.setVisible(true);
        buttonPanel.setVisible(false);
        setMessage("Place bet to start game");
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    // Remove bettingPanel when player places bet
    public void placeBet(){
        if(bettingSystem.getPlayerBet() > 0) {
            bettingPanel.setVisible(false);
            buttonPanel.setVisible(true);
            setMessage("");
            // After betting, start game
            blackjack.startGame();
        }
    }

    public void endGame(String message) {

        // Repaint panel
        frame.revalidate();
        frame.repaint();

        // Display results
        setMessage(message);

        // Remove all buttons + add exit button
        buttonPanel.removeAll();
        buttonPanel.add(exitButton);
    }
}
