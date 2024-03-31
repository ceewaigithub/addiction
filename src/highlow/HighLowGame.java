package highlow;

import java.util.ArrayList;
import java.util.List;
import main.BettingSystem;
import main.Player;
import main.Card;
import main.Deck;

/**
 * The HighLowGame class encapsulates the core game logic for a High-Low card game.
 * It manages the game's deck, players, and betting system, as well as the game's
 * current state, such as the current and next card, and the player's score.
 */
class HighLowGame {

    private Deck deck; // The deck of cards used in the game.
    private Card currCard; // The current card being compared against.
    private Card nextCard; // The next card drawn from the deck.
    private int score; // The player's current score.
    private List<Player> players; // The list of players in the game.
    private BettingSystem bettingSystem; // The betting system for handling bets.

    // Constructor for the HighLowGame class.
    public HighLowGame(BettingSystem bettingSystem) {
        this.bettingSystem = bettingSystem;
        players = new ArrayList<>();
    }

    // Adds a player to the game.
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Initializes and starts a new game. This includes creating a new deck of cards
     * and resetting the player's score to 0.
     */
    public void startGame(){
        deck = new Deck();
        currCard = deck.dealCard();
        score = 0;
    }

    /**
     * Determines if the player's guess is correct and updates the game state accordingly.
     * @param guess The player's guess ('H' for higher, 'L' for lower).
     * @return True if the guess is correct, false otherwise.
     */
    public boolean isCorrect(String guess) {
        nextCard = deck.dealCard();
        System.out.println("Current card: " + currCard + ", Next card is: " + nextCard);
        System.out.println("Guess: " + guess);
        
        // Determine if the guess is correct based on the comparison of current and next card values.
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

    /**
     * Calculates and returns the player's winnings based on their score and updates the betting system.
     * @return The amount of winnings.
     */
    public int checkScore(){
        int winnings = 0;

        // Calculate winnings based on the score. No winnings for a score of 2 or less.
        if (score <= 2) {
            bettingSystem.loseBet();
        } else {
            int multiplier = score;
            winnings = bettingSystem.getPlayerBet() * (multiplier + 1);
            System.out.println(multiplier);
            bettingSystem.winBet(multiplier);
        }
        return winnings;

    }

    // Returns the list of players in the game.
    public List<Player> getPlayers(){
        return players;
    }

    // Getter methods for the current card, next card, and score.
    public Card getCurrCard(){
        return currCard;
    }

    public Card getNextCard(){
        return nextCard;
    }

    public int getScore(){
        return score;
    }

    /**
     * Ends the current round of the game. This method is responsible for clearing
     * the players' hands and preparing for the next round or ending the game.
     */
    public void endRound(){
        for (Player player : players) {
            player.discardHand();
        }
    }
}