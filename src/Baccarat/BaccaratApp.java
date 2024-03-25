package Baccarat;

import javax.swing.JFrame;

import blackjack.BlackJackPanel;
import entity.User;
import main.BettingGUI;
import main.BettingSystem;
import main.Player;
public class BaccaratApp {

    // Variable
    User user;
    JFrame mapFrame;

    public BaccaratApp(User user, JFrame mapFrame) {
        this.user = user;
        this.mapFrame = mapFrame;
        Player player = new Player("Player");
        Player dealer = new Player("Dealer");
        BettingSystem bettingSystem = new BettingSystem(user);
        BaccaratGame baccaratGame = new BaccaratGame(bettingSystem);
        baccaratGame.addPlayer(player);
        baccaratGame.addPlayer(dealer);

        BaccaratGUI baccaratGUI = new BaccaratGUI(baccaratGame, bettingSystem);
        baccaratGUI.start();
    }

//    public static void main(String[] args) {
//        Player player = new Player("Player");
//        Player dealer = new Player("Dealer");
//        BettingSystem bettingSystem = new BettingSystem(user);
//        BaccaratGame baccaratGame = new BaccaratGame(bettingSystem);
//        baccaratGame.addPlayer(player);
//        baccaratGame.addPlayer(dealer);
//
//        BaccaratGUI baccaratGUI = new BaccaratGUI(baccaratGame, bettingSystem);
//        baccaratGUI.start();
//    }
}
