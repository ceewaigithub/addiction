package highlow;

import javax.swing.JFrame;

import entity.User;
import main.BettingSystem;
import main.Player;

public class HighLowApp {

    // Variable
    User user;
    JFrame mapFrame;

    public HighLowApp(User user, JFrame mapFrame) {
        this.user = user;
        this.mapFrame = mapFrame;

        Player player = new Player("Player");
        BettingSystem bettingSystem = new BettingSystem(player);
        HighLowGame highLowGame = new HighLowGame(bettingSystem);
        highLowGame.addPlayer(player);

        HighLowGUI highLowGUI = new HighLowGUI(highLowGame, bettingSystem);
        highLowGUI.start();
    }
    public static void main(String[] args) {

        Player player = new Player("Player");
        BettingSystem bettingSystem = new BettingSystem(player);
        HighLowGame highLowGame = new HighLowGame(bettingSystem);
        highLowGame.addPlayer(player);

        HighLowGUI highLowGUI = new HighLowGUI(highLowGame, bettingSystem);
        highLowGUI.start();
    }
}       


/*
 * 
 * Betting system.
 * starting bet: 100
 * starting multiplyer: 0.25
 * power = 2
 * winnings = bet * 0.125 * 2^(score - 1)
 * 
 * 1: 100 * 0.125 = 12
 * 2: 100 * 0.25 = 25
 * 3: 100 * 0.5 = 50
 * 4: 100 * 1 = 100
 * 5: 100 * 2 = 200
 * 6: 100 * 4 = 400
 * 7: 100 * 8 = 800
 * 8: 100 * 16 = 1600
 * 9: 100 * 32 = 3200
 * 10: 100 * 64 = 6400
 * 
 * 
 * 
 */