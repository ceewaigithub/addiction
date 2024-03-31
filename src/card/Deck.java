package card;

import java.util.*;

import exception.DeckEmptyException;

public class Deck {
    private List<Card> cards;
    
    public Deck() {
        cards = new ArrayList<>();
        initializeDeck();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public void initializeDeck() {
        // Initialize the deck with all the cards 
        // Do not modify this code, most of the logic uses these specific strings
        String[] suits = {"c", "d", "h", "s"};
        String[] ranks = {"a", "2", "3", "4", "5", "6", "7", "8", "9",
                          "t", "j", "q", "k"};
        
        // Initialize the deck with all the cards
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(suit, rank));
            }
        }

        shuffle();
    }

    public Card dealCard() throws DeckEmptyException {
        try {
            if (cards.isEmpty()) {
                throw new DeckEmptyException("Deck is empty");
            } else {
                return cards.remove(0);
            }
        } catch (DeckEmptyException e) {
            System.out.println(e.getMessage());
            // Reinitialize the deck
            initializeDeck();
            return cards.remove(0);
        }
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
