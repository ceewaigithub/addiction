package highlow;

import card.CardGame;
import main.BettingSystem;

import main.Card;
import main.Deck;

class HighLowGame extends CardGame{

    private Deck deck;
    private Card currCard;
    private Card nextCard;
    private int score;
    private BettingSystem bettingSystem;

    public HighLowGame(BettingSystem bettingSystem) {
        this.bettingSystem = bettingSystem;

    }

    public void startGame(){
        // Make new deck every game
        deck = new Deck();
        currCard = deck.dealCard();
        score = 0;
    }

    // Checking if guess is correct or incorrect
    public boolean isCorrect(String guess) {
        nextCard = deck.dealCard();
        System.out.println("Current card: " + currCard + ", Next card is: " + nextCard);
        System.out.println("Guess: " + guess);
        
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

    public int checkScore(){
        int winnings = 0;
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