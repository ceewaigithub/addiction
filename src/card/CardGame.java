package card;

import java.util.*;

public abstract class CardGame implements ICardGame {
    protected List<Player> players = new ArrayList<>();
    protected Deck deck;
    protected BettingSystem bettingSystem;

    public CardGame(BettingSystem bettingSystem) {
        this.bettingSystem = bettingSystem;
    }

    @Override
    public void endRound() {
        for (Player player : players) {
            player.discardHand();
        }
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public List<Player> getPlayers() {
        return players;
    }
}
