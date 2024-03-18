import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BaccaratGUI {
    private JFrame frame;
    private BaccaratGame BaccaratGame;
    private JPanel gamePanel, buttonPanel, topPanel, bottomPanel, controlPanel;
    private JLabel messageLabel, topLabel, bottomLabel;
    private JButton hitButton, standButton, exitButton, nextGameButton;
    private List<Player> players;
    private BettingSystem bettingSystem;
    private BettingGUI bettingGUI;
    public BaccaratGUI(BaccaratGame baccaratGame) {
        BaccaratGame = baccaratGame;
        Player player = new Player("Player");
        Player dealer = new Player("Dealer");
        BaccaratGame.addPlayer(player);
        BaccaratGame.addPlayer(dealer);
        BaccaratGame.startGame();
        bettingSystem = new BettingSystem((player));
        bettingGUI = new BettingGUI(bettingSystem);
        frame = new JFrame("Baccarat");
        gamePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Paint game panel
                players = BaccaratGame.getPlayers();
                int centerPlayerX = (getWidth() - (player.getHand().size() * Card.getCardWidth())) / 2;
                int centerDealerX = (getWidth() - (dealer.getHand().size() * Card.getCardWidth())) / 2;
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
                            hiddenCard.printCard(g, xPos, startY);
                        }
                    }
                }
            }
        };
        buttonPanel = new JPanel();
        controlPanel = new JPanel();
    }

    public void start() {
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
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(nextGameButton);
        buttonPanel.add(exitButton);
//        buttonPanel.add(bettingGUI.getBettingPanel(), BorderLayout.NORTH);
        // Add buttonPanel to the frame's SOUTH position
//        frame.add(buttonPanel, BorderLayout.SOUTH);
        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);
        controlPanel.add(bettingGUI.getBettingPanel(), BorderLayout.NORTH);
        // Add gamePanel to the frame
        frame.add(controlPanel, BorderLayout.SOUTH);

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

        exitButton.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
        checkWinner();
    }

    public void dealerTurn(){
        BaccaratGame.dealerTurn();
        disableButtons();
    }

    public void disableButtons(){
        hitButton.setVisible(false);
        standButton.setVisible(false);
        nextGameButton.setVisible(true);
    }

    public void restartGame(){
        messageLabel.setText("");
        hitButton.setVisible(true);
        standButton.setVisible(true);
        BaccaratGame.startGame();
        nextGameButton.setVisible(false);
        checkWinner();
    }

    public void checkWinner(){
        if(BaccaratGame.checkNaturals() != "None"){
            announceWinner(BaccaratGame.checkNaturals());
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
