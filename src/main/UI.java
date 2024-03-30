package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import object.OBJ_Coin;
import shop.ShopItem;
import shop.SpriteItem;
import world.GamePanel;
import java.awt.BasicStroke; // Import the BasicStroke class from the java.awt package

/**
 * The UI class represents the user interface of the game. It handles drawing various screens and elements on the screen.
 */
public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font aerial_10;
    BufferedImage coinImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public int commandNumber = 0;

    public int titleScreenState = 0;

    /**
     * Constructs a new UI object with the specified GamePanel.
     *
     * @param gp The GamePanel object associated with this UI.
     */
    public UI(GamePanel gp) {
        this.gp = gp;

        aerial_10 = new Font("Arial", Font.PLAIN, 10);
        OBJ_Coin coin = new OBJ_Coin();
        coinImage = coin.image;
    }

    /**
     * Displays a message on the screen.
     *
     * @param text The text to be displayed as a message.
     */
    public void showMessage(String text) {
        this.message = text;
        messageOn = true;
    }

    /**
     * Draws the UI elements on the screen.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    public void draw(Graphics2D g2) {

        if (gp.gameLogic.getGameState() == gp.gameLogic.titleState) {
            drawTitleScreen(g2);
        }

        g2.setFont(aerial_10);
        g2.setColor(Color.white);
        g2.drawImage(coinImage, gp.screenSettings.tileSize/2, gp.screenSettings.tileSize/2, gp.screenSettings.tileSize/2, gp.screenSettings.tileSize/2, null);
        g2.drawString("x " + gp.user.getBalance(), 55, 45);
        drawControls(g2, gp.screenSettings.tileSize, gp.getHeight() - gp.screenSettings.screenHeight - gp.screenSettings.tileSize, gp.screenSettings.screenWidth, gp.screenSettings.screenHeight);

        if (messageOn) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25));
            int messageX = (gp.getWidth() - g2.getFontMetrics().stringWidth(message)) / 2;
            int messageY = (int)(gp.getHeight() / 2 - gp.screenSettings.tileSize * 1.5);

            g2.drawString(message, messageX, messageY);
            messageCounter++;
            if (messageCounter > 60) {
                messageOn = false;
                messageCounter = 0;
            }
        }

        if (gp.gameLogic.getGameState() == gp.gameLogic.playState) {

        }

        if (gp.gameLogic.getGameState() == gp.gameLogic.pauseState) {
            drawPauseScreen(g2);
        }

        if (gp.gameLogic.getGameState() == gp.gameLogic.gameOverState) {
            drawGameOverScreen(g2);
        }

    }

    /**
     * Draws the dialogue state on the screen.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    public void drawDialogueState(Graphics2D g2) {
        
        // window 
        int width = gp.screenSettings.screenWidth - gp.screenSettings.tileSize * 4;
        int height = gp.screenSettings.tileSize *5;

        g2.setColor(Color.BLACK);
        g2.fillRect(gp.screenSettings.tileSize, gp.screenSettings.tileSize, width, height);
        g2.setColor(Color.WHITE);
        g2.drawRect(gp.screenSettings.tileSize, gp.screenSettings.tileSize, width, height);
    }

    /**
     * Draws a sub-window on the screen.
     *
     * @param x      The x-coordinate of the sub-window.
     * @param y      The y-coordinate of the sub-window.
     * @param width  The width of the sub-window.
     * @param height The height of the sub-window.
     */
    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0);

        g2.setColor(c);
        g2.fillRect(x, y, width, height);
        g2.setColor(Color.WHITE);
        g2.drawRect(x, y, width, height);

    }

    /**
     * Draws the controls on the screen.
     *
     * @param g2     The Graphics2D object used for drawing.
     * @param x      The x-coordinate of the controls.
     * @param y      The y-coordinate of the controls.
     * @param width  The width of the controls.
     * @param height The height of the controls.
     */
    public void drawControls(Graphics2D g2, int x, int y, int width, int height) {
        // Calculate the height required to fit the instructions
        g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
        String controls = "Controls:\nW - Up\nA - Left\nS - Down\nD - Right\nSpace - Interact\nESC - Menu";
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

    /**
     * Draws the title screen on the screen.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    public void drawTitleScreen(Graphics2D g2) {

        if (titleScreenState == 0) {
            drawTitleScreenMain(g2);
        } else if (titleScreenState == 1) {
            drawTitleScreenShop(g2);
        }

    }

    /**
     * Draws a menu item on the screen.
     *
     * @param g2             The Graphics2D object used for drawing.
     * @param menuItemX      The x-coordinate of the menu item.
     * @param menuItemY      The y-coordinate of the menu item.
     * @param menuItemText   The text of the menu item.
     * @param menuItemNumber The number of the menu item.
     */
    public void drawMenuItem(Graphics2D g2, int menuItemY, String menuItemText, int menuItemNumber) {
        FontMetrics fontMetrics = g2.getFontMetrics();
        int menuItemWidth = fontMetrics.stringWidth(menuItemText);
        int menuItemX = (gp.getWidth() - menuItemWidth) / 2;
    
        if (commandNumber == menuItemNumber) {
            g2.drawString("> " + menuItemText, menuItemX, menuItemY);
        } else {
            g2.drawString(menuItemText, menuItemX, menuItemY);
        }
    }

    /**
     * Draws the main title screen on the screen.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    public void drawTitleScreenMain(Graphics2D g2) {
        // Load the background image
        BufferedImage backgroundImage = null;
        try {
            backgroundImage = ImageIO.read(new File("res/ui/titleScreenBackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Draw the background image
        if (backgroundImage != null) {
            // Calculate the position and size of the background image
            int bgX = (gp.getWidth() - backgroundImage.getWidth()) / 2;
            int bgY = (gp.getHeight() - backgroundImage.getHeight()) / 2;
            int bgWidth = backgroundImage.getWidth();
            int bgHeight = backgroundImage.getHeight();

            // Draw the greyed-out background
            g2.setColor(new Color(0, 0, 0, 128)); // Set the color to semi-transparent black
            g2.fillRect(0, 0, gp.getWidth(), gp.getHeight());

            // Draw the background image centered
            g2.drawImage(backgroundImage, bgX, bgY, bgWidth, bgHeight, null);
        }

        // Set the font to a game font
        Font gameFont = new Font("GameFont", Font.BOLD, 50);
        g2.setFont(gameFont);

        // Draw a border with transparent background
        int borderWidth = gp.getWidth() - 250;
        int borderHeight = gp.getHeight() - 200;
        // Calculate the position and size of the border
        int borderX = (gp.getWidth() - borderWidth) / 2;
        int borderY = (gp.getHeight() - borderHeight) / 2 + 25;
        g2.setColor(new Color(128, 128, 128, 200)); // Set the border color to transparent grey
        g2.fillRect(borderX, borderY, borderWidth, borderHeight); // Draw the transparent background
        g2.setColor(Color.WHITE); // Set the border color to white
        g2.setStroke(new BasicStroke(5)); // Set the border thickness
        g2.drawRect(borderX, borderY, borderWidth, borderHeight); // Draw the border

        // Draw title with shadow
        String title = "addiction.";
        FontMetrics fontMetrics = g2.getFontMetrics(gameFont);
        int titleX = (gp.getWidth() - fontMetrics.stringWidth(title)) / 2;
        int titleY = (int)(gp.getHeight() / 2 - gp.screenSettings.tileSize * 1.5);

        // Draw shadow
        g2.setColor(Color.darkGray);
        g2.drawString(title, titleX + 2, titleY + 2);

        // Draw title
        g2.setColor(Color.decode("#ff6600")); // Set the color to #ff6600 (orange)
        g2.drawString(title, titleX, titleY);

        // Draw menu items
        Font menuItemFont = new Font("GameFont", Font.BOLD, 20);
        g2.setFont(menuItemFont);
        int menuItemY = titleY + 100; // Adjust the Y position for menu items
        g2.setColor(Color.WHITE); // Set the color to white

        // Draw arrows based on commandNumber
        drawMenuItem(g2, menuItemY, "Start/Continue", 0);
        menuItemY += 50; // Adjust the Y position for the next menu item
        drawMenuItem(g2, menuItemY, "Shop", 1);
        menuItemY += 50; // Adjust the Y position for the next menu item
        drawMenuItem(g2, menuItemY, "Save", 2);
        menuItemY += 50; // Adjust the Y position for the next menu item
        drawMenuItem(g2, menuItemY, "Exit", 3);
    }

    /**
     * Draws the shop screen on the screen.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    public void drawTitleScreenShop(Graphics2D g2) {

        try {
            // Load the background image
            BufferedImage backgroundImage = ImageIO.read(new File("res/ui/shopScreenBackground.png"));

            // Draw the background image
            g2.drawImage(backgroundImage, 0, 0, gp.screenSettings.screenWidth, gp.screenSettings.screenHeight, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set the font to a game font
        Font gameFont = new Font("GameFont", Font.BOLD, 50);
        g2.setFont(gameFont);

        // Draw a border with transparent background
        int borderWidth = gp.getWidth() - 250;
        int borderHeight = gp.getHeight() - 200;
        // Calculate the position and size of the border
        int borderX = (gp.getWidth() - borderWidth) / 2;
        int borderY = (gp.getHeight() - borderHeight) / 2 + 50;
        g2.setColor(new Color(64, 64, 64, 250));

        g2.fillRect(borderX, borderY, borderWidth, borderHeight); // Draw the transparent background
        g2.setColor(Color.WHITE); // Set the border color to white
        g2.setStroke(new BasicStroke(5)); // Set the border thickness
        g2.drawRect(borderX, borderY, borderWidth, borderHeight); // Draw the border

        // Draw title with shadow
        String title = "Shop";
        FontMetrics fontMetrics = g2.getFontMetrics(gameFont);
        int titleX = (gp.getWidth() - fontMetrics.stringWidth(title)) / 2;
        int titleY = (int)(gp.getHeight() / 2 - gp.screenSettings.tileSize * 1.5);

        // Draw shadow
        g2.setColor(Color.darkGray);
        g2.drawString(title, titleX + 2, titleY + 2);

        // Draw title
        g2.setColor(Color.decode("#ff6600")); // Set the color to #ff6600 (orange)
        g2.drawString(title, titleX, titleY);

        // Draw shop items
        Font itemFont = new Font("GameFont", Font.PLAIN, 16);
        g2.setFont(itemFont);
        int itemY = titleY + 100; // Adjust the Y position for shop items
        g2.setColor(Color.WHITE); // Set the color to white

        for (int i = 0; i < gp.shopManager.getShopItems().size(); i++) {
            ShopItem item = gp.shopManager.getShopItems().get(i);
            String itemText = item.getName();
            if (!item.isPurchased()) {
                itemText += " - Price: " + item.getPrice();
                g2.setColor(Color.WHITE); // Set the color to white for non-purchased items
            } else {
                if (item instanceof SpriteItem) {
                    SpriteItem spriteItem = (SpriteItem) item;
                    if (spriteItem.getSprite().equals(gp.user.sprite)) {
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

            drawMenuItem(g2, itemY, itemText, i);

            itemY += 30; // Adjust the Y position for the next item
        }

        // Draw "Back" option
        String backText = "Back";
        g2.setColor(Color.WHITE); // Set the color to white for the "Back" option
        drawMenuItem(g2, itemY, backText, gp.shopManager.getShopItems().size());
    }

    /**
     * Draws the pause screen on the screen.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    public void drawPauseScreen(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50));
        int messageX = (gp.getWidth() - g2.getFontMetrics().stringWidth("PAUSED")) / 2;
        int messageY = (int)(gp.getHeight() / 2 - gp.screenSettings.tileSize * 1.5);
        g2.drawString("PAUSED", messageX, messageY);
    }

    public void drawGameOverScreen(Graphics2D g2) {
        // Draw black background
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.getWidth(), gp.getHeight());

        // Draw "GAME OVER" message
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50));
        g2.setColor(Color.WHITE);
        int messageX = (gp.getWidth() - g2.getFontMetrics().stringWidth("GAME OVER")) / 2;
        int messageY = (int)(gp.getHeight() / 2 - gp.screenSettings.tileSize * 1.5);
        g2.drawString("GAME OVER", messageX, messageY);

        // Draw subheader
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20));
        g2.setColor(Color.RED);
        int subheaderX = (gp.getWidth() - g2.getFontMetrics().stringWidth("YOU ARE BANKRUPT")) / 2;
        int subheaderY = messageY + 50;
        g2.drawString("YOU ARE BANKRUPT", subheaderX, subheaderY);

        // Add padding between subheader and menu items
        int padding = 20;
        int menuItemY = subheaderY + g2.getFontMetrics().getHeight() + padding;

        // Draw smaller menu items with smaller font size
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16));
        drawMenuItem(g2, menuItemY, "Restart Game", 0);
        drawMenuItem(g2, menuItemY + 30, "Exit", 1);
    }

}
