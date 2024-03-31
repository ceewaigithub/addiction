package blackjack;

import javax.swing.*;

import card.BettingSystem;
import card.Player;
import entity.User;

public class BlackJackApp {

    // Variable
    User user;
    JFrame mapFrame;

    // Constructor
    public BlackJackApp(User user, JFrame mapFrame) {

        // Send in user & mapFrame
        this.user = user;
        this.mapFrame = mapFrame;

        // Create players
        Player dealer = new Player("Dealer");
        Player player = new Player("Player");

        // Create betting system
        BettingSystem bettingSystem = new BettingSystem(user);
    
        // Create blackJack game
        BlackJackGame blackjack = new BlackJackGame(bettingSystem);
        blackjack.addPlayer(dealer);
        blackjack.addPlayer(player);

        // Starting BlackJackApp runs BlackJackGUI
        new BlackJackGUI(blackjack, mapFrame, bettingSystem);
    }
}
