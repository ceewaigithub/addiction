package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_Coin;
import world.GamePanel;

public class UI {

    GamePanel gp;
    Font aerial_20;
    BufferedImage coinImage;

    public UI(GamePanel gp) {
        this.gp = gp;

        aerial_20 = new Font("Arial", Font.PLAIN, 20);
        OBJ_Coin coin = new OBJ_Coin();
        coinImage = coin.image;
    }

    public void draw(Graphics2D g2) {
        g2.setFont(aerial_20);
        g2.setColor(Color.white);
        g2.drawImage(coinImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
        g2.drawString("x " + gp.user.money, 74, 65);
    }

}
