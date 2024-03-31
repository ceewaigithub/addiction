package card;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a playing card.
 */
public class Card implements Comparable<Card>{
    private String suit; // The suit of the card
    private String rank; // The rank of the card

    static final int cardWidth = 73; // The width of the card image
    static final int cardHeight = 97; // The height of the card image

    /**
     * Constructs a Card object with the specified suit and rank.
     * @param suit the suit of the card
     * @param rank the rank of the card
     */
    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Constructs a Card object with the specified suit and an empty rank.
     * @param suit the suit of the card
     */
    public Card(String suit) {
        this.suit = suit;
        this.rank = "";
    }

    // Getters and setters

    /**
     * Returns the rank of the card.
     * @return the rank of the card
     */
    public String getRank() {
        return rank;
    }

    /**
     * Returns the suit of the card.
     * @return the suit of the card
     */
    public String getSuit() {
        return suit;
    }

    /**
     * Sets the rank of the card.
     * @param rank the rank to set
     */
    public void setRank(String rank) {
        this.rank = rank;
    }

    /**
     * Sets the suit of the card.
     * @param suit the suit to set
     */
    public void setSuit(String suit) {
        this.suit = suit;
    }

    /**
     * Returns the value of the card.
     * @return the value of the card
     */
    public int getValue() {
        if (rank.equals("a")) {
            return 11;
        } else if (rank.equals("j") || rank.equals("q") || rank.equals("k") || rank.equals("t")) {
            return 10;
        } else {
            return Integer.parseInt(rank);
        }
    }

    /**
     * Returns the index of the rank of the card.
     * @return the index of the rank of the card
     */
    public int getRankIndex() {
        if (rank.equals("a")) {
            return 1;
        } else if (rank.equals("t")){
            return 10;
        } else if (rank.equals("j")){
            return 11;
        } else if (rank.equals("q")){
            return 12;
        } else if (rank.equals("k")){
            return 13;
        } else {
            return Integer.parseInt(rank);
        }
    }

    /**
     * Compares this card with another card based on their ranks.
     * @param anotherCard the card to be compared
     * @return a negative integer, zero, or a positive integer as this card is less than, equal to, or greater than the specified card
     */
    public int compareTo(Card anotherCard) {
        return getRankIndex() - anotherCard.getRankIndex();
    }

    /**
     * Returns a string representation of the card.
     * @return a string representation of the card
     */
    @Override
    public String toString() {
        return rank + "" + suit;
    }

    /**
     * Returns the image of the card.
     * @return the image of the card
     */
    public Image getImage() {
        try {
            String imagePath = "res/cards/" + rank + suit + ".gif";
            // System.out.println(imagePath);
            return new ImageIcon(imagePath).getImage();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Prints the card image on the specified graphics context at the specified location.
     * @param g the graphics context
     * @param x the x-coordinate of the top-left corner of the card image
     * @param y the y-coordinate of the top-left corner of the card image
     */
    public void printCard(Graphics g, int x, int y){
        g.drawImage(this.getImage(), x, y, cardWidth, cardHeight, null);
    }

    /**
     * Returns the width of the card image.
     * @return the width of the card image
     */
    public static int getCardWidth(){
        return cardWidth;
    }

    /**
     * Returns the height of the card image.
     * @return the height of the card image
     */
    public static int getCardHeight(){
        return cardHeight;
    }

    /**
     * Checks if the card is an Ace.
     * @return true if the card is an Ace, false otherwise
     */
    public boolean isAce() {
        return rank.equals("a");
    }

}