package main;

import java.util.*;

public class Player {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

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

    public void printHandSize() {
        System.out.println(name + "'s Hand Size: " + hand.size());
    }

    public List<Card> getHand() {
        return hand;
    }

    public int getHandValue() {
        int value = 0;
        int aceCount = 0;
        for (Card card : hand) {
            value += card.getValue();
            if (card.isAce()) {
                aceCount++;
            }
        }
        if (value > 21 && aceCount > 0) {
            // If the player has aces and the hand value is greater than 21,
            // then we can convert the value of the ace from 11 to 1
            // until the hand value is less than 21 or there are no more aces
            while (value > 21 && aceCount > 0) {
                value -= 10;
                aceCount--;
            }
        }
        return value;
    }

    public boolean hasBlackJack() {
        return getHandValue() == 21;
    }

    public boolean hasBusted() {
        return getHandValue() > 21;
    }

    public boolean wantsToHit() {
        // Implement player's decision logic
        if (getHandValue() < 17) {
            return true;
        } else {
            return false;
        }
    }

}