package card;

import main.Player;
import java.util.*;

public abstract class CardGame implements ICardGame {
    private List<Player> players = new ArrayList<>();
    
    @Override
    public void startGame() {
        // Common startGame logic
    }

    @Override
    public void endRound() {
        for (Player player : players) {
            player.discardHand();
        }
    }
}
