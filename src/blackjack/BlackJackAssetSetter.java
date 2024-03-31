package blackjack;

import java.awt.*;

import card.Card;
import card.Player;

public class BlackJackAssetSetter {
    
    // Variables
    private BlackJackGame blackjack; // Reference to the BlackJackGame instance
    private int boardWidth; // Width of the game board
    private int boardHeight; // Height of the game board
    private int cardWidth = 73; // Width of a card image
    private int cardHeight = 97; // Height of a card image
    private int spacing = 10; // Spacing between cards

    // Constructor
    /**
     * Constructs a BlackJackAssetSetter object.
     * 
     * @param blackjack   The BlackJackGame instance.
     * @param boardWidth  The width of the game board.
     * @param boardHeight The height of the game board.
     */
    public BlackJackAssetSetter(BlackJackGame blackjack, int boardWidth, int boardHeight) {
        // Pass in blackjack
        this.blackjack = blackjack;

        // Other dimensions needed
        this.boardWidth = boardWidth;
        this.boardHeight= boardHeight;
    }

    /**
     * Draws the hands of the dealer and player.
     * 
     * @param g The Graphics object used for drawing.
     */
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
    /**
     * Draws an individual card image.
     * 
     * @param g      The Graphics object used for drawing.
     * @param player The player whose card is being drawn.
     * @param x      The x-coordinate of the card's top-left corner.
     * @param y      The y-coordinate of the card's top-left corner.
     * @param back   Whether to draw the back of the card (true) or the front (false).
     */
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
