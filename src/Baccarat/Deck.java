package Baccarat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {

        // Initialize the deck with all the cards
        // Do not modify this code, most of the logic uses these specific strings
        String[] suits = {"c", "d", "h", "s"};
        String[] ranks = {"a", "2", "3", "4", "5", "6", "7", "8", "9",
                "t", "j", "q", "k"};
        cards = new ArrayList<>();

        // Initialize the deck with all the cards
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(suit, rank));
            }
        }

        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card dealCard() {
        if (cards.isEmpty()) {
            // Handle case when there are no more cards in the deck
            return null;
        }

        // KANBAN: Maybe we should use a queue instead of a list?
        return cards.remove(0);
    }


    public void printDeck() {
        System.out.print("Deck: [");
        for (int i = 0; i < cards.size(); i++) {
            System.out.print(cards.get(i));
            if (i != cards.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("] ");
        System.out.println("(Cards Remaining): " + cards.size());
    }
}
