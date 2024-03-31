package highlow;

import card.BettingSystem;
import card.Card;
import card.CardGame;
import card.Deck;

/**
 * The HighLowGame class encapsulates the core game logic for a High-Low card game.
 * It manages the game's deck, players, and betting system, as well as the game's
 * current state, such as the current and next card, and the player's score.
 */
class HighLowGame extends CardGame{

    private Card currCard; // The current card being compared against.
    private Card nextCard; // The next card drawn from the deck.
    private int score; // The player's current score.

    public HighLowGame(BettingSystem bettingSystem) {
        super(bettingSystem);
    }

    /**
     * Initializes and starts a new game. This includes creating a new deck of cards
     * and resetting the player's score to 0.
     */
    @Override
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
        
        // Determine if the guess is correct based on the comparison of current and next card values.
        if ((nextCard.compareTo(currCard) > 0 && guess.equals("H")) || (nextCard.compareTo(currCard) < 0 && guess.equals("L"))) {
            score++;
            currCard = nextCard;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calculates and returns the player's winnings based on their score and updates the betting system.
     * @return The amount of winnings.
     */
    public int checkScore() {
        int winnings = 0;

        // Calculate winnings based on the score. No winnings for a score of 2 or less.
        if (score <= 2) {
            bettingSystem.loseBet();
        } else {
            int multiplier = score;
            winnings = bettingSystem.getPlayerBet() * (multiplier + 1);
            bettingSystem.winBet(multiplier);
        }
        return winnings;
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

}