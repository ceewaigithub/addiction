package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import blackjack.BlackJackApp;
import highlow.*;
import object.OBJ_Door;
import Baccarat.*;
import world.GamePanel;
import world.KeyHandler;

public class User extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int money = 0;
    public String sprite;
    private final Object moneyLock = new Object();

    public User(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;
        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        sprite = "default";
        setDefaultValues();
        getPlayerImage();
    }


    public void setDefaultValues() {
        worldX = gp.worldWidth / 2 - gp.tileSize / 2;
        worldY = gp.worldHeight / 2 - gp.tileSize / 2;
        speed = 4;
        direction = "down";
        money = 250;
    }

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

            // check for object collision
            int objIndex = gp.cc.checkObject(this, true);
            interactWithObject(objIndex);
            if (objIndex != 999) {
                collisionOn = true;
            }
            
            // if collision is false, player can move
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
    }

    public void interactWithObject(int idx) {
        if (idx != 999) {
            String objectName = gp.obj[idx].name;
            switch (objectName) {
                case "BlackJack":
                    gp.ui.showMessage("BlackJack");
                    if (keyH.spacePressed) {
                        gp.playSE(1);
                        gp.frame.setVisible(false);
                        BlackJackApp bj = new BlackJackApp(this, gp.frame);
                        clearKeyPresses(); // clear key presses so that the player doesn't move while playing
                    }
                    break;
                case "Door":
                    if (((OBJ_Door)gp.obj[idx]).isLocked()) {
                        gp.ui.showMessage("Pay 50 to unlock");
                        if (keyH.spacePressed) {
                            if (money > 50) {
                                money -= 50;
                                gp.playSE(5);
                                gp.ui.showMessage("Money - 50");
                                ((OBJ_Door)gp.obj[idx]).setOpen(true);
                                clearKeyPresses();
                            } else {
                                gp.ui.showMessage("You don't have enough money");
                            }
                        }
                    }
                    break;
                case "Baccarat":
                    gp.ui.showMessage("Baccarat");
                    if (keyH.spacePressed) {
                        gp.playSE(1);
                        gp.frame.setVisible(false);
                        BaccaratApp b = new BaccaratApp(this, gp.frame);
                        clearKeyPresses(); // clear key presses so that the player doesn't move while playing
                    }
                    break;
                case "HighLow":
                    gp.ui.showMessage("HighLow");
                    if (keyH.spacePressed) {
                        gp.playSE(1);
                        gp.frame.setVisible(false);
                        HighLowApp hl = new HighLowApp(this, gp.frame);
                        clearKeyPresses(); // clear key presses so that the player doesn't move while playing
                    }
                    break;
            }
        }
    }

    public int getBalance(){
        return money;
    }

    public void clearKeyPresses() {
        keyH.spacePressed = false;
        keyH.upPressed = false;
        keyH.downPressed = false;
        keyH.leftPressed = false;
        keyH.rightPressed = false;
    }

    public void draw(Graphics2D g2) {
        // g2.setColor(Color.white);
        // g2.fillRect(x, y, gp.tileSize, gp.tileSize);

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
        //System.out.println(money);
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

    public void addMoney(int amount) {
        synchronized (moneyLock) {
            money += amount;
        }
    }
    
    public void subtractMoney(int amount) {
        synchronized (moneyLock) {
            money -= amount;
        }
    }
}
