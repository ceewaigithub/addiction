package blackjack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Player;
import world.GamePanel;

public class BlackJackPanel extends JPanel implements Runnable {

    // Screen Settings
    public final int screenWidth = 768;
    public final int screenHeight = 576;

    public final int cardWidth = 73;
    public final int cardHeight = 97;
    public final int spacing = 10;

    // BlackJackPanel constructor
    public BlackJackPanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    // // Draw hand
    // public void drawHand() {
    //     String imagePath = currentDirectory + "/res/" + dealer.getHand().get(i).getImagePath();
    //     Image cardImg = new ImageIcon(imagePath).getImage();
    //     g.drawImage(cardImg, centerDealerX + (i * (cardWidth + spacing)), 20, cardWidth, cardHeight, null);
    // }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        
    }
}
