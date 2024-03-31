package baccarat;

import main.BettingSystem;
import main.Player;
import main.Card;
import main.Deck;

import card.CardGame;

public class BaccaratGame extends CardGame{
    private int playerTurn;

    public BaccaratGame(BettingSystem bettingSystem) {
        super(bettingSystem);
    }

    @Override
    public void startGame() {
        // Make new deck per round
        deck = new Deck();
        // Set player turn
        playerTurn = 0;
        // Start the game - issue 2 cards per player
        for (Player player : players) {
            for(int i = 0; i < 2; i++){
                Card newcard = deck.dealCard();
                player.addCard(newcard);
            }
        }
    }

    public String checkWinner(boolean checkNatural){

        Player player = players.getFirst();
        Player dealer = players.getLast();
        int playerScore = player.getHandValue()%10;
        int dealerScore = dealer.getHandValue()%10;

        if(checkNatural){
            // Check if player or dealer has naturals
            if(playerScore >= 8 || dealerScore >= 8) {
                nextPlayerTurn();
                return compareScore(playerScore, dealerScore);
            } else {
                return "";
            }

        } else {
            return compareScore(playerScore, dealerScore);
        }

    }

    public String compareScore(int playerScore, int dealerScore) {

            if(playerScore > dealerScore){
                bettingSystem.winBet(1);
                return "Player";
            }else if (playerScore < dealerScore){
                bettingSystem.loseBet();
                return "Dealer";
            }else{
                bettingSystem.pushBet();
                return "Push";
            }

    }

    public void hit(){
        // Player draws a card
        players.getFirst().addCard(deck.dealCard());
        nextPlayerTurn();
    }
    public void stand(){
        nextPlayerTurn();
    }

    public boolean isDealerTurn(){
        return playerTurn == 1;
    }

    public void nextPlayerTurn(){
        playerTurn++;
    }

    public void dealerTurn(){
        // Make dealer draw a card when score less than 5
        if(players.getLast().getHandValue() % 10 < 5){
            players.getLast().addCard(deck.dealCard());
        }
    }

}
