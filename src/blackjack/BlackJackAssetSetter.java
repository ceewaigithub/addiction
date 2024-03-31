package blackjack;

import java.awt.*;

import card.Card;
import card.Player;

public class BlackJackAssetSetter {
    
    // Variables
    private BlackJackGame blackjack;
    private int boardWidth;
    private int boardHeight;
    private int cardWidth = 73;
    private int cardHeight = 97;
    private int spacing = 10;

    // Constructor
    public BlackJackAssetSetter(BlackJackGame blackjack, int boardWidth, int boardHeight) {
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
            int dealerY = 40;
            if (blackjack.getGameStatus()) {
                drawCardImage(g, dealer, dealerX, dealerY, true);
            } else {
                drawCardImage(g, dealer, dealerX, dealerY, false);
            }

            // Draw player's hand
            int playerX = (boardWidth - (player.getHand().size() * cardWidth)) / 2;
            int playerY = boardHeight - cardHeight - 120;
            drawCardImage(g, player, playerX, playerY, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Draw individual cards
    public void drawCardImage(Graphics g, Player player, int x, int y, boolean back) {

        for (int i = 0; i < player.getHand().size(); i++) {
            
            Card card;
            if (back) {
                card = new Card("b");
            } else {
                card = player.getHand().get(i);
            }
            
            int cardX = x + (i * (cardWidth + spacing));
            int cardY = y;
            card.printCard(g, cardX, cardY);
        }
    }
}
