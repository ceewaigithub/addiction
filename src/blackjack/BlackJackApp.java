package blackjack;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.random.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import entity.User;
import main.BettingSystem;
import main.Card;
import main.Deck;
import main.Player;
import tile.Tile;

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
        BlackJack blackjack = new BlackJack(user, bettingSystem);
        blackjack.addPlayer(dealer);
        blackjack.addPlayer(player);

        // Starting BlackJackApp runs BlackJackGUI
        JFrame bJframe = new JFrame("BlackJack");
        BlackJackGUI blackJackPanel = new BlackJackGUI(blackjack, bJframe, mapFrame, bettingSystem, user);

        // if (!blackjack.getGameStatus()) {
        //     if (blackjack.getResult()) {
        //         user.addMoney(10);
        //     } else {
        //         user.subtractMoney(10);
        //     }
        // }
    }

    public static void main(String[] args) {
        
        BlackJackApp bj = new BlackJackApp(null, new JFrame());
    }
}
