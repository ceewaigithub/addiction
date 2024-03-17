package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_Coin;
import world.GamePanel;

public class UI {

    GamePanel gp;
    Font aerial_10;
    BufferedImage coinImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

        aerial_10 = new Font("Arial", Font.PLAIN, 10);
        OBJ_Coin coin = new OBJ_Coin();
        coinImage = coin.image;
    }

    public void showMessage(String text) {
        this.message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        g2.setFont(aerial_10);
        g2.setColor(Color.white);
        g2.drawImage(coinImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize/2, gp.tileSize/2, null);
        g2.drawString("x " + gp.user.money, 55, 45);

        if (messageOn) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25));
            int messageX = (gp.getWidth() - g2.getFontMetrics().stringWidth(message)) / 2;
            int messageY = (int)(gp.getHeight() / 2 - gp.tileSize * 1.5);

            g2.drawString(message, messageX, messageY);
            messageCounter++;
            if (messageCounter > 60) {
                messageOn = false;
                messageCounter = 0;
            }
        }

    }

}
