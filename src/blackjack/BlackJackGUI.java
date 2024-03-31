package blackjack;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import card.BettingSystem;

/**
 * The BlackJackGUI class represents the graphical user interface for the Blackjack game.
 */
public class BlackJackGUI {

    // Variables
    private BlackJackGame blackjack; // The Blackjack game instance
    private JFrame frame; // The main frame of the GUI
    private JFrame mapFrame; // The frame of the map
    private JPanel gamePanel, buttonPanel, controlPanel, topPanel, bottomPanel, bettingPanel; // Panels for different sections of the GUI
    private JLabel messageLabel, topLabel, bottomLabel; // Labels for displaying messages and titles
    private JButton hitButton, stayButton, exitButton, nextGameButton; // Buttons for game actions
    private BlackJackAssetSetter bJackAssetSetter; // Asset setter for drawing game assets
    private BettingSystem bettingSystem; // The betting system for the game

    /**
     * Constructs a BlackJackGUI object.
     * 
     * @param blackjack The Blackjack game instance
     * @param mapFrame The frame of the map
     * @param bettingSystem The betting system for the game
     */
    public BlackJackGUI(BlackJackGame blackjack, JFrame mapFrame, BettingSystem bettingSystem) {
        
        // Pass in blackjack game
        this.blackjack = blackjack;

        // Get betting system
        this.bettingSystem = bettingSystem;

        // Pass in previous frame
        this.mapFrame = mapFrame;

        // New frame dimensions
        int boardWidth = 800;
        int boardHeight = 600;

        // Set up frame
        frame = new JFrame("Blackjack");
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

                Image backgroundImage = new ImageIcon("res/cards/table.png").getImage();
                g.drawImage(backgroundImage, 0, -80, boardWidth, boardHeight, this);
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

        // Set up buttonPanel
        buttonPanel = new JPanel();

        // Set up hitButton + purpose hitButton + add hitButton
        hitButton = new JButton("Hit");
        hitButton.setFocusable(false);
        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hit in game
                blackjack.hit();
                // Repaint panel
                frame.revalidate();
                frame.repaint();
            }
        });
        buttonPanel.add(hitButton);

        // Set up stayButton + purpose stayButton + add stayButton
        stayButton = new JButton("Stay");
        stayButton.setFocusable(false);
        stayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Stay in game
                blackjack.stay();
                // Repaint panel
                frame.revalidate();
                frame.repaint();
            }
        });
        buttonPanel.add(stayButton);

        // Set up nextGameButton + purpose nextGameButton + add (invisible)
        nextGameButton = new JButton("Next Game");
        nextGameButton.setFocusable(false);
        nextGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Restart game
                restartGame();
                // Repaint panel
                frame.revalidate();
                frame.repaint();
            }
        });
        buttonPanel.add(nextGameButton);
        nextGameButton.setVisible(false);

        // Set up exitButton + purpose exitButton + add (invisible)
        exitButton = new JButton("Exit");
        exitButton.setFocusable(false);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close frame
                bettingSystem.checkBankrupt();
                frame.setVisible(false);
                mapFrame.setVisible(true);
            }
        });
        buttonPanel.add(exitButton);

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
        startGame();
    }

    /**
     * Starts the game by updating the betting panel and displaying the "Place bet to start game" message.
     */
    public void startGame() {
        bettingSystem.updateBettingPanel();
        bettingPanel.setVisible(true);
        stayButton.setVisible(false);
        hitButton.setVisible(false);
        setMessage("Place bet to start game");
    }

    /**
     * Sets the message label with the specified message.
     * 
     * @param message The message to be displayed
     */
    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    /**
     * Removes the betting panel when the player places a bet.
     * If the player's bet is greater than 0, the stay and hit buttons are made visible and the game is started.
     */
    public void placeBet(){
        if(bettingSystem.getPlayerBet() > 0) {
            bettingPanel.setVisible(false);
            stayButton.setVisible(true);
            hitButton.setVisible(true);
            setMessage("");
            // After betting, start game
            blackjack.startGame();
            bettingSystem.confirmBet();
        }
    }

    /**
     * Restarts the game by clearing the previous message, making the original buttons visible,
     * clearing all cards and resetting the game status, and starting the game.
     */
    public void restartGame(){
        // Clear previous string
        setMessage("");
        // Set original buttons visible
        nextGameButton.setVisible(false);
        hitButton.setVisible(true);
        stayButton.setVisible(true);
        // Clear all cards + reset game status
        // ???
        blackjack.setGameStatus(true);
        blackjack.endRound();
        // Start
        startGame();
    }

    /**
     * Ends the game by repainting the panel, displaying the results message,
     * and making the exit button and next game button visible if applicable.
     * 
     * @param message The message to be displayed as the game result
     */
    public void endGame(String message) {

        // Repaint panel
        frame.revalidate();
        frame.repaint();

        // Display results
        setMessage(message);

        // Set exit to visible
        hitButton.setVisible(false);
        stayButton.setVisible(false);
        exitButton.setVisible(true);
        if (bettingSystem.getPlayerBalance() > 0) {
            nextGameButton.setVisible(true);
        }
    }
}
