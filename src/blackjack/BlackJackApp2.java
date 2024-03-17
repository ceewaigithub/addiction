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
import main.Card;
import main.Deck;
import main.Player;
import tile.Tile;

public class BlackJackApp2 {

    // Variable
    User user;
    JFrame mapFrame;

    // Constructor
    public BlackJackApp2(User user, JFrame mapFrame) {

        // Send in user & mapFrame
        this.user = user;
        this.mapFrame = mapFrame;

        // Create blackJack game
        BlackJack2 blackjack = new BlackJack2(user);

        // Starting BlackJackApp runs BlackJackGUI
        BlackJackPanel blackJackPanel = new BlackJackPanel(blackjack);     
        
        // After setting up GUI, start game
        blackjack.startGame();

        // Once game ends, show previous frame
        mapFrame.setVisible(true);
    }

    public static void main(String[] args) {
        
        BlackJackApp2 bj = new BlackJackApp2(null, new JFrame());
    }
}
