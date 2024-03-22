package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Coin;
import world.GamePanel;

public class UI {

    GamePanel gp;
    Graphics2D g2;
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

        if (gp.gameState == gp.playState) {
            drawControls(g2, gp.tileSize, gp.getHeight() - gp.screenHeight - gp.tileSize, gp.screenWidth, gp.screenHeight);
        }

        if (gp.gameState == gp.pauseState) {
            drawPauseScreen(g2);
        }

    }

    public void drawDialogueState(Graphics2D g2) {
        
        // window 
        int x = gp.getWidth() * 2;
        int y = gp.getHeight() * 2;
        int width = gp.screenWidth - gp.tileSize * 4;
        int height = gp.tileSize *5;

        g2.setColor(Color.BLACK);
        g2.fillRect(gp.tileSize, gp.tileSize, width, height);
        g2.setColor(Color.WHITE);
        g2.drawRect(gp.tileSize, gp.tileSize, width, height);

    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0);

        g2.setColor(c);
        g2.fillRect(x, y, width, height);
        g2.setColor(Color.WHITE);
        g2.drawRect(x, y, width, height);

    }

    public void drawControls(Graphics2D g2, int x, int y, int width, int height) {
        // Calculate the height required to fit the instructions
        g2.setFont(aerial_10);
        String controls = "Controls:\nW - Move Up\nA - Move Left\nS - Move Down\nD - Move Right\nSpace - Interact\nESC - Pause Game";
        String[] lines = controls.split("\n");
        int lineHeight = g2.getFontMetrics().getHeight();
        int requiredHeight = lineHeight * (lines.length + 1);

        // Adjust the height of the rectangle to fit the instructions
        if (requiredHeight < height) {
            y = gp.getHeight() - requiredHeight - 10; // Adjust y position to bottom left corner
            height = requiredHeight;
        }

        // Define padding
        int padding = 10;

        // Calculate the width required to fit the longest line of text
        int maxWidth = 0;
        for (String line : lines) {
            int lineWidth = g2.getFontMetrics().stringWidth(line);
            if (lineWidth > maxWidth) {
                maxWidth = lineWidth;
            }
        }

        // Adjust the width of the rectangle to fit the text
        if (maxWidth + 2 * padding < width) {
            x = padding; // Adjust x position to bottom left corner
            width = maxWidth + 2 * padding;
        }

        // Calculate the position to align the text with padding
        int textX = x + padding;
        int textY = y + lineHeight + padding/2;

        // Draw grey transparent box with borders
        g2.setColor(new Color(128, 128, 128, 128));
        g2.fillRect(x, y, width, height);
        g2.setColor(Color.WHITE);
        g2.drawRect(x, y, width, height);

        // Draw controls text with padding
        g2.setFont(aerial_10);
        g2.setColor(Color.WHITE);
        for (int i = 0; i < lines.length; i++) {
            g2.drawString(lines[i], textX, textY + lineHeight * i);
        }
    }

    public void drawPauseScreen(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50));
        int messageX = (gp.getWidth() - g2.getFontMetrics().stringWidth("PAUSED")) / 2;
        int messageY = (int)(gp.getHeight() / 2 - gp.tileSize * 1.5);
        g2.drawString("PAUSED", messageX, messageY);
    }

}
