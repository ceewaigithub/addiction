package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import world.GamePanel;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * The SuperObject class represents a generic object in a game.
 * It contains properties and methods that are common to all game objects.
 */
public class SuperObject {
    /**
     * The image associated with the object.
     */
    public BufferedImage image;
    
    /**
     * The name of the object.
     */
    public String name;
    
    /**
     * Indicates whether the object can collide with other objects.
     */
    public boolean collision = false;
    
    /**
     * The x-coordinate of the object's position.
     */
    public int x;
    
    /**
     * The y-coordinate of the object's position.
     */
    public int y;
    
    /**
     * The rectangular area used for collision detection.
     */
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    
    /**
     * The default x-coordinate of the solid area.
     */
    public int solidAreaDefaultX = 0;
    
    /**
     * The default y-coordinate of the solid area.
     */
    public int solidAreaDefaultY = 0;

    /**
     * Draws the object on the screen.
     * 
     * @param g2 The graphics context used for drawing.
     * @param gp The game panel where the object is being drawn.
     */
    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = x - gp.user.worldX + gp.user.screenX;
        int screenY = y - gp.user.worldY + gp.user.screenY;

        if (x + gp.tileSize > gp.user.worldX - gp.user.screenX
                && x < gp.user.worldX - gp.user.screenX + gp.screenWidth
                && y + gp.tileSize > gp.user.worldY - gp.user.screenY
                && y < gp.user.worldY - gp.user.screenY + gp.screenHeight) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    /**
     * Returns a string representation of the object.
     * 
     * @return The string representation of the object.
     */
    @Override
    public String toString() {
        return name + " " + x + " " + y;
    }
}
