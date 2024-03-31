package highlow;

import javax.swing.JFrame;
import entity.User;
import main.BettingSystem;
import main.Player;


/**
 * The HighLowApp class is the main entry point for the HighLow card game application.
 * It sets up the game environment, including the user, game frame, betting system,
 * players, and the GUI.
 */
public class HighLowApp {

     // Instance variables to hold the current user and the main application frame.
    User user; // Represents the current user playing the game.
    JFrame mapFrame; // The main frame (map) on which the game's GUI is displayed.

    public HighLowApp(User user, JFrame mapFrame) {
        this.user = user;
        this.mapFrame = mapFrame;

        // Create a new player for the game.
        Player player = new Player("Player");

        // Initialize the betting system with the current user.
        BettingSystem bettingSystem = new BettingSystem(user);

        // Set up the HighLowGame with the betting system.
        HighLowGame highLowGame = new HighLowGame(bettingSystem);

        // Add the player to the game.
        highLowGame.addPlayer(player);

        // Initialize the GUI for the HighLow game and pass in necessary dependencies.
        HighLowGUI highLowGUI = new HighLowGUI(highLowGame, bettingSystem, mapFrame, user);

        // Start the game through its GUI.
        highLowGUI.start();
    }
}       
