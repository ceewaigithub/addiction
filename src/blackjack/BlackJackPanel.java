package blackjack;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import main.Card;
import main.Player;

public class BlackJackPanel extends JPanel {

    private JFrame previousFrame;

    private final int screenWidth = 768;
    private final int screenHeight = 576;

    private final int cardWidth = 73;
    private final int cardHeight = 97;
    private final int spacing = 10;

    private BlackJack2 blackjack;
    private Player player;
    private Player dealer;

    private JButton hitButton;
    private JButton stayButton;
    private JButton exitButton;

    private boolean win;

    public BlackJackPanel(JFrame previousFrame, BlackJack2 blackjack, Player player, Player dealer) {
        this.previousFrame = previousFrame;
        this.blackjack = blackjack;
        this.player = player;
        this.dealer = dealer;
        this.win = false;

        initGame();

        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Card drawnCard = blackjack.getDeck().dealCard();
                player.addCard(drawnCard);
                repaint();
                System.out.println("All hands:");
                blackjack.revealAllHands();
                if (blackjack.reducePlayerAce(player) > 21) {
                    hitButton.setEnabled(false);
                }
            }
        });

        stayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);

                while (dealer.getHandValue() < 17) {
                    Card drawnCard = blackjack.getDeck().dealCard();
                    dealer.addCard(drawnCard);
                    System.out.println("All hands:");
                    blackjack.revealAllHands();
                }

                displayEndGameMessage(blackjack.determineWinners());
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endGame();
            }
        });

        setLayout(new BorderLayout());
        add(hitButton, BorderLayout.WEST);
        add(stayButton, BorderLayout.CENTER);
        add(exitButton, BorderLayout.EAST);
    }

    private void initGame() {
        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");
        exitButton = new JButton("Exit");

        hitButton.setFocusable(false);
        stayButton.setFocusable(false);
        exitButton.setFocusable(false);

        hitButton.setPreferredSize(new Dimension(100, 40));  // Adjust the dimensions as needed
        stayButton.setPreferredSize(new Dimension(100, 40));
        exitButton.setPreferredSize(new Dimension(100, 40));
    }

    public BlackJack2 getBlackjack() {
        return blackjack;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getDealer() {
        return dealer;
    }

    public void drawHands(Graphics g) {
        int centerPlayerX = (screenWidth - (player.getHand().size() * cardWidth)) / 2;
        int centerDealerX = (screenWidth - (dealer.getHand().size() * cardWidth)) / 2;

        // Draw dealer's hands
        drawCardImage(g, dealer, cardWidth, cardHeight, spacing, centerDealerX, 20, !stayButton.isEnabled());

        // Draw player's hands
        drawCardImage(g, player, cardWidth, cardHeight, spacing, centerPlayerX, screenHeight - cardHeight - 80, false);
    }

    public void drawCardImage(Graphics g, Player player, int cardWidth, int cardHeight, int spacing, int x, int y, boolean back) {
        for (int i = 0; i < player.getHand().size(); i++) {
            String imagePath = "res/" + player.getHand().get(i).getImagePath(); // Assuming the images are in the "res" folder
            if (back) {
                imagePath = "res/cards/b.gif";
            }
            Image cardImg = new ImageIcon(imagePath).getImage();
            g.drawImage(cardImg, x + (i * (cardWidth + spacing)), y, cardWidth, cardHeight, null);
        }
    }

    public void updateButtons(Graphics g) {
        if (!stayButton.isEnabled()) {
            while (dealer.getHandValue() < 17) {
                Card drawnCard = blackjack.getDeck().dealCard();
                dealer.addCard(drawnCard);
                System.out.println("All hands:");
                blackjack.revealAllHands();
            }

            ArrayList<Player> winners = blackjack.determineWinners();
            String message = determineWinMessage(winners);

            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.setColor(Color.white);
            g.drawString(message, (screenWidth - g.getFontMetrics().stringWidth(message)) / 2, screenHeight / 2);

            // Update the button panel
            removeAll();
            add(exitButton);
            repaint();
        }
    }

    public void displayEndGameMessage(ArrayList<Player> winners) {
        String message = determineWinMessage(winners);
        Graphics g = getGraphics();
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.white);
        g.drawString(message, (screenWidth - g.getFontMetrics().stringWidth(message)) / 2, screenHeight / 2);
    }

    public void endGame() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setVisible(false); // Hide the Blackjack frame
        if (previousFrame != null) {
            previousFrame.setVisible(true); // Show the previous frame
        } else {
            System.exit(0); // Close the program
        }
    }

    public JButton getHitButton() {
        return hitButton;
    }

    public JButton getStayButton() {
        return stayButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    private String determineWinMessage(ArrayList<Player> winners) {
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

        return message;
    }
}
