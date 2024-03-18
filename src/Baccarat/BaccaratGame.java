package Baccarat;

import main.BettingSystem;
import main.Player;
import main.Card;
import main.Deck;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BaccaratGame {
    private Deck deck;
    private List<Player> players;

    private BettingSystem bettingSystem;

    private boolean activeRound = false;
    private int playerTurn;
    public BaccaratGame(BettingSystem bettingSystem) {
        this.bettingSystem = bettingSystem;
        players = new ArrayList<>();
        playerTurn = 0;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void startGame() {
        // Make new deck every game
        deck = new Deck();

        // Start the game
        activeRound = true;

        for (Player player : players) {
            for(int i = 0; i < 2; i++){
                Card newcard = deck.dealCard();
                player.addCard(newcard);
                System.out.println(newcard);
            }
        }
        deck.printDeck();

    }

    public String compareScore(boolean natural){
        Player player = players.get(0);
        Player dealer = players.get(1);
        int playerScore = player.getHandValue()%10;
        int dealerScore = dealer.getHandValue()%10;
        if(natural){
            if(playerScore >= 8 || dealerScore >= 8){
                playerTurn = -1;
                if(playerScore > dealerScore){
                    bettingSystem.winBet(2);
                    return "Player";
                }else if (playerScore < dealerScore){
                    bettingSystem.loseBet();
                    return "Dealer";
                }else{
                    bettingSystem.pushBet();
                    return "Push";
                }
            }
            return "";
        } else {
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
    }

    public int getPlayerTurn(){
        return playerTurn;
    }
    public List<Player> determineWinners() {
        // Determine winners of the game
        return null;
    }

    public void hit(){
        players.get(playerTurn).addCard(deck.dealCard());
        nextPlayerTurn();
    }
    public void stand(){
        nextPlayerTurn();
    }

    public void nextPlayerTurn(){
        playerTurn++;
    }

    public void dealerTurn(){
        if(players.get(playerTurn).getHandValue() % 10 < 4){
            players.get(playerTurn).addCard(deck.dealCard());
        }
    }

    public boolean isActiveRound(){
        return activeRound;
    }
    public Deck getDeck() {
        return deck;
    }

    public List<Player> getPlayers(){
        return players;
    }

    public void endRound(){
        activeRound = false;
        for (Player player : players) {
            player.discardHand();
        }
        playerTurn = 0;
    }

//    public int getMultiplier(){
//
//    }

//    public void checkWin(){
//
//        List<Card> playerHand = players.get(playerTurn).getHand();
//        System.out.println("Three of a kind: "+checkThreeOfAKind(playerHand));
//        System.out.println("Same suit: "+checkSameSuit(playerHand));
//        System.out.println("Straights: "+checkStraight(playerHand));
//    }
    public static boolean checkThreeOfAKind(List<Card> hand) {
        if (hand.size() != 3) {
            throw new IllegalArgumentException("Exactly 3 cards are required.");
        }

        int[] numbers = {hand.get(0).getValue(), hand.get(1).getValue(), hand.get(2).getValue()};
        Arrays.sort(numbers);

        return numbers[0] == numbers[2];
    }

    // Function to check if the 3 cards have the same suit
    public static boolean checkSameSuit(List<Card> hand) {
        if (hand.size() != 3) {
            throw new IllegalArgumentException("Exactly 3 cards are required.");
        }

        return hand.get(0).getSuit().equals(hand.get(1).getSuit()) && hand.get(1).getSuit().equals(hand.get(2).getSuit());
    }

    // Function to check if the 3 cards form a straight
    public static boolean checkStraight(List<Card> hand) {
        if (hand.size() != 3) {
            throw new IllegalArgumentException("Exactly 3 cards are required.");
        }

        int[] numbers = {hand.get(0).getValue(), hand.get(1).getValue(), hand.get(2).getValue()};
        Arrays.sort(numbers);

        return (numbers[0] + 1 == numbers[1] && numbers[1] + 1 == numbers[2]) ||
                (numbers[0] == 1 && numbers[1] == 10 && numbers[2] == 13);
    }
}
