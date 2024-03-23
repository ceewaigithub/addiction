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
    private int isWin;

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

    public void setGameStatus(boolean gameStatus) {
        this.gameStatus = gameStatus;
    }

    public BettingSystem getBettingSystem() {
        return bettingSystem;
    }

    public int getisWin() {
        // Returns -1 if lost, 0 if tied, and 1 if won
        return isWin;
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

    public int reducePlayerHand(Player player) {
        int currentHand = player.getHandValue();
        int reducedHand = 0;
        for (Card card : player.getHand()) {
            if (card.isAce() && currentHand > 21) {
                reducedHand += 1;
                currentHand -= 10;
            } else {
                reducedHand += card.getValue();
            }
        }
        return reducedHand;
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

        ArrayList<Player> winners = new ArrayList<Player>();
        int maxHandSize = 0;
        boolean allBusted = true;

        for (Player player : players) {

            int handSize = player.getHandValue();

            // Hand size with ace will be reduced to ideal hand
            while (handSize > 21) {
                handSize = reducePlayerHand(player);
            }

            if (handSize > maxHandSize) {
                maxHandSize = handSize;
                winners.clear();
                winners.add(player);
                allBusted = false;
            } else if (handSize == maxHandSize) {
                winners.add(player);
                allBusted = false;
            } else {
                continue;
            }

            if (handSize <= 21) {
                allBusted = false;
            }
        }

        // Update playerWin
        boolean playerWin = false;
        for (Player winner : winners) {
            if (getPlayer().equals(winner)) {
                playerWin = true;
            }
        }
        
        // Based on winners, return respective message & update betting system
        String message = "";

        if (winners.isEmpty()) {
            message = "All players busted!";
            bettingSystem.loseBet();
            isWin = -1;
        } else if (winners.size() == 1) {
            message = winners.get(0).getName() + " is the winner!";

            if (playerWin) {
                bettingSystem.winBet(2);
                isWin = 1;
            } else {
                bettingSystem.loseBet();
                isWin = -1;
            }
        } else if (winners.size() > 1) {
            message = "It's a tie!";
            bettingSystem.pushBet();
            isWin = 0;
        }

        return message;
    }

    public void endRound(){
        for (Player player : players) {
            player.discardHand();
        }
    }
    
}
