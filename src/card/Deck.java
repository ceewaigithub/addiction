package card;

import java.util.*;

import exception.DeckEmptyException;

public class Deck {
    private List<Card> cards;
    
    /**
     * Constructs a new Deck object.
     * Initializes the deck with a standard set of 52 playing cards.
     */
    public Deck() {
        cards = new ArrayList<>();
        initializeDeck();
    }

    /**
     * Shuffles the deck of cards.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Initializes the deck with all the cards.
     * Do not modify this code, most of the logic uses these specific strings.
     */
    public void initializeDeck() {
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

    /**
     * Deals a card from the deck.
     * If the deck is empty, reinitializes the deck and deals a card.
     * @return The dealt card.
     * @throws DeckEmptyException if the deck is empty and cannot be reinitialized.
     */
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

    /**
     * Prints the deck of cards.
     * Displays the cards in the format: [card1, card2, ..., cardN]
     * Also displays the number of cards remaining in the deck.
     */
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
