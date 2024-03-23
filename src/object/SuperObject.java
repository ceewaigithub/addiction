package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import world.GamePanel;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int x, y;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

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

    @Override
    public String toString() {
        return name + " " + x + " " + y;
    }
}
