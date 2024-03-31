package cardsaraver;

import java.util.ArrayList;
import java.util.List;

import card.Deck;
import card.Player;
import main.BettingSystem;

public abstract class CardGame {
    
    private Deck deck;
    private List<Player> players;
    private BettingSystem bettingSystem;

    public CardGame(BettingSystem bettingSystem) {
        this.bettingSystem = bettingSystem;
        players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public abstract void startGame();

    public void endGame() {
        for (Player player : players) {
            player.discardHand();
        }
    }
}
