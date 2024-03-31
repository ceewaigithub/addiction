package card;

import java.util.ArrayList;
import java.util.List;

/**
 * The Player class represents a player in a card game.
 */
public class Player {
    private String name;
    private List<Card> hand;
    private int money = 1000;

    /**
     * Constructs a Player object with the specified name.
     *
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    /**
     * Returns the name of the player.
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the hand of the player.
     *
     * @return the hand of the player
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Adds a card to the player's hand.
     *
     * @param card the card to be added
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Calculates and returns the value of the player's hand.
     *
     * @return the value of the player's hand
     */
    public int getHandValue() {
        int value = 0;
        for (Card card : hand) {
            value += card.getValue();
        }
        return value;
    }

    /**
     * Discards all cards from the player's hand.
     */
    public void discardHand() {
        hand.clear();
    }

    /**
     * Checks if the player is a dealer.
     *
     * @return true if the player is a dealer, false otherwise
     */
    public boolean isDealer() {
        return name.equals("Dealer");
    }

    /**
     * Returns the balance (money) of the player.
     *
     * @return the balance of the player
     */
    public int getBalance() {
        return money;
    }

    /**
     * Sets the balance (money) of the player.
     *
     * @param toSet the balance to be set
     */
    public void setBalance(int toSet) {
        money = toSet;
    }

    /**
     * Prints the player's hand.
     */
    public void printHand() {
        System.out.print(name + "'s Hand: [");
        for (int i = 0; i < hand.size(); i++) {
            System.out.print(hand.get(i));
            if (i < hand.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("] ");

        System.out.println(name + "'s Hand Value: " + getHandValue());
    }

    /**
     * Prints the size of the player's hand.
     */
    public void printHandSize() {
        System.out.println(name + "'s Hand Size: " + hand.size());
    }
}