package card;

import main.Player;
import java.util.*;

public abstract class CardGame implements ICardGame {
    protected List<Player> players = new ArrayList<>();

    @Override
    public void endRound() {
        for (Player player : players) {
            player.discardHand();
        }
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public List<Player> getPlayers(){
        return players;
    }
}
