import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.util.random.*;
import javax.swing.*;

public class BlackJack {
    private List<Player> players;
    private Deck deck;

    public BlackJack() {
        players = new ArrayList<>();
        deck = new Deck();
        startGame();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void startGame() {
        // Shuffle the deck
        deck.shuffle();

        // Deal initial cards to players
        for (Player player : players) {
            player.addCard(deck.dealCard());
            player.addCard(deck.dealCard());
        }

        // Play the game
        for (Player player : players) {
            player.play(deck);
        }

        // Determine the winner
        Player winner = determineWinner();
        System.out.println("The winner is: " + winner.getName());
    }

    private Player determineWinner() {
        // Implement your logic to determine the winner
        return null;
    }

    public static void main(String[] args) {
        BlackJack blackJack = new BlackJack();
        blackJack.deck.printDeck();
    }
}
