package blackjack;

import javax.swing.*;
import entity.User;
import main.BettingSystem;
import main.Player;

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
        BlackJack blackjack = new BlackJack(bettingSystem);
        blackjack.addPlayer(dealer);
        blackjack.addPlayer(player);

        // Starting BlackJackApp runs BlackJackGUI
        new BlackJackGUI(blackjack, mapFrame, bettingSystem);
    }
}
