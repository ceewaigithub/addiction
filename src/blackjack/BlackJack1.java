package blackjack;

import java.util.ArrayList;
import java.util.List;
import main.Deck;
import main.Player;
import main.Card;

public class BlackJack1 {
    private List<Player> players;
    private Deck deck;

    public BlackJack1() {
        players = new ArrayList<>();
        deck = new Deck();
    }

    // CHANGE: Adds players and a dealer to players list based on player quantity (excludes dealer)
    public void addPlayers(int playerQuantity) {

        // Add dealer
        Player dealer = new Player("Dealer");
        players.add(dealer);

        // Add player(s)
        for (int i = 1; i <= playerQuantity; i++) {
            
            Player player = new Player("Player" + i);
            players.add(player);
        }
    }

    // CHANGE: Returns list of players
    // This seems pretty redundant though...
    public List<Player> getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }

    public void startGame() {
        // Shuffle the deck
        deck.shuffle();

        // Deal initial cards to players
        for (Player player : players) {
            player.addCard(deck.dealCard());
            player.addCard(deck.dealCard());
        }
    }

    public ArrayList<Player> determineWinners() {
        ArrayList<Player> winners = new ArrayList<Player>();
        int maxHandSize = 0;
        boolean allBusted = true;

        for (Player player : players) {

            int handSizeWithoutAce = player.getHandValue();
            int handSizeWithAce = reducePlayerAce(player);

            if (handSizeWithoutAce > 21) {
                if (handSizeWithAce > 21) {
                    continue;
                } else {
                    handSizeWithoutAce = handSizeWithAce;
                }
            }

            // TODO - Check if any player has 7/7/7 or 5 Cards

            if (handSizeWithAce > maxHandSize) {
                maxHandSize = handSizeWithAce;
                winners.clear();
                winners.add(player);
                allBusted = false;
            } else if (handSizeWithAce == maxHandSize) {
                winners.add(player);
                allBusted = false;
            } else {
                continue;
            }

            if (handSizeWithoutAce <= 21) {
                allBusted = false;
            }
        }

        if (allBusted) {
            return null;
        }

        return winners;
    }

    public int reducePlayerAce(Player player) {
        int total = 0;
        for (Card card : player.getHand()) {
            if (card.isAce()) {
                total += 1;
            } else {
                total += card.getValue();
            }
        }
        return total;
    }

    public void revealAllHands() {
        for (Player player : players) {
            player.printHand();
        }
        System.out.println("");
    }

}
