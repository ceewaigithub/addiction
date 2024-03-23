package blackjack;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import entity.User;
import main.Deck;
import main.Player;
import main.BettingSystem;
import main.Card;

public class BlackJack2 {

    // Variables
    private User user;
    private Deck deck;
    private List<Player> players;
    private boolean gameStatus = true;
    private BettingSystem bettingSystem;
    private boolean isWin;

    // Constructor
    public BlackJack2(User user, BettingSystem bettingSystem) {

        // Pass in user
        this.user = user;

        // Pass in betting system
        this.bettingSystem = bettingSystem;

        // Create deck
        deck = new Deck();

        // Create player list
        players = new ArrayList<>();

        // isWin is first false
        isWin = false;
    }

    // Add players
    public void addPlayer(Player player) {
        players.add(player);
    }

    // Basic getters
    public Deck getDeck() {
        return deck;
    }

    public Player getDealer() {
        return players.getFirst();
    }

    public Player getPlayer() {
        return players.getLast();
    }

    public boolean getGameStatus() {
        return gameStatus;
    }

    public BettingSystem getBettingSystem() {
        return bettingSystem;
    }

    // Start game
    public void startGame() {
        // Shuffle the deck
        deck.shuffle();

        // Deal initial cards to players
        for (Player player : players) {
            player.addCard(deck.dealCard());
            player.addCard(deck.dealCard());
        }

        // Now, user will either hit or stay
    }

    // User hits
    public void hit() {
        // Deal card to player
        Card drawn_card = deck.dealCard();
        getPlayer().addCard(drawn_card);
    }

    public void dealerTurn(boolean playerStays) {

        if (getDealer().getHandValue() < 17) {
            // Dealer hits
            Card drawn_card = getDeck().dealCard();
            getDealer().addCard(drawn_card);

            // Player's turn next
        } else {
            // Dealer stays

            // Either end game
            if (playerStays) {
                // If both dealer and player stays, game ends 
                gameStatus = false;
                // Winners are determined
                determineWinners();
            }

            // Or player's turn next
        }
    }

    public String determineWinners() {

        // Add winners into list
        ArrayList<Player> winners = new ArrayList<Player>();
        boolean playerWin = false;

        if (getDealer().getHandValue() > 21 && getPlayer().getHandValue() > 21) {
            winners = null;
        } else if (getDealer().getHandValue() > 21 && getPlayer().getHandValue() <= 21) {
            winners.add(getPlayer());
            playerWin = true;
        } else if (getDealer().getHandValue() <= 21 && getPlayer().getHandValue() > 21) {
            winners.add(getDealer());
        } else {
            if (getDealer().getHandValue() > getPlayer().getHandValue()) {
                winners.add(getDealer());
            } else if (getDealer().getHandValue() < getPlayer().getHandValue()) {
                winners.add(getPlayer());
                playerWin = true;
            } else {
                winners.add(getDealer());
                winners.add(getPlayer());
                playerWin = true;
            }
        }

        // Based on winners, return respective message & update betting system
        String message = "";

        if (winners == null) {
            message = "All players busted!";
            bettingSystem.pushBet();
        } else if (winners.size() == 1) {
            message = winners.get(0).getName() + " is the winner!";

            if (playerWin) {
                bettingSystem.winBet(1);
            } else {
                bettingSystem.loseBet();
            }
        } else if (winners.size() > 1) {
            message = "It's a tie!";
            bettingSystem.pushBet();
        }

        if (playerWin) {
            isWin = true;
        }

        return message;
    }

    public boolean getResult() {
        return isWin;
    }

}
