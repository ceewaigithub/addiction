package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import blackjack.BlackJackApp;
import highlow.*;
import object.OBJ_Door;
import baccarat.*;
import world.GamePanel;
import world.KeyHandler;

/**
 * The User class represents a user in the game. It extends the Entity class and contains
 * information about the user's position, money, sprite, and other attributes.
 */
/**
 * Represents a User in the game.
 */
public class User extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    int money = 0;
    public String sprite;
    private final Object moneyLock = new Object();

    /**
     * Constructs a User object.
     * 
     * @param gp   The GamePanel object.
     * @param keyH The KeyHandler object.
     */
    public User(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.screenSettings.screenWidth / 2 - gp.screenSettings.tileSize / 2;
        screenY = gp.screenSettings.screenHeight / 2 - gp.screenSettings.tileSize / 2;
        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDefaultValues();
        getPlayerImage();
    }

    /**
     * Sets the default values for the User.
     */
    public void setDefaultValues() {
        worldX = gp.screenSettings.worldWidth / 2 - gp.screenSettings.tileSize / 2;
        worldY = gp.screenSettings.worldHeight / 2 - gp.screenSettings.tileSize / 4;
        speed = 4;
        direction = "down";
        sprite = "default";
        money = 250;
    }

    /**
     * Loads the player's image based on the sprite.
     */
    public void getPlayerImage() {
        try {
            String currentDirectory = new File("").getAbsolutePath();
            System.out.println("Current Directory: " + currentDirectory);

            up1 = ImageIO.read(new File(currentDirectory + "/res/player/" + sprite + "_up_1.png"));
            up2 = ImageIO.read(new File(currentDirectory + "/res/player/" + sprite + "_up_2.png"));

            down1 = ImageIO.read(new File(currentDirectory + "/res/player/" + sprite + "_down_1.png"));
            down2 = ImageIO.read(new File(currentDirectory + "/res/player/" + sprite + "_down_2.png"));

            left1 = ImageIO.read(new File(currentDirectory + "/res/player/" + sprite + "_left_1.png"));
            left2 = ImageIO.read(new File(currentDirectory + "/res/player/" + sprite + "_left_2.png"));

            right1 = ImageIO.read(new File(currentDirectory + "/res/player/" + sprite + "_right_1.png"));
            right2 = ImageIO.read(new File(currentDirectory + "/res/player/" + sprite + "_right_2.png"));

        } catch (Exception e) {
            System.out.println("Error getting player image: " + e + " " + sprite);
        }
    }

    /**
     * Updates the User's position and handles collisions.
     */
    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            }
            if (keyH.downPressed) {
                direction = "down";
            }
            if (keyH.leftPressed) {
                direction = "left";
            }
            if (keyH.rightPressed) {
                direction = "right";
            }

            // Check for tile collision
            collisionOn = false;
            gp.cc.checkTile(this);

            // Check for object collision
            int objIndex = gp.cc.checkObject(this, true);
            interactWithObject(objIndex);
            if (objIndex != 999) {
                collisionOn = true;
            }

            // If collision is false, player can move
            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        // Check for object interaction
        int objectFacingIndex = gp.cc.isFacing(this);
        if (objectFacingIndex != 999 && keyH.spacePressed) {
            interactWithObject(objectFacingIndex);
        }
    }

    /**
     * Interacts with the object at the given index.
     * 
     * @param idx The index of the object.
     */
    public void interactWithObject(int idx) {
        if (idx != 999) {
            String objectName = gp.obj[idx].name;
            switch (objectName) {
                case "BlackJack":
                    gp.ui.showMessage("BlackJack");
                    if (keyH.spacePressed) {
                        gp.musicManager.playSE(1);
                        gp.frame.setVisible(false);
                        BlackJackApp bj = new BlackJackApp(this, gp.frame);
                        clearKeyPresses(); // clear key presses so that the player doesn't move while playing
                    }
                    break;
                case "Door":
                    OBJ_Door door = (OBJ_Door) gp.obj[idx];
                    if (door.isLocked()) {
                        int cost = door.getCostToOpen();
                        gp.ui.showMessage("$" + cost + " to unlock.");
                        if (keyH.spacePressed) {
                            door.interact(this, gp);
                        }
                    }

                    break;
                case "Baccarat":
                    gp.ui.showMessage("Baccarat");
                    if (keyH.spacePressed) {
                        gp.musicManager.playSE(1);
                        gp.frame.setVisible(false);
                        BaccaratApp b = new BaccaratApp(this, gp.frame);
                        clearKeyPresses(); // clear key presses so that the player doesn't move while playing
                    }
                    break;
                case "HighLow":
                    gp.ui.showMessage("HighLow");
                    if (keyH.spacePressed) {
                        gp.musicManager.playSE(1);
                        gp.frame.setVisible(false);
                        HighLowApp hl = new HighLowApp(this, gp.frame);
                        clearKeyPresses(); // clear key presses so that the player doesn't move while playing
                    }
                    break;
            }
        }
    }

    /**
     * Gets the current balance of the User.
     * 
     * @return The current balance.
     */
    public int getBalance() {
        return money;
    }

    public void setBalance(int money) {
        this.money = money;
    }

    /**
     * Clears the key presses.
     */
    public void clearKeyPresses() {
        keyH.spacePressed = false;
        keyH.upPressed = false;
        keyH.downPressed = false;
        keyH.leftPressed = false;
        keyH.rightPressed = false;
    }

    /**
     * Draws the User on the screen.
     * 
     * @param g2 The Graphics2D object.
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.screenSettings.tileSize, gp.screenSettings.tileSize, null);
    }

    /**
     * Adds money to the User's balance.
     * 
     * @param amount The amount to add.
     */
    public void addMoney(int amount) {
        synchronized (moneyLock) {
            money += amount;
        }
    }

    /**
     * Subtracts money from the User's balance.
     * 
     * @param amount The amount to subtract.
     */
    public void subtractMoney(int amount) {
        synchronized (moneyLock) {
            money -= amount;
        }
    }
}
