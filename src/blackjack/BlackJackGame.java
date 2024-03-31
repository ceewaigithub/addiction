package blackjack;

import java.util.ArrayList;
import java.util.List;

import card.BettingSystem;
import card.Card;
import card.CardGame;
import card.Deck;
import card.Player;

public class BlackJackGame extends CardGame {

    // Variables
    private boolean gameStatus = true;

    /**
     * Constructor for BlackJackGame class.
     * 
     * @param bettingSystem The betting system used in the game.
     */
    public BlackJackGame(BettingSystem bettingSystem) {
        super(bettingSystem);
    }

    /**
     * Get the dealer player.
     * 
     * @return The dealer player.
     */
    public Player getDealer() {
        return players.getFirst();
    }

    /**
     * Get the player.
     * 
     * @return The player.
     */
    public Player getPlayer() {
        return players.getLast();
    }

    /**
     * Get the game status.
     * 
     * @return The game status.
     */
    public boolean getGameStatus() {
        return gameStatus;
    }

    /**
     * Set the game status.
     * 
     * @param gameStatus The game status to be set.
     */
    public void setGameStatus(boolean gameStatus) {
        this.gameStatus = gameStatus;
    }

    /**
     * Get the betting system used in the game.
     * 
     * @return The betting system.
     */
    public BettingSystem getBettingSystem() {
        return bettingSystem;
    }

    @Override
    // Start game
    public void startGame() {
        // Shuffle the deck
        deck = new Deck();
        // Deal initial cards to players
        for (Player player : players) {
            player.addCard(deck.dealCard());
            player.addCard(deck.dealCard());
        }

        // Now, user will either hit or stay
    }

    /**
     * Player hits.
     * 
     * Deal a card to the player and check if the player has busted or reached 21.
     * If so, it's the dealer's turn.
     */
    public void hit() {
        // Deal card
        Card drawn_card = deck.dealCard();
        getPlayer().addCard(drawn_card);

        // If player busted or 21, dealer's turn
        if (reducePlayerHand(getPlayer()) >= 21) {
            dealerTurn();
        }
    }

    /**
     * Player stays.
     * 
     * It's the dealer's turn.
     */
    public void stay() {
        dealerTurn();
    }

    /**
     * Reduce the player's hand value by 10 if the player has an Ace and the hand value is greater than 21.
     * 
     * @param player The player whose hand value needs to be reduced.
     * @return The reduced hand value.
     */
    public int reducePlayerHand(Player player) {
        int currentHand = player.getHandValue();
        for (Card card : player.getHand()) {
            if (card.isAce() && currentHand > 21) {
                currentHand -= 10;
            }
        }
        return currentHand;
    }

    /**
     * Dealer's turn.
     * 
     * The dealer hits until their hand value is at least 17.
     * Then, determine the winners of the game.
     */
    public void dealerTurn() {
        while (reducePlayerHand(getDealer()) < 17) {
            // Dealer hits
            Card drawn_card = deck.dealCard();
            getDealer().addCard(drawn_card);
        }

        determineWinners();
    }

    /**
     * Determine the winners of the game.
     * 
     * @return The message indicating the winners and the betting system updates.
     */
    public String determineWinners() {

        gameStatus = false;

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
    
}
