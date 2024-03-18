package blackjack;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;

import entity.User;
import main.Card;
import main.Player;

public class BlackJackAssetSetter {
    
    // Variables
    private BlackJack2 blackjack;
    private int boardWidth;
    private int boardHeight;
    private int cardWidth = 73;
    private int cardHeight = 97;
    private int spacing = 10;

    // Constructor
    public BlackJackAssetSetter(BlackJack2 blackjack, int boardWidth, int boardHeight) {
        // Pass in blackjack
        this.blackjack = blackjack;

        // Other dimensions needed
        this.boardWidth = boardWidth;
        this.boardHeight= boardHeight;
    }

    public void drawHands(Graphics g) {
        try {
            // Get dealer and player
            Player dealer = blackjack.getDealer();
            Player player = blackjack.getPlayer();

            // Draw dealer's hand (back if ongoing/front if over)
            int dealerX = (boardWidth - (dealer.getHand().size() * cardWidth)) / 2;
            int dealerY = 20;
            if (blackjack.getGameStatus()) {
                drawCardImage(g, dealer, dealerX, dealerY, true);
            } else {
                drawCardImage(g, dealer, dealerX, dealerY, false);
            }

            // Draw player's hand
            int playerX = (boardWidth - (player.getHand().size() * cardWidth)) / 2;
            int playerY = boardHeight - cardHeight - 80;
            drawCardImage(g, player, playerX, playerY, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Draw individual cards
    public void drawCardImage(Graphics g, Player player, int x, int y, boolean back) {

        String currentDirectory = new File("").getAbsolutePath();

        for (int i = 0; i < player.getHand().size(); i++) {
            String imagePath = currentDirectory + "/res/" + player.getHand().get(i).getImagePath();
            if (back) imagePath = currentDirectory + "/res/cards/b.gif";
            Image cardImg = new ImageIcon(imagePath).getImage();
            g.drawImage(cardImg, x + (i * (cardWidth + spacing)), y, cardWidth, cardHeight, null);
        }
    }
}