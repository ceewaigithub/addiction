package Baccarat;

import main.BettingGUI;
import main.BettingSystem;
import main.Player;
public class BaccaratApp {
    public static void main(String[] args) {
        Player player = new Player("Player");
        Player dealer = new Player("Dealer");
        BettingSystem bettingSystem = new BettingSystem(player);
        BettingGUI bettingGUI = new BettingGUI(bettingSystem);
        BaccaratGame baccaratGame = new BaccaratGame(bettingSystem);
        baccaratGame.addPlayer(player);
        baccaratGame.addPlayer(dealer);

        BaccaratGUI baccaratGUI = new BaccaratGUI(baccaratGame, bettingGUI);
        baccaratGUI.start();
    }
}
