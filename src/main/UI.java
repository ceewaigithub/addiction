package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import world.GamePanel;

public class UI {

    GamePanel gp;
    Font aerial_20;

    public UI(GamePanel gp) {
        this.gp = gp;

        aerial_20 = new Font("Arial", Font.PLAIN, 20);
    }

    public void draw(Graphics2D g2) {
        g2.setFont(aerial_20);
        g2.setColor(Color.white);
        g2.drawString("Money = " + gp.user.money, 50, 50);
    }

}
