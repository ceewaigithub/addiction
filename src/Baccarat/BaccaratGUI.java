package Baccarat;

import main.BettingGUI;
import main.BettingSystem;
import main.Player;
import main.Card;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BaccaratGUI {
    private JFrame frame;
    private BaccaratGame BaccaratGame;

    private BettingSystem bettingSystem;
    private JPanel gamePanel, buttonPanel, controlPanel, topPanel, bottomPanel, bettingPanel;
    private JLabel messageLabel, topLabel, bottomLabel;
    private JButton hitButton, standButton, exitButton, nextGameButton;
    private List<Player> players;
    public BaccaratGUI(BaccaratGame baccaratGame, BettingSystem bettingSystem) {

        this.bettingSystem = bettingSystem;
        bettingPanel = bettingSystem.getBettingPanel();
        BaccaratGame = baccaratGame;
        frame = new JFrame("Baccarat");
        int boardWidth = 800;
        int boardHeight = 540;

        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up gamePanel
        gamePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Paint game panel
                players = BaccaratGame.getPlayers();
                int centerPlayerX = (getWidth() - (players.getFirst().getHand().size() * Card.getCardWidth())) / 2;
                int centerDealerX = (getWidth() - (players.getLast().getHand().size() * Card.getCardWidth())) / 2;
                int spacing = 10;

                Card hiddenCard = new Card("b");

                //Loop through players
                for (Player player : players) {
                    int startY = player.isDealer() ? 40 : getHeight() - Card.getCardHeight() - 40;
                    int centerX = player.isDealer() ? centerDealerX : centerPlayerX;

                    for (int i = 0; i < player.getHand().size(); i++) {

                        int xPos = centerX + (i * (Card.getCardWidth() + spacing));
                        player.getHand().get(i).printCard(g, xPos, startY);

                        if (player.isDealer() && i == 0 && BaccaratGame.getPlayerTurn() == 0) {
                            // Print hidden card when it is the player's turn
                            hiddenCard.printCard(g, xPos, startY);
                        }
                    }
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

        // Set up buttons and add to the buttonPanel
        hitButton = new JButton("Hit");
        hitButton.setFocusable(false);
        standButton = new JButton("Stand");
        standButton.setFocusable(false);
        nextGameButton = new JButton("Next Game");
        nextGameButton.setFocusable(false);
        nextGameButton.setVisible(false);
        exitButton = new JButton("Exit");
        exitButton.setFocusable(false);

        // Add buttons to the buttonPanel
        buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(nextGameButton);
        buttonPanel.add(exitButton);

        // Add buttonPanel to the frame's SOUTH position
        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel,BoxLayout.Y_AXIS));
        controlPanel.add(bettingPanel);
        controlPanel.add(buttonPanel);


        frame.add(controlPanel, BorderLayout.SOUTH);

        // Add gamePanel to the frame
        frame.add(gamePanel);

        // Add action listeners
        hitButton.addActionListener(e -> {
            BaccaratGame.hit();
            dealerTurn();
            gamePanel.repaint();
        });

        standButton.addActionListener(e -> {
            BaccaratGame.stand();
            dealerTurn();
            gamePanel.repaint();
        });

        nextGameButton.addActionListener(e -> {
            restartGame();
            gamePanel.repaint();
        });

        bettingSystem.getPlaceBetButton().addActionListener(e->{
            startRound();
        });

        exitButton.addActionListener(e -> System.exit(0));




        frame.setVisible(true);
    }

    public void start() {
        showBettingControl();
        messageLabel.setText("Place your bet");
    }

    public void startRound(){
        hideBettingControl();
        messageLabel.setText("");
        BaccaratGame.startGame();
        checkWinner(true);
    }


    public void dealerTurn(){
        disableButtons();
        BaccaratGame.dealerTurn();
        checkWinner(false);
    }

    public void disableButtons(){
        hitButton.setVisible(false);
        standButton.setVisible(false);
        nextGameButton.setVisible(true);
    }

    public void hideBettingControl(){
        bettingPanel.setVisible(false);
        buttonPanel.setVisible(true);
    }

    public void showBettingControl(){
        bettingSystem.updateBettingPanel();
        bettingPanel.setVisible(true);
        buttonPanel.setVisible(false);
    }

    public void restartGame(){
        messageLabel.setText("");
        hitButton.setVisible(true);
        standButton.setVisible(true);
        nextGameButton.setVisible(false);
        BaccaratGame.endRound();
        start();
    }

    public void checkWinner(boolean natural){
        if(!BaccaratGame.compareScore(natural).isEmpty()){
            announceWinner(BaccaratGame.compareScore(natural));
            disableButtons();
        }
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    public void announceWinner(String win){
        if(win != "Push"){
            setMessage(win + " Won!");
        } else {
            setMessage("It's a Push!");
        }
    }
}
