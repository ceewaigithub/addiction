package blackjack;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.random.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import entity.User;
import main.Card;
import main.Deck;
import main.Player;
import tile.Tile;

public class BlackJackApp1 extends JFrame{

    // GUI Initialisation
    int boardWidth = 800;
    int boardHeight = 540;
    int cardWidth = 73;
    int cardHeight = 97;
    static boolean win = false;
    private static JFrame previousFrame;

    public BlackJackApp1() {
        // Initialisation
        // TODO -- make the players into an arrayList so that we can iterate through it instead of just having two players
        BlackJack1 blackJack = new BlackJack1();

        // CHANGE: Adds players in BlackJack + copies a list of players + dealer
        // I think we should differentiate between players including dealer and excluding
        blackJack.addPlayers(1);
        List<Player> players = blackJack.getPlayers(); // Note: Iterate from 1 for players excluding dealer
        // Test to see if code runs
        Player player = players.get(1);
        Player dealer = players.get(0);

        blackJack.startGame();
        win = false;

        // Print all hands
        System.out.println("All hands:");
        blackJack.revealAllHands();
        this.previousFrame = previousFrame;
        runGame(blackJack, boardWidth, boardHeight, cardWidth, cardHeight, player, dealer, null);
    }

    public static void drawCardImage(Graphics g, Player player, int cardWidth, int cardHeight, int spacing, String currentDirectory, int x, int y, boolean back) {
        for (int i = 0; i < player.getHand().size(); i++) {
            String imagePath = currentDirectory + "/res/" + player.getHand().get(i).getImagePath();
            if (back) imagePath = currentDirectory + "/res/cards/b.gif";
            Image cardImg = new ImageIcon(imagePath).getImage();
            g.drawImage(cardImg, x + (i * (cardWidth + spacing)), y, cardWidth, cardHeight, null);
        }
    }

    public static void drawHands(Graphics g, BlackJack1 blackJack, Player player, Player dealer, int cardWidth, int cardHeight, JButton stayButton, int boardWidth, int boardHeight) {
        try {
            // Calculate the x-coordinate to center the cards horizontally
            int centerPlayerX = (boardWidth - (player.getHand().size() * cardWidth)) / 2;
            int centerDealerX = (boardWidth - (dealer.getHand().size() * cardWidth)) / 2;
            int spacing = 10; // Adjust the spacing value as per your preference
            String currentDirectory = new File("").getAbsolutePath();

            // Draw dealer's hands
            if (!stayButton.isEnabled()) {
                drawCardImage(g, dealer, cardWidth, cardHeight, spacing, currentDirectory, centerDealerX, 20, false);
            } else {
                drawCardImage(g, dealer, cardWidth, cardHeight, spacing, currentDirectory, centerDealerX, 20, true);
            }
            drawCardImage(g, player, cardWidth, cardHeight, spacing, currentDirectory, centerPlayerX, boardHeight - cardHeight - 80, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }

    public static void updateButtons(Graphics g, BlackJack1 blackJack, Player dealer, JButton stayButton, JButton exitButton, JPanel buttonPanel, int boardWidth, int boardHeight, User user) {
        if (!stayButton.isEnabled()) {
            while (dealer.getHandValue() < 17) {
                Card drawn_card = blackJack.getDeck().dealCard();
                dealer.addCard(drawn_card);
                System.out.println("All hands:");
                blackJack.revealAllHands();
            }

            ArrayList<Player> winners = blackJack.determineWinners();
            String message = "";

            if (winners == null) {
                message = "All players busted!";
                win = false;
            } else if (winners.size() == 1) {
                message = winners.get(0).getName() + " is the winner!";
                if (winners.get(0).getName().equals("Player")) {
                    win = true;
                }
            } else if (winners.size() > 1) {
                message = "It's a tie!";
                if (winners.get(0).getName().equals("Player")) {
                    win = true;
                }
            }

            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.setColor(Color.white);
            g.drawString(message, (boardWidth - g.getFontMetrics().stringWidth(message)) / 2, boardHeight / 2);

            // Update the button panel
            buttonPanel.removeAll();
            buttonPanel.add(exitButton);
        }
    }

    public static void runGame(BlackJack1 blackjack, int boardWidth, int boardHeight, int cardWidth, int cardHeight, Player player, Player dealer, User user) {

        JPanel buttonPanel = new JPanel();
        JButton hitButton = new JButton("Hit");
        JButton stayButton = new JButton("Stay");
        JButton exitButton = new JButton("Exit");

        JFrame frame = new JFrame("BlackJack");
        JPanel gamePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawHands(g, blackjack, player, dealer, cardWidth, cardHeight, stayButton, boardWidth, boardHeight);
                updateButtons(g, blackjack, dealer, stayButton, exitButton, buttonPanel, boardWidth, boardHeight, user);
                frame.revalidate();
                frame.repaint();
            }
        };

        frame.setSize(boardWidth, boardHeight);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Game panel painting
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(255, 90, 01));
        frame.add(gamePanel);

        // Update frame color immediately
        gamePanel.revalidate();
        gamePanel.repaint();

        // Button panel set up
        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        stayButton.setFocusable(false);
        buttonPanel.add(stayButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // hitbutton action listener
        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Card drawn_card = blackjack.getDeck().dealCard();
                player.addCard(drawn_card);
                gamePanel.repaint();
                System.out.println("All hands:");
                blackjack.revealAllHands();
                if (blackjack.reducePlayerAce(player) > 21) {
                    hitButton.setEnabled(false);
                }
            }
        });

        gamePanel.repaint();

        // staybutton action listener
        stayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);

                if (dealer.getHandValue() > 21) {
                    stayButton.setEnabled(false);
                }

                System.out.println("All hands:");
                blackjack.revealAllHands();
                gamePanel.repaint();
            }
        });
        // exitbutton action listener
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (user != null) {
                    if (win) {
                        user.money += 10;
                    } else {
                        user.money -= 10;
                    }
                    endGame(frame);
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // When the game is over, call this method
    public static void endGame(JFrame frame) {
        frame.setVisible(false); // Hide the Blackjack frame
        previousFrame.setVisible(true); // Show the previous frame
    }

    public static void main(String[] args) {
        BlackJackApp1 bj = new BlackJackApp1();
    }
}
