package highlow;

import java.awt.*;
import javax.swing.*;
import main.BettingSystem;
import main.Card;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import entity.User;


/**
 * The HighLowGUI class manages the graphical user interface for the High-Low card game.
 * It handles the game's layout, user interactions, and displays game information such as
 * the current card, player's score, and betting options.
 */
public class HighLowGUI {
    private JFrame frame;
    private HighLowGame highLowGame;

    private User user;
    private JFrame mapFrame;

    private BettingSystem bettingSystem; // The betting system for handling bets.
    // Definition of all JPanel components used in the layout.
    private JPanel gamePanel, buttonPanel, controlPanel, topPanel, bottomPanel, bettingPanel, centerPanel, centerPanelRow1, centerPanelRow2, centerPanelRow3;
    // Definition of all JLabel components for displaying text information.
    private JLabel topLabel, bottomLabel, centerLabel, messageLabel;
    // Definition of JButton components for user interaction.
    private JButton higherButton, lowerButton, exitButton, nextGameButton;

    // Constructor for HighLowGUI. Initializes the GUI components and sets up the game environment.
    public HighLowGUI(HighLowGame highLowGame, BettingSystem bettingSystem, JFrame mapFrame, User user) {
        this.mapFrame = mapFrame;
        this.user = user;
        this.bettingSystem = bettingSystem;
        bettingPanel = bettingSystem.getBettingPanel();
        this.highLowGame = highLowGame;
        frame = new JFrame("High Low");
        int boardWidth = 800;
        int boardHeight = 540;

        // Set up frame properties
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set up gamePanel
        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(0, 116, 3));
        
        // Top Panel
        topPanel = new JPanel();
        topPanel.setBackground(gamePanel.getBackground());
        topLabel = new JLabel("*Payout: (Bet * (Score + 1)) | (Score > 2) else 0");
        topLabel.setForeground(Color.WHITE);
        topLabel.setFont(new Font("Arial", Font.BOLD, 15));
        topLabel.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(topLabel);
        
        // Bottom Panel
        bottomPanel = new JPanel();
        bottomPanel.setBackground(gamePanel.getBackground());
        bottomLabel = new JLabel("Player (You)");
        bottomLabel.setForeground(Color.WHITE);
        bottomLabel.setFont(new Font("Arial", Font.BOLD, 15));
        bottomPanel.add(bottomLabel);
        
        // Center Panel
        centerPanel = new JPanel(new GridLayout(3,1));
        centerPanel.setBackground(gamePanel.getBackground());
        centerPanelRow1 = new JPanel(new BorderLayout());
        centerPanelRow2 = new JPanel();
        centerPanelRow3 = new JPanel();
        centerPanelRow1.setBackground(gamePanel.getBackground());
        centerPanelRow2.setBackground(gamePanel.getBackground());
        centerPanelRow3.setBackground(gamePanel.getBackground());
        centerLabel = new JLabel("HIGHER OR LOWER?");
        centerLabel.setForeground(Color.WHITE);
        centerLabel.setFont(new Font("Arial", Font.BOLD, 25));
        centerLabel.setHorizontalAlignment(JLabel.CENTER);
        centerPanelRow1.add(centerLabel, BorderLayout.CENTER);
        centerPanel.add(centerPanelRow1);
        centerPanel.add(centerPanelRow2);
        centerPanel.add(centerPanelRow3);
        
        // Add Top, Bottom and Center panels to the gamePanel
        gamePanel.add(topPanel, BorderLayout.NORTH);
        gamePanel.add(bottomPanel, BorderLayout.SOUTH);
        gamePanel.add(centerPanel, BorderLayout.CENTER);
        
        // Add messageLabel to the centerPanelRow3
        messageLabel = new JLabel();
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanelRow3.add(messageLabel);
        
        // Set up buttons
        higherButton = new JButton("Higher");
        higherButton.setFocusable(false);
        lowerButton = new JButton("Lower");
        lowerButton.setFocusable(false);
        exitButton = new JButton("Exit");
        exitButton.setFocusable(false);
        nextGameButton = new JButton("Next Game");
        nextGameButton.setFocusable(false);
        nextGameButton.setVisible(false);
        
        // Add buttons to the buttonPanel
        buttonPanel = new JPanel();
        buttonPanel.add(higherButton);
        buttonPanel.add(lowerButton);
        buttonPanel.add(nextGameButton);
        buttonPanel.add(exitButton);

        // Set up controlPanel
        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(bettingPanel);
        controlPanel.add(buttonPanel);
        
        // Add controlPanel to the frame's SOUTH position
        frame.add(controlPanel, BorderLayout.SOUTH);
        
        // Add gamePanel to the frame
        frame.add(gamePanel);

        // Add action listeners
        higherButton.addActionListener(e -> {
            boolean correctGuess = true;
            if (highLowGame.isCorrect("H")) {
                getScore();
            } else {
                correctGuess = false;
                endGame();
            }
            updateCardPanel(correctGuess);
        });
    
        lowerButton.addActionListener(e -> {
            boolean correctGuess = true;
            if (highLowGame.isCorrect("L")){
                getScore();
            } else {
                correctGuess = false;
                endGame();
            }
            updateCardPanel(correctGuess);
        });

        nextGameButton.addActionListener(e -> {
            restartGame();
            gamePanel.repaint();
        });

        bettingSystem.getPlaceBetButton().addActionListener(e->{
            placeBet();
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close frame
                bettingSystem.checkBankrupt();
                frame.setVisible(false);
                mapFrame.setVisible(true);
            }
        });

        // Make frame visible
        frame.setVisible(true);
    }

    // Initial method calls to set up the game environment.
    public void start() {
        showBettingControl();
        setMessage("Place your bet");
        highLowGame.startGame();
        Card currCard = highLowGame.getCurrCard();
        displayCard(currCard, centerPanelRow2);
        addHiddenCard();
    }

    // Updates the message label with the given text.
    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    // Prepares the GUI for a new round of the game.
    public void startRound(){
        hideBettingControl();
        getScore();
    }

    // Updates the display with the current score.
    public void getScore(){
        messageLabel.setText("Score: " + highLowGame.getScore());
    }

    // Displays a card on the given panel.
    public void displayCard(Card card, JPanel panel) {
        Image image = card.getImage();
        JLabel label = new JLabel(new ImageIcon(image));
        label.setLayout(new BorderLayout());
        panel.add(label);
        panel.revalidate();
        panel.repaint();
    }

    // Handles end-of-game logic, updating the GUI accordingly.
    public void endGame(){
        higherButton.setVisible(false);
        lowerButton.setVisible(false);
        int winnings = highLowGame.checkScore();
        messageLabel.setText("Game Over! Score: " + highLowGame.getScore() + " Winnings: " + winnings);
        if (bettingSystem.getPlayerBalance() > 0) {
            nextGameButton.setVisible(true);
        }
    }

    // Adds the next card to the display.
    public void addNextCard() {
        displayCard(highLowGame.getNextCard(), centerPanelRow2);
    }

    // Removes first element in GUI only when number of cards in panel > 5
    public void checkSize(){
        Component[] components = centerPanelRow2.getComponents();
        if (components.length > 5) {
            centerPanelRow2.remove(components[0]);
            centerPanelRow2.revalidate();
            centerPanelRow2.repaint();
        }
    }

    // Removes the last element (card) from the display panel.
    public void removeLastElement(){
        Component[] components = centerPanelRow2.getComponents();
        int lastIndex = components.length - 1;
        if (lastIndex >= 0) {
            centerPanelRow2.remove(components[lastIndex]);
            centerPanelRow2.revalidate();
            centerPanelRow2.repaint();
        }
    }

    // Adds a hidden card to the display to indicate the next card.
    public void addHiddenCard(){
        Card hiddenCard = new Card("b");
        displayCard(hiddenCard, centerPanelRow2);
    }

    // Updates the card display panel based on whether the guess was correct.
    public void updateCardPanel(boolean correctGuess){
        removeLastElement();
        addNextCard();
        if (correctGuess) {
            addHiddenCard();
        }
        checkSize();
    }

    // Resets the game environment and GUI for a new game.
    public void restartGame(){
        higherButton.setVisible(true);
        lowerButton.setVisible(true);
        nextGameButton.setVisible(false);
        highLowGame.endRound();
        centerPanelRow2.removeAll();
        centerPanelRow2.revalidate();
        centerPanelRow2.repaint();
        start();
    }

    // Hides betting controls when the game starts.
    public void hideBettingControl(){
        bettingPanel.setVisible(false);
        higherButton.setVisible(true);
        lowerButton.setVisible(true);
    }

    // Shows betting controls before the game starts.
    public void showBettingControl(){
        bettingSystem.updateBettingPanel();
        bettingPanel.setVisible(true);
        higherButton.setVisible(false);
        lowerButton.setVisible(false);
    }

    // Handles the logic for placing a bet before the game starts.
    public void placeBet(){
        if(bettingSystem.getPlayerBet() > 0) {
            bettingSystem.confirmBet();
            startRound();
        }else{
            setMessage("Place bet to start game");
        }
    }
}
