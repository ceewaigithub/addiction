package main;

import javax.imageio.ImageIO;

public class Card {
    private String suit;
    private String rank;    

    // Insert cardWidth and height

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    // Insert constructor 2

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

    public int getValue() {
        if (rank.equals("a")) {
            return 11;
        } else if (rank.equals("j") || rank.equals("q") || rank.equals("k") || rank.equals("t")) {
            return 10;
        } else {
            return Integer.parseInt(rank);
        }
    }

    // Add into Adrian's
    public boolean isAce() {
        return rank.equals("a");
    }
    
    // Replace with getImage
    public String getImagePath() {
        return "./cards/" + rank + suit + ".gif";
    }

    // Add Print card, Get card width, Get card height
    @Override
    public String toString() {
        return rank + "" + suit;
    }
}