package blackjack;

import javax.swing.*;

import card.BettingSystem;
import card.Player;
import entity.User;

/**
 * The BlackJackApp class represents the main application for the Blackjack game.
 * It initializes the necessary components and starts the game.
 */
public class BlackJackApp {

    // Variable
    User user; // Represents the user playing the game
    JFrame mapFrame; // Represents the main frame of the game

    /**
     * Constructs a BlackJackApp object with the specified user and map frame.
     * 
     * @param user      the user playing the game
     * @param mapFrame  the main frame of the game
     */
    public BlackJackApp(User user, JFrame mapFrame) {

        // Send in user & mapFrame
        this.user = user;
        this.mapFrame = mapFrame;

        // Create players
        Player dealer = new Player("Dealer"); // Represents the dealer in the game
        Player player = new Player("Player"); // Represents the player in the game

        // Create betting system
        BettingSystem bettingSystem = new BettingSystem(user); // Represents the betting system for the game
    
        // Create blackJack game
        BlackJackGame blackjack = new BlackJackGame(bettingSystem); // Represents the Blackjack game
        blackjack.addPlayer(dealer);
        blackjack.addPlayer(player);

        // Starting BlackJackApp runs BlackJackGUI
        new BlackJackGUI(blackjack, mapFrame, bettingSystem); // Starts the GUI for the Blackjack game
    }
}
