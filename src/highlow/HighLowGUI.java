package highlow;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import main.BettingGUI;
import main.BettingSystem;
import main.Player;
import main.Card;

public class HighLowGUI {
    private JFrame frame;
    private HighLowGame highLowGame;

    private JPanel gamePanel, buttonPanel, topPanel, bottomPanel, centerPanel, centerPanelRow1, centerPanelRow2, centerPanelRow3;
    private JLabel topLabel, bottomLabel, messageLabel;
    private JButton higherButton, lowerButton, exitButton, nextGameButton;
    private List<Player> players;

    public HighLowGUI(HighLowGame highLowGame) {
        this.highLowGame = highLowGame;

        frame = new JFrame("High Low");
        gamePanel = new JPanel();
        buttonPanel = new JPanel();

        // Set up frame
        int boardWidth = 800;
        int boardHeight = 540;
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up gamePanel
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(0, 116, 3));

        // Top Panel
        topPanel = new JPanel();
        topPanel.setBackground(gamePanel.getBackground());
        topLabel = new JLabel("HIGHER OR LOWER?");
        topLabel.setForeground(Color.WHITE);
        topLabel.setFont(new Font("Arial", Font.BOLD, 25));
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
        centerPanelRow1.add(topLabel, BorderLayout.CENTER);
        centerPanel.add(centerPanelRow1);
        centerPanel.add(centerPanelRow2);
        centerPanel.add(centerPanelRow3);
      
        // Add Top, Bottom and Center panels to the gamePanel
        gamePanel.add(topPanel, BorderLayout.NORTH);
        gamePanel.add(bottomPanel, BorderLayout.SOUTH);
        gamePanel.add(centerPanel, BorderLayout.CENTER);

        // Add messageLabel to the gamePanel
        messageLabel = new JLabel();
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanelRow3.add(messageLabel);

        // Set up buttons and add to the buttonPanel
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
        buttonPanel.add(higherButton);
        buttonPanel.add(lowerButton);
        buttonPanel.add(nextGameButton);
        buttonPanel.add(exitButton);

        // Add buttonPanel to the frame's SOUTH position
        frame.add(buttonPanel, BorderLayout.SOUTH);

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

        exitButton.addActionListener(e -> System.exit(0));

        // Make frame visible
        frame.setVisible(true);
    }

    public void start(){
        highLowGame.startGame();
        Card currCard = highLowGame.getCurrCard();
        getScore();
        displayCard(currCard, centerPanelRow2);
        addHiddenCard();
    }

    public void getScore(){
        messageLabel.setText("Score: " + highLowGame.getScore());
    }

    // To display card in GUI
    public void displayCard(Card card, JPanel panel) {
        Image image = card.getImage();
        JLabel label = new JLabel(new ImageIcon(image));
        label.setLayout(new BorderLayout());
        panel.add(label);
        panel.revalidate();
        panel.repaint();
    }

    public void endGame(){
        higherButton.setVisible(false);
        lowerButton.setVisible(false);
        nextGameButton.setVisible(true);
        messageLabel.setText("Game Over! Your score was " + highLowGame.getScore() + "!");
    }

    public void addNextCard() {
        displayCard(highLowGame.getNextCard(), centerPanelRow2);
    }

    // Removes first element only when number of cards in panel > 5
    public void checkSize(){
        Component[] components = centerPanelRow2.getComponents();
        if (components.length > 5) {
            centerPanelRow2.remove(components[0]);
            centerPanelRow2.revalidate();
            centerPanelRow2.repaint();
        }
    }

    public void removeLastElement(){
        Component[] components = centerPanelRow2.getComponents();
        int lastIndex = components.length - 1;
        if (lastIndex >= 0) {
            centerPanelRow2.remove(components[lastIndex]);
            centerPanelRow2.revalidate();
            centerPanelRow2.repaint();
        }
    }

    public void addHiddenCard(){
        Card hiddenCard = new Card("b");
        displayCard(hiddenCard, centerPanelRow2);
    }

    public void updateCardPanel(boolean correctGuess){
        removeLastElement();
        addNextCard();
        if (correctGuess) {
            addHiddenCard();
        }
        checkSize();
    }

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
}
