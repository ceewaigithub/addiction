package main;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> hand;

    private int money = 1000;


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

    public int getBalance(){
        return money;
    }

    public void setBalance(int toSet){
        money = toSet;
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

}