package card;

import java.util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The abstract class representing a card game.
 * It implements the {@link ICardGame} interface.
 */
public abstract class CardGame implements ICardGame {
    protected List<Player> players = new ArrayList<>(); // List of players in the game
    protected Deck deck; // The deck of cards used in the game
    protected BettingSystem bettingSystem; // The betting system used in the game

    /**
     * Constructs a CardGame object with the specified betting system.
     *
     * @param bettingSystem the betting system used in the game
     */
    public CardGame(BettingSystem bettingSystem) {
        this.bettingSystem = bettingSystem;
    }

    /**
     * Ends the current round of the game.
     * Discards the hand of each player.
     */
    @Override
    public void endRound() {
        for (Player player : players) {
            player.discardHand();
        }
    }

    /**
     * Adds a player to the game.
     *
     * @param player the player to be added
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Returns the list of players in the game.
     *
     * @return the list of players
     */
    public List<Player> getPlayers() {
        return players;
    }
}
