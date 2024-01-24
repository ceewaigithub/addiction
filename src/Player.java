import java.util.*;

class Player {
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
        for (Card card : hand) {
            System.out.print(card + ", ");
        }
        System.out.println("] ");

        System.out.println(name + "'s Hand Value: " + getHandValue());
    }

    public void handSize() {
        System.out.println(name + "'s Hand Size: " + hand.size());
    }

    public int getHandValue() {
        int value = 0;
        for (Card card : hand) {
            value += card.getValue();
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