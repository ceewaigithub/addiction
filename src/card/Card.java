package card;

import javax.swing.*;
import java.awt.*;

public class Card implements Comparable<Card>{
    private String suit;
    private String rank;

    static final int cardWidth = 73;
    static final int cardHeight = 97;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Card(String suit) {
        this.suit = suit;
        this.rank = "";
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

    public int getValue() {
        if (rank.equals("a")) {
            return 11;
        } else if (rank.equals("j") || rank.equals("q") || rank.equals("k") || rank.equals("t")) {
            return 10;
        } else {
            return Integer.parseInt(rank);
        }
    }

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

    public int compareTo(Card anotherCard) {
        return getRankIndex() - anotherCard.getRankIndex();
    }

    @Override
    public String toString() {
        return rank + "" + suit;
    }

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

    public void printCard(Graphics g, int x, int y){
        g.drawImage(this.getImage(), x, y, cardWidth, cardHeight, null);
    }

    public static int getCardWidth(){
        return cardWidth;
    }

    public static int getCardHeight(){
        return cardHeight;
    }

    public boolean isAce() {
        return rank.equals("a");
    }

}