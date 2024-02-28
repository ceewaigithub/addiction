package blackjack;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.random.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import main.Card;
import main.Deck;
import main.Player;
import tile.Tile;

public class BlackJackApp2 {

    public static void drawCardImage(Graphics g, Player player, int cardWidth, int cardHeight, int spacing, String currentDirectory, int x, int y, boolean back) {
        for (int i = 0; i < player.getHand().size(); i++) {
            String imagePath = currentDirectory + "/res/" + player.getHand().get(i).getImagePath();
            if (back) imagePath = currentDirectory + "/res/cards/b.gif";
            Image cardImg = new ImageIcon(imagePath).getImage();
            g.drawImage(cardImg, x + (i * (cardWidth + spacing)), y, cardWidth, cardHeight, null);
        }
    }

    public static void drawHands(Graphics g, BlackJack blackJack, Player player, Player dealer, int cardWidth, int cardHeight, JButton stayButton, int boardWidth, int boardHeight) {
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
            drawCardImage(g, player, cardWidth, cardHeight, spacing, currentDirectory, centerPlayerX, boardHeight - cardHeight - 20, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }

    public static void updateButtons(Graphics g, BlackJack blackJack, Player dealer, JButton stayButton, JButton exitButton, JPanel buttonPanel, int boardWidth, int boardHeight) {
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
            } else if (winners.size() == 1) {
                message = winners.get(0).getName() + " is the winner!";
            } else if (winners.size() > 1) {
                message = "It's a tie!";
            }

            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.setColor(Color.white);
            g.drawString(message, (boardWidth - g.getFontMetrics().stringWidth(message)) / 2, boardHeight / 2);

            // Update the button panel
            buttonPanel.removeAll();
            buttonPanel.add(exitButton);
        }
    }

    public static void main(String[] args) {

        // Initialisation
        BlackJack blackJack = new BlackJack();
        Player player = new Player("Player");
        Player dealer = new Player("Dealer");

        blackJack.addPlayer(player);
        blackJack.addPlayer(dealer);
        blackJack.startGame();

        // Print all hands
        System.out.println("All hands:");
        blackJack.revealAllHands();

        // GUI Initialisation
        int boardWidth = 800;
        int boardHeight = 540;
        int cardWidth = 73;
        int cardHeight = 97;
        
        JPanel buttonPanel = new JPanel();
        JButton hitButton = new JButton("Hit");
        JButton stayButton = new JButton("Stay");
        JButton exitButton = new JButton("Exit");

        JFrame frame = new JFrame("BlackJack");
        JPanel gamePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawHands(g, blackJack, player, dealer, cardWidth, cardHeight, stayButton, boardWidth, boardHeight);
                updateButtons(g, blackJack, dealer, stayButton, exitButton, buttonPanel, boardWidth, boardHeight);
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
                Card drawn_card = blackJack.getDeck().dealCard();
                player.addCard(drawn_card);
                gamePanel.repaint();
                System.out.println("All hands:");
                blackJack.revealAllHands();
                if (blackJack.reducePlayerAce(player) > 21) {
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
                blackJack.revealAllHands();
                gamePanel.repaint();
            }
        });

        // exitbutton action listener
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }
}
