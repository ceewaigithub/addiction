import java.util.*;

class Card {
    private String suit;
    private String rank;    

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    // Getters and setters
    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    @Override
    public String toString() {
        return rank + "" + suit;
    }
}