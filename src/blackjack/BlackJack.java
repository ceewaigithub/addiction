package blackjack;

import java.util.ArrayList;
import java.util.List;
import main.Deck;
import main.Player;
import main.BettingSystem;
import main.Card;

public class BlackJack {

    // Variables
    private Deck deck;
    private List<Player> players;
    private boolean gameStatus = true;
    private BettingSystem bettingSystem;

    // Constructor
    public BlackJack(BettingSystem bettingSystem) {

        // Pass in betting system
        this.bettingSystem = bettingSystem;

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

    // Start game
    public void startGame() {
        // Shuffle the deck
        deck = new Deck();
        deck.shuffle();

        // Deal initial cards to players
        for (Player player : players) {
            player.addCard(deck.dealCard());
            player.addCard(deck.dealCard());
        }

        // Now, user will either hit or stay
    }

    // User hits
    public boolean hit() {
        // Deal card to player unless busted
        Card drawn_card = deck.dealCard();
        getPlayer().addCard(drawn_card);

        // If hand is more than or 21, return false to disable hit
        if (reducePlayerHand(getPlayer()) >= 21) {
            return false;
        }
        return true;
    }

    public int reducePlayerHand(Player player) {
        int currentHand = player.getHandValue();
        for (Card card : player.getHand()) {
            if (card.isAce() && currentHand > 21) {
                currentHand -= 10;
            }
        }
        return currentHand;
    }

    public void dealerTurn(boolean playerStays) {

        if (reducePlayerHand(getDealer()) < 17) {
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

        for (Player player : players) {

            int handSize = reducePlayerHand(player);

            if (handSize > 21) {
                continue;
            }

            if (handSize > maxHandSize) {
                maxHandSize = handSize;
                winners.clear();
                winners.add(player);
            } else if (handSize == maxHandSize) {
                winners.add(player);
            } else {
                continue;
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
            bettingSystem.pushBet();
        } else if (winners.size() == 1) {
            message = winners.get(0).getName() + " is the winner!";
            if (reducePlayerHand(winners.get(0)) == 21) {
                message = winners.get(0).getName() + " is the winner with Blackjack!";
            }

            if (playerWin) {
                if (reducePlayerHand(getPlayer()) == 21) {
                    bettingSystem.winBet(2);
                } else {
                    bettingSystem.winBet(1);
                }
            } else {
                bettingSystem.loseBet();
            }
        } else if (winners.size() > 1) {
            message = "It's a tie!";
            bettingSystem.pushBet();
        }

        return message;
    }

    public void endRound(){
        for (Player player : players) {
            player.discardHand();
        }
    }
    
}
