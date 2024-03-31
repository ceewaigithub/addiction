package highlow;

import javax.swing.JFrame;

import card.BettingSystem;
import card.Player;
import entity.User;

public class HighLowApp {

    // Variable
    User user;
    JFrame mapFrame;

    public HighLowApp(User user, JFrame mapFrame) {
        this.user = user;
        this.mapFrame = mapFrame;

        Player player = new Player("Player");
        BettingSystem bettingSystem = new BettingSystem(user);
        HighLowGame highLowGame = new HighLowGame(bettingSystem);
        highLowGame.addPlayer(player);

        HighLowGUI highLowGUI = new HighLowGUI(highLowGame, bettingSystem, mapFrame, user);
        highLowGUI.start();
    }
//    public static void main(String[] args) {

//        Player player = new Player("Player");
//        BettingSystem bettingSystem = new BettingSystem(player);
//        HighLowGame highLowGame = new HighLowGame(bettingSystem);
//        highLowGame.addPlayer(player);

//        HighLowGUI highLowGUI = new HighLowGUI(highLowGame, bettingSystem);
//        highLowGUI.start();
//    }
}       
