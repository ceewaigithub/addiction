package blackjack;

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

    public int getValue() {
        if (rank.equals("a")) {
            return 11;
        } else if (rank.equals("j") || rank.equals("q") || rank.equals("k") || rank.equals("t")) {
            return 10;
        } else {
            return Integer.parseInt(rank);
        }
    }

    public boolean isAce() {
        return rank.equals("a");
    }

    public String getImagePath() {
        return "./cards/" + rank + suit + ".gif";
    }

    @Override
    public String toString() {
        return rank + "" + suit;
    }
}