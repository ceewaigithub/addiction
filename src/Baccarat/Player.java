package Baccarat;

import java.util.ArrayList;
import java.util.List;

class Player {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public int getHandValue() {
        int value = 0;
        for (Card card : hand) {
            value += card.getValue();
        }
        return value;
    }

    public void discardHand() {
        hand.clear();
    }

    public boolean isDealer(){
        if(name == "Dealer"){
            return true;
        }
        return false;
    }

}