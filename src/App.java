import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.util.random.*;
import javax.swing.*;
import java.util.Scanner;

public class App {
    public static int reducePlayerAce(Player player) {
        int total = 0;
        for (Card card : player.getHand()) {
            if (card.isAce()) {
                total += 1;
            } else {
                total += card.getValue();
            }
        }
        return total;
    }

    public static void main(String[] args) {

        BlackJack blackJack = new BlackJack();
        Player player = new Player("Player");
        Player dealer = new Player("Dealer");

        blackJack.addPlayer(player);
        blackJack.addPlayer(dealer);

        blackJack.startGame();

        // print all hands
        System.out.println("All hands:");
        blackJack.revealAllHands();

        // Set up GUI
        int boardWidth = 800;
        int boardHeight = boardWidth;
        
        // card width and height based off of images provided
        int cardWidth = 73;
        int cardHeight = 97;

        JPanel buttonPanel = new JPanel();
        JButton hitButton = new JButton("Hit");
        JButton stayButton = new JButton("Stay");

        JFrame frame = new JFrame("BlackJack");
        JPanel gamePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                try {
                    // Calculate the x-coordinate to center the cards horizontally
                    int centerPlayerX = (getWidth() - (player.getHand().size() * cardWidth)) / 2;
                    int centerDealerX = (getWidth() - (dealer.getHand().size() * cardWidth)) / 2;
                    int spacing = 10; // Adjust the spacing value as per your preference

                    // Draw dealer's hand
                    if (!stayButton.isEnabled()) {
                        for (int i = 0; i < dealer.getHand().size(); i++) {
                            Image cardImg = new ImageIcon(getClass().getResource(dealer.getHand().get(i).getImagePath())).getImage();
                            g.drawImage(cardImg, centerDealerX + (i * (cardWidth + spacing)), 20, cardWidth, cardHeight, null);
                        }
                    } else {
                        for (int i = 0; i < dealer.getHand().size(); i++) {
                            Image hiddenCardImg = new ImageIcon(getClass().getResource("./cards/b.gif")).getImage();
                            g.drawImage(hiddenCardImg, centerDealerX + (i * (cardWidth + spacing)), 20, cardWidth, cardHeight, null);
                        }
                    }

                    // Draw player's hand
                    for (int i = 0; i < player.getHand().size(); i++) {
                        Image cardImg = new ImageIcon(getClass().getResource(player.getHand().get(i).getImagePath())).getImage();
                        g.drawImage(cardImg, centerPlayerX + (i * (cardWidth + spacing)), getHeight() - cardHeight - 20, cardWidth, cardHeight, null);
                    }

                    if (!stayButton.isEnabled()) {
                        int dealerSum = reducePlayerAce(dealer);
                        int playerSum = reducePlayerAce(player);
                        String message = "";

                        if (dealerSum > 21) {
                            message = "Dealer busts! You win!";
                        } else if (playerSum > 21) {
                            message = "You bust! Dealer wins!";
                        } else if (dealerSum > playerSum) {
                            message = "Dealer wins!";
                        } else if (playerSum > dealerSum) {
                            message = "You win!";
                        } else {
                            message = "It's a tie!";
                        }
                        g.setFont(new Font("Arial", Font.BOLD, 30));
                        g.setColor(Color.white);
                        g.drawString(message, (getWidth() - g.getFontMetrics().stringWidth(message)) / 2, getHeight() / 2);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        frame.setSize(boardWidth, boardHeight);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up game panel
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(255, 90, 01));
        frame.add(gamePanel);

        // Update frame color immediately
        gamePanel.revalidate();
        gamePanel.repaint();

        // Set up button panel
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
                if (reducePlayerAce(player) > 21) {
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

                while (dealer.getHandValue() < 17) {
                    Card drawn_card = blackJack.getDeck().dealCard();
                    dealer.addCard(drawn_card);
                    gamePanel.repaint();
                    System.out.println("All hands:");
                    blackJack.revealAllHands();
                }
                if (dealer.getHandValue() > 21) {
                    stayButton.setEnabled(false);
                }

                System.out.println("All hands:");
                blackJack.revealAllHands();
                gamePanel.repaint();
            }
        });

    }
}
