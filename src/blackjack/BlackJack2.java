package blackjack;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import entity.User;
import main.Deck;
import main.Player;
import main.Card;

public class BlackJack2 {

    // Variables
    private User user;
    private Deck deck;
    private Player dealer;
    private Player player;
    private boolean gameStatus = true;

    // Constructor
    public BlackJack2(User user) {

        // Pass in user
        this.user = user;

        // Create deck
        deck = new Deck();

        // Create dealer & player
        dealer = new Player("dealer");
        player = new Player("player");
    }

    // Basic getters
    public Deck getDeck() {
        return deck;
    }

    public Player getDealer() {
        return dealer;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean getGameStatus() {
        return gameStatus;
    }

    // Start game
    public void startGame() {
        // Shuffle the deck
        deck.shuffle();

        // Deal initial cards to players
        dealer.addCard(deck.dealCard());
        player.addCard(deck.dealCard());

        // Now, user will either hit or stay
    }

    // User hits
    public void hit() {
        // Deal card to player
        Card drawn_card = deck.dealCard();
        player.addCard(drawn_card);
        
        // Dealer's turn
        dealerTurn(false);
    }

    // User stays
    public void stay() {
        // No change to player

        // Dealer's turn
        dealerTurn(true);
    }

    public void dealerTurn(boolean playerStays) {

        if (dealer.getHandValue() < 17) {
            // Dealer hits
            Card drawn_card = getDeck().dealCard();
            dealer.addCard(drawn_card);

            // Player's turn next
        } else {
            // Dealer stays

            // Either end game
            if (playerStays) {
                // If both dealer and player stays, game ends 
                gameStatus = false;
                // Winners are determined
                determineWinners();
                // Update panel with results & exit button
            }

            // Or player's turn next
        }
    }

    public String determineWinners() {

        // Add winners into list
        ArrayList<Player> winners = new ArrayList<Player>();
        boolean playerWin = false;

        if (dealer.getHandValue() > 21 && player.getHandValue() > 21) {
            winners = null;
        } else if (dealer.getHandValue() > 21 && player.getHandValue() <= 21) {
            winners.add(player);
            playerWin = true;
        } else if (dealer.getHandValue() <= 21 && player.getHandValue() > 21) {
            winners.add(dealer);
        } else {
            if (dealer.getHandValue() > player.getHandValue()) {
                winners.add(dealer);
            } else if (dealer.getHandValue() < player.getHandValue()) {
                winners.add(player);
                playerWin = true;
            } else {
                winners.add(dealer);
                winners.add(player);
                playerWin = true;
            }
        }

        // Update user bank
        updateUserBank(playerWin);

        // Based on winners, return respective message
        String message = "";

        if (winners == null) {
            message = "All players busted!";
        } else if (winners.size() == 1) {
            message = winners.get(0).getName() + " is the winner!";
        } else if (winners.size() > 1) {
            message = "It's a tie!";
        }

        return message;
    }

    public void updateUserBank(boolean playerWin) {
        // Update user bank based on game result
        if (playerWin) {
            user.money += 10;
        } else {
            user.money -= 10;
        }
    }

}
