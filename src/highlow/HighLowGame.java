package highlow;

import java.util.ArrayList;
import java.util.List;
import main.BettingSystem;
import main.Player;
import main.Card;
import main.Deck;

class HighLowGame {

    private Deck deck;
    private Card currCard;
    private Card nextCard;
    private int score;
    private List<Player> players;

    public HighLowGame() {
        players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void startGame(){
        // Make new deck every game
        deck = new Deck();
        currCard = deck.dealCard();
        score = 0;

        // Add the first card to the player's hand
        // player.addCard(currCard);
    }

    // Checking if guess is correct or incorrect
    public boolean isCorrect(String guess) {
        nextCard = deck.dealCard();
        System.out.println("Current card: " + currCard + ", Next card is: " + nextCard);
        System.out.println("Guess: " + guess);
        // player.addCard(currCard);
        
        if ((nextCard.compareTo(currCard) > 0 && guess.equals("H")) || (nextCard.compareTo(currCard) < 0 && guess.equals("L"))) {
            System.out.println("Correct guess");
            score++;
            currCard = nextCard;
            return true;
        } else {
            if (nextCard.compareTo(currCard) == 0) {
                System.out.println("You lose on ties");
            } else {
                System.out.println("Incorrect guess");
            }
            return false;
        }        
    }

    public List<Player> getPlayers(){
        return players;
    }

    public Card getCurrCard(){
        return currCard;
    }

    public Card getNextCard(){
        return nextCard;
    }

    public int getScore(){
        return score;
    }

    public void endRound(){
        for (Player player : players) {
            player.discardHand();
        }
    }
}