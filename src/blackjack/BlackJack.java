package blackjack;

import java.util.ArrayList;
import java.util.List;

public class BlackJack {
    private List<Player> players;
    private Deck deck;

    public BlackJack() {
        players = new ArrayList<>();
        deck = new Deck();
    }

    public void addPlayer(Player player) {
        players.add(player);
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

    public Player determineWinner() {
        Player winner = null;
        int maxHandSize = 0;
        boolean allBusted = true;

        for (Player player : players) {
            int handSizeWithAce = reducePlayerAce(player);
            int handSizeWithoutAce = player.getHandValue();
            if (handSizeWithAce > 21) {
                if (handSizeWithoutAce > 21) {
                    continue;
                } else {
                    handSizeWithAce = handSizeWithoutAce;
                }
            }
            if (handSizeWithAce > maxHandSize) {
                maxHandSize = handSizeWithAce;
                winner = player;
                allBusted = false;
            }
            if (handSizeWithoutAce <= 21) {
                allBusted = false;
            }
        }

        if (allBusted) {
            return null;
        }

        return winner;
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
