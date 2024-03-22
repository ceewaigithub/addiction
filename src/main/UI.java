package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Coin;
import shop.ShopItem;
import shop.SpriteItem;
import world.GamePanel;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font aerial_10;
    BufferedImage coinImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public int commandNumber = 0;

    // title screen state: 0 : main, 1: shop
    public int titleScreenState = 0;

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

        if (gp.gameState == gp.titleState) {
            drawTitleScreen(g2);
        }

        g2.setFont(aerial_10);
        g2.setColor(Color.white);
        g2.drawImage(coinImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize/2, gp.tileSize/2, null);
        g2.drawString("x " + gp.user.money, 55, 45);
        drawControls(g2, gp.tileSize, gp.getHeight() - gp.screenHeight - gp.tileSize, gp.screenWidth, gp.screenHeight);


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
        g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
        String controls = "Controls:\nW - Move Up\nA - Move Left\nS - Move Down\nD - Move Right\nSpace - Interact\nESC - Menu";
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

    public void drawTitleScreen(Graphics2D g2) {

        if (titleScreenState == 0) {
            drawTitleScreenMain(g2);
        } else if (titleScreenState == 1) {
            drawTitleScreenShop(g2);
        }

    }

    public void drawMenuItem(Graphics2D g2, int menuItemX, int menuItemY, String menuItemText, int menuItemNumber) {
        if (commandNumber == menuItemNumber) {
            g2.drawString("> " + menuItemText, menuItemX, menuItemY);
        } else {
            g2.drawString(menuItemText, menuItemX, menuItemY);
        }
    }

    public void drawTitleScreenMain(Graphics2D g2) {
        g2.setColor(new Color(40, 40, 43));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Set the font to a game font
        Font gameFont = new Font("GameFont", Font.BOLD, 50);
        g2.setFont(gameFont);

        // Draw title with shadow
        String title = "addiction.";
        FontMetrics fontMetrics = g2.getFontMetrics(gameFont);
        int titleX = (gp.getWidth() - fontMetrics.stringWidth(title)) / 2;
        int titleY = (int)(gp.getHeight() / 2 - gp.tileSize * 1.5);
        
        // Draw shadow
        g2.setColor(Color.darkGray);
        g2.drawString(title, titleX + 2, titleY + 2);
        
        // Draw title
        g2.setColor(Color.decode("#ff6600")); // Set the color to #ff6600 (orange)
        g2.drawString(title, titleX, titleY);

        // Draw menu items
        Font menuItemFont = new Font("GameFont", Font.BOLD, 20);
        g2.setFont(menuItemFont);
        int menuItemX = (gp.getWidth() - fontMetrics.stringWidth("Start")) / 2;
        int menuItemY = titleY + 100; // Adjust the Y position for menu items
        g2.setColor(Color.WHITE); // Set the color to white

        // Draw arrows based on commandNumber
        drawMenuItem(g2, menuItemX, menuItemY, "Start/Continue", 0);
        menuItemY += 50; // Adjust the Y position for the next menu item
        drawMenuItem(g2, menuItemX, menuItemY, "Shop", 1);
        menuItemY += 50; // Adjust the Y position for the next menu item
        drawMenuItem(g2, menuItemX, menuItemY, "Save", 2);
        menuItemY += 50; // Adjust the Y position for the next menu item
        drawMenuItem(g2, menuItemX, menuItemY, "Exit", 3);
    }


    public void drawTitleScreenShop(Graphics2D g2) {
        g2.setColor(new Color(40, 40, 43));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Set the font to a game font
        Font gameFont = new Font("GameFont", Font.BOLD, 50);
        g2.setFont(gameFont);

        // Draw title with shadow
        String title = "Shop";
        FontMetrics fontMetrics = g2.getFontMetrics(gameFont);
        int titleX = (gp.getWidth() - fontMetrics.stringWidth(title)) / 2;
        int titleY = (int)(gp.getHeight() / 2 - gp.tileSize * 1.5);

        // Draw shadow
        g2.setColor(Color.darkGray);
        g2.drawString(title, titleX + 2, titleY + 2);

        // Draw title
        g2.setColor(Color.decode("#ff6600")); // Set the color to #ff6600 (orange)
        g2.drawString(title, titleX, titleY);

        // Draw shop items
        Font itemFont = new Font("GameFont", Font.PLAIN, 16);
        g2.setFont(itemFont);
        int itemX = (gp.getWidth() - fontMetrics.stringWidth("Buy")) / 2;
        int itemY = titleY + 100; // Adjust the Y position for shop items
        g2.setColor(Color.WHITE); // Set the color to white

        for (int i = 0; i < gp.sm.getShopItems().size(); i++) {
            ShopItem item = gp.sm.getShopItems().get(i);
            String itemText = item.getName();
            if (!item.isPurchased()) {
                itemText += " - Price: " + item.getPrice();
                g2.setColor(Color.WHITE); // Set the color to white for non-purchased items
            } else {
                if (item instanceof SpriteItem) {
                    SpriteItem spriteItem = (SpriteItem) item;
                    if (spriteItem.getSprite().equals(gp.sm.getCurrentSprite())) {
                        itemText += " - Equipped";
                        g2.setColor(Color.GREEN); // Set the color to green for the equipped sprite
                    } else {
                        itemText += " - Purchased";
                        g2.setColor(Color.GRAY); // Set the color to gray for purchased sprite items
                    }
                } else {
                    itemText += " - Purchased";
                    g2.setColor(Color.GRAY); // Set the color to gray for purchased items
                }
            }

            drawMenuItem(g2, itemX, itemY, itemText, i);

            itemY += 30; // Adjust the Y position for the next item
        }
    
        // Draw "Back" option
        String backText = "Back";
        g2.setColor(Color.WHITE); // Set the color to white for the "Back" option
        drawMenuItem(g2, itemX, itemY, backText, gp.sm.getShopItems().size());
    }

    public void drawPauseScreen(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50));
        int messageX = (gp.getWidth() - g2.getFontMetrics().stringWidth("PAUSED")) / 2;
        int messageY = (int)(gp.getHeight() / 2 - gp.tileSize * 1.5);
        g2.drawString("PAUSED", messageX, messageY);
    }

}
