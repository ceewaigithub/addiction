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

public class BlackJackApp2 extends JFrame{

    // GUI Initialisation
    static boolean win = false;
    private static JFrame previousFrame;

    public BlackJackApp2() {
        // Initialisation
        BlackJack2 blackJack = new BlackJack2();
        Player player = new Player("Player");
        Player dealer = new Player("Dealer");
        blackJack.addPlayer(player);
        blackJack.addPlayer(dealer);
        blackJack.startGame();
        win = false;

        // Print all hands
        System.out.println("All hands:");
        blackJack.revealAllHands();

        BlackJackPanel blackjackPanel = new BlackJackPanel(this, blackJack, player, dealer);
        setupButtons(blackjackPanel, blackJack, player, dealer);

        JFrame frame = new JFrame("BlackJack");
        frame.setSize(blackjackPanel.getScreenWidth(), blackjackPanel.getScreenHeight());
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(blackjackPanel);
        
        runGame(blackjackPanel);
    }

    // public BlackJackApp2(User user, JFrame previousFrame) {
    //     // Initialization
    //     BlackJack2 blackJack = new BlackJack2();
    //     Player player = new Player("Player");
    //     Player dealer = new Player("Dealer");
    //     blackJack.addPlayer(player);
    //     blackJack.addPlayer(dealer);
    //     blackJack.startGame();
    //     win = false;

    //     // Print all hands
    //     System.out.println("All hands:");
    //     blackJack.revealAllHands();
    //     this.previousFrame = previousFrame;

    //     BlackJackPanel blackjackPanel = new BlackJackPanel(this, blackJack, player, dealer);
    //     setupButtons(blackjackPanel, blackJack, player, dealer);

    //     JFrame frame = new JFrame("BlackJack");
    //     frame.setSize(blackjackPanel.getScreenWidth(), blackjackPanel.getScreenHeight());
    //     frame.setVisible(true);
    //     frame.setLocationRelativeTo(null);
    //     frame.setResizable(false);
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     frame.add(blackjackPanel);

    //     runGame(blackjackPanel, user);
    // }

    private static void runGame(BlackJackPanel blackjackPanel) {
        blackjackPanel.getHitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Card drawnCard = blackjackPanel.getBlackjack().getDeck().dealCard();
                blackjackPanel.getPlayer().addCard(drawnCard);
                blackjackPanel.repaint();
                System.out.println("All hands:");
                blackjackPanel.getBlackjack().revealAllHands();
                if (blackjackPanel.getBlackjack().reducePlayerAce(blackjackPanel.getPlayer()) > 21) {
                    blackjackPanel.getHitButton().setEnabled(false);
                }
            }
        });

        blackjackPanel.getStayButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blackjackPanel.getHitButton().setEnabled(false);
                blackjackPanel.getStayButton().setEnabled(false);

                while (blackjackPanel.getDealer().getHandValue() < 17) {
                    Card drawnCard = blackjackPanel.getBlackjack().getDeck().dealCard();
                    blackjackPanel.getDealer().addCard(drawnCard);
                    System.out.println("All hands:");
                    blackjackPanel.getBlackjack().revealAllHands();
                }

                blackjackPanel.displayEndGameMessage(blackjackPanel.getBlackjack().determineWinners());
            }
        });

        blackjackPanel.getExitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blackjackPanel.endGame();
            }
        });
    }

    private static void setupButtons(BlackJackPanel blackjackPanel, BlackJack2 blackjack, Player player, Player dealer) {
        blackjackPanel.getHitButton().setFocusable(false);
        blackjackPanel.getStayButton().setFocusable(false);
        blackjackPanel.getExitButton().setFocusable(false);
    }

    public static void main(String[] args) {
        BlackJackApp2 bj = new BlackJackApp2();
    }
}
